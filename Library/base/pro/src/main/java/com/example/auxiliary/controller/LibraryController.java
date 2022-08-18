package com.example.auxiliary.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.auxiliary.entity.*;
import com.example.auxiliary.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ztx
 * @date 2021-04-27 16:16
 */
@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;
    private final LibraryTagsService libraryTagsService;
    private final LibraryReservationService libraryReservationService;
    private final UserLibraryService userLibraryService;
    private final RemindService remindService;

    /**
     * 获取书籍
     *
     * @param library
     * @return
     */
    @GetMapping("/library")
    public Result<?> getLibrary(Library library) {
        List<Library> list = this.libraryService.list(Wrappers.<Library>lambdaQuery()
            .and(StringUtils.isNotBlank(library.getStatus()), wrapper -> wrapper.eq(Library::getStatus, library.getStatus())
                .like(StringUtils.isNotBlank(library.getSearch()), Library::getName, library.getSearch())
                .or()
                .like(StringUtils.isNotBlank(library.getSearch()), Library::getAuthor, library.getSearch())
                .or()
                .like(StringUtils.isNotBlank(library.getSearch()), Library::getPress, library.getSearch())
                .or()
                .like(StringUtils.isNotBlank(library.getSearch()), Library::getCatory, library.getSearch())
            )
        );

        list.stream().forEach((e -> {
            List<String> tags = this.libraryTagsService.list(Wrappers.<LibraryTags>lambdaQuery()
                .eq(LibraryTags::getLibraryId, e.getId())).stream()
                .map(LibraryTags::getName).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(tags)) {
                e.setTags(tags);
            }
            //获取归还日期
            List<UserLibrary> list1 = this.userLibraryService.list(Wrappers.<UserLibrary>lambdaQuery()
                .eq(UserLibrary::getLibraryId, e.getId()).orderByDesc(UserLibrary::getEndDatetime));
            if (CollectionUtils.isNotEmpty(list1)) {
                e.setDate(list1.get(0).getEndDatetime().toString());
            }
        }));
        return Result.createSuccess(list);
    }

    /**
     * 借阅
     *
     * @param userLibrary
     * @return
     */
    @PostMapping("/borrow")
    @Transactional
    public Result<?> borrow(@RequestBody UserLibrary userLibrary) {
        userLibrary.setStartDatetime(LocalDate.now());
        //查询该书该天是否被预约
        List<LibraryReservation> libraryReservationList = this.libraryReservationService.list(Wrappers.<LibraryReservation>lambdaQuery()
            .eq(LibraryReservation::getReservationDate, userLibrary.getStartDatetime())
            .eq(LibraryReservation::getLibraryId, userLibrary.getLibraryId())
        );
        //正在被借阅的书籍
        List<Library> libraryList = this.libraryService.list(Wrappers.<Library>lambdaQuery().eq(Library::getId, userLibrary.getLibraryId())
            .eq(Library::getStatus, BorrowStatusEnum.BORROWING.getValue()));
        if (CollectionUtils.isNotEmpty(libraryReservationList) || CollectionUtils.isNotEmpty(libraryList)) {
            return Result.createFail("该日期此书已被预约或者借阅");
        }
        //可以被借阅
        userLibraryService.save(userLibrary);
        this.libraryService.updateById(Library.builder()
            .id(userLibrary.getLibraryId())
            .status(BorrowStatusEnum.BORROWING.getValue())
            .build());
        //发送提醒
        Library library = this.libraryService.getById(userLibrary.getLibraryId());
        this.remindService.save(Remind.builder()
            .type(BorrowStatusEnum.LIBRARY.getValue())
            .content("您已借阅" + library.getName() + "请于" + userLibrary.getEndDatetime() + "归还")
            .userId(userLibrary.getUserId())
            .status(BorrowStatusEnum.NOTTOBORROW.getValue())
            .build());
        return Result.createSuccess();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        this.libraryService.removeById(id);
        return Result.createSuccess();
    }

    /**
     * 新增书籍
     *
     * @param library
     * @return
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Library library) {
        this.libraryService.saveOrUpdate(library);
        return Result.createSuccess();
    }



}
