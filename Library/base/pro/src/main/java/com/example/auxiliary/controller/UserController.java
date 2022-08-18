package com.example.auxiliary.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.auxiliary.entity.Result;
import com.example.auxiliary.entity.User;
import com.example.auxiliary.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author ztx
 * @date 2021-02-21 13:35
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * 获取微信用户
     *
     * @param userName
     * @return
     */
    @GetMapping("/user")
    Result<List<User>> getUserList(@RequestParam String userName, @RequestParam String openid) {
        if (StringUtils.isEmpty(userName)) {
            return Result.createSuccess(userService.list(Wrappers.<User>lambdaQuery()
                .ne(User::getOpenId, openid)));
        }
        return Result.createSuccess(userService.list(Wrappers.<User>lambdaQuery()
            .eq(User::getNickName, userName)
            .ne(User::getOpenId, openid)));
    }

    /**
     * 更新数据
     *
     * @param user
     * @return
     */
    @PostMapping("/user")
    public Result<?> updateUser(@RequestBody User user) {
        this.userService.updateById(user);
        return Result.createSuccess();
    }


    /**
     * 获取所有用户
     *
     * @return
     */
    @GetMapping("/allUser")
    public Result<?> getAllUser(String name) {
        return Result.createSuccess(this.userService.list(Wrappers.<User>lambdaQuery()
            .like(StringUtils.isNotEmpty(name), User::getNickName, name)));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteUser(@PathVariable String id) {
        this.userService.removeById(id);
        return Result.createSuccess();
    }


}
