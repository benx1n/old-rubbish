package com.example.auxiliary.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.auxiliary.entity.Remind;
import com.example.auxiliary.entity.Result;
import com.example.auxiliary.service.RemindService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author ztx
 * @date 2021-04-28 15:25
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/remind")
public class RemindController {

    private final RemindService remindService;

    /**
     * 获取提醒
     *
     * @param remind
     * @return
     */
    @GetMapping("/remind")
    public Result<?> getRemind(Remind remind) {
        return Result.createSuccess(this.remindService.list(Wrappers.<Remind>lambdaQuery()
                .eq(StringUtils.isNotEmpty(remind.getUserId()), Remind::getUserId, remind.getUserId())
                .in(StringUtils.isNotEmpty(remind.getType()), Remind::getType, remind.getType())
                .in(StringUtils.isNotEmpty(remind.getStatus()), Remind::getStatus, remind.getStatus())
            )
        );
    }

    /**
     * 更新状态
     *
     * @param remind
     * @return
     */
    @PostMapping("/remind")
    public Result<?> setStatus(@RequestBody Remind remind) {
        this.remindService.saveOrUpdate(remind);
        return Result.createSuccess();

    }
}
