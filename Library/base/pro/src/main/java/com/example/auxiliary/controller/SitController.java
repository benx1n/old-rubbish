package com.example.auxiliary.controller;

import com.example.auxiliary.entity.Result;
import com.example.auxiliary.entity.Sit;
import com.example.auxiliary.service.SitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author ztx
 * @date 2021-04-28 15:22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sit")
public class SitController {
    private final SitService sitService;

    /**
     * 新增座位
     *
     * @param sit
     * @return
     */
    @PostMapping("/sit")
    public Result<?> add(@RequestBody Sit sit) {
        this.sitService.save(sit);
        return Result.createSuccess();
    }

    /**
     * 获取座位
     *
     * @return
     */
    @GetMapping("/sit")
    public Result<?> get() {
        return Result.createSuccess(this.sitService.list());
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        this.sitService.removeById(id);
        return Result.createSuccess();
    }


}
