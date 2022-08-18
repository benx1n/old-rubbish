package com.example.auxiliary.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.auxiliary.entity.BorrowStatusEnum;
import com.example.auxiliary.entity.Library;
import com.example.auxiliary.entity.Result;
import com.example.auxiliary.entity.UserLibrary;
import com.example.auxiliary.service.LibraryService;
import com.example.auxiliary.service.UserLibraryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ztx
 * @date 2021-04-27 22:20
 */
@RestController
@RequestMapping("/userLibrary")
@RequiredArgsConstructor
public class UserLibraryController {

    private final LibraryService libraryService;
    private final UserLibraryService userLibraryService;

    /**
     * 获取用户所借约的图书
     *
     * @param userLibrary
     * @return
     */
    @GetMapping("/userLibrary")
    public Result<?> getUserLirary(UserLibrary userLibrary) {
        //借阅的所有书籍
        List<UserLibrary> userLibraryList = userLibraryService.list(Wrappers.<UserLibrary>lambdaQuery()
            .eq(UserLibrary::getUserId, userLibrary.getUserId()));
        //图书列表
        userLibraryList.forEach(e -> {
            Library library = this.libraryService.getById(e.getLibraryId());
            library.setStatus(e.getStatus());
            //判断是否没有归还，如果未归还设置逾期标识
            if (StringUtils.equals(e.getStatus(), BorrowStatusEnum.BORROWING.getValue())) {
                //借阅状态下
                if (e.getEndDatetime().isBefore(LocalDate.now())) {
                    e.setStatus(BorrowStatusEnum.OVERDUE.getValue());
                }
            }
            e.setLibrary(library);
        });
        return Result.createSuccess(userLibraryList);
    }

    /**
     * 归还书籍
     *
     * @param userLibrary
     * @return
     */
    @PostMapping("/returnBooks")
    public Result<?> returnBooks(@RequestBody UserLibrary userLibrary) {
        UserLibrary entity = this.userLibraryService.getById(userLibrary.getId());
        Library library = this.libraryService.getById(entity.getLibraryId());
        if (entity.getEndDatetime().isBefore(LocalDate.now())) {
            //逾期
            entity.setPrice(8.8);
        }
        entity.setStatus(BorrowStatusEnum.WAITERBORROW.getValue());
        library.setStatus(BorrowStatusEnum.WAITERBORROW.getValue());
        this.userLibraryService.updateById(entity);
        this.libraryService.updateById(library);
        return Result.createSuccess();
    }

    /**
     * 图书归还
     *
     * @param userLibrary
     * @return
     */
    @PostMapping("/libraryRefound")
    public Result<?> libraryRefound(@RequestBody UserLibrary userLibrary) {
        UserLibrary entity = this.userLibraryService.list(Wrappers.<UserLibrary>lambdaQuery().eq(UserLibrary::getLibraryId, userLibrary.getLibraryId()).orderByDesc(UserLibrary::getEndDatetime)).get(0);
        Library library = this.libraryService.getById(entity.getLibraryId());
        if (entity.getEndDatetime().isBefore(LocalDate.now())) {
            //逾期
            entity.setStatus(BorrowStatusEnum.OVERDUEBORROW.getValue());
            entity.setPrice(8.8);
        } else {
            entity.setStatus(BorrowStatusEnum.BORROW.getValue());
        }

        library.setStatus(BorrowStatusEnum.NOTTOBORROW.getValue());
        this.userLibraryService.updateById(entity);
        this.libraryService.updateById(library);
        return Result.createSuccess();

    }

}
