package com.example.auxiliary.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.auxiliary.entity.*;
import com.example.auxiliary.service.RemindService;
import com.example.auxiliary.service.SitReservationService;
import com.example.auxiliary.service.SitService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ztx
 * @date 2021-04-27 16:16
 */
@RestController
@RequestMapping("/sitReservation")
@RequiredArgsConstructor
public class SitReservationController {

    private final SitService sitService;
    private final SitReservationService sitReservationService;
    private final RemindService remindService;


    /**
     * 获取指定日期的座位状态及所有座位
     *
     * @param sitReservation
     * @return
     */
    @GetMapping("/sitReservation")
    public Result<?> getSitReservation(SitReservation sitReservation) {
        //获取所有座位
        List<Sit> allSit = this.sitService.list();
        sitReservation.setReservationDatetime(LocalDate.parse(sitReservation.getDate()));
        //获取该日期的预约座位
        List<SitReservation> sitReservationList = this.sitReservationService.list(Wrappers.<SitReservation>lambdaQuery()
            .eq(SitReservation::getReservationDatetime, sitReservation.getReservationDatetime()));
        //设置座位的状态
        allSit.parallelStream().forEach(e -> {
            List<SitReservation> sitReservations = sitReservationList.parallelStream()
                .filter(r -> StringUtils.equals(r.getSitId(), e.getId()))
                .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(sitReservations)) {
                e.setStatus(BorrowStatusEnum.BORROWING.getValue());
            } else {
                e.setStatus(BorrowStatusEnum.NOTTOBORROW.getValue());
            }
        });
        //拼接数据
        Map<String, List<Sit>> sits = allSit.stream().collect(Collectors.groupingBy(Sit::getLevel));
        return Result.createSuccess(sits);
    }

    /**
     * 座位预约
     *
     * @param sitReservation
     * @return
     */
    @PostMapping("/sitReservation")
    public Result<?> borrowSit(@RequestBody SitReservation sitReservation) {
        //查询当天用户是否已经预约其他座位
        sitReservation.setReservationDatetime(LocalDate.parse(sitReservation.getDate()));
        List<SitReservation> userReservationList = this.sitReservationService.list(Wrappers.<SitReservation>lambdaQuery()
            .eq(SitReservation::getUserId, sitReservation.getUserId())
            .eq(SitReservation::getReservationDatetime, sitReservation.getReservationDatetime())
        );
        if (CollectionUtils.isNotEmpty(userReservationList)) {
            return Result.createFail("你在该日期已经预约了其他座位，请勿重复预约");
        }
        //查询该座位是否被预约
        List<SitReservation> sitReservationList = this.sitReservationService.list(Wrappers.<SitReservation>lambdaQuery()
            .eq(SitReservation::getSitId, sitReservation.getSitId())
            .eq(SitReservation::getReservationDatetime, sitReservation.getReservationDatetime())
        );
        if (CollectionUtils.isNotEmpty(sitReservationList)) {
            return Result.createFail("该座位已被预约!");
        }
        //进行座位预约
        this.sitReservationService.save(sitReservation);
        //发送座位预约提醒
        Sit sit = this.sitService.getById(sitReservation.getSitId());
        this.remindService.save(Remind.builder()
            .userId(sitReservation.getUserId())
            .type(BorrowStatusEnum.SIT.getValue())
            .content("您已预约" + sitReservation.getReservationDatetime() + "当天" + sit.getLevel() + "-" + sit.getName())
            .status(BorrowStatusEnum.NOTTOBORROW.getValue())
            .build());
        return Result.createSuccess("预约成功!");
    }


}
