package com.example.auxiliary.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.auxiliary.config.WxMaConfiguration;
import com.example.auxiliary.entity.Result;
import com.example.auxiliary.entity.ResultEnum;
import com.example.auxiliary.entity.User;
import com.example.auxiliary.service.IUserService;
import com.example.auxiliary.utils.JsonUtils;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public String login(@PathVariable String appid, String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

//    /**
//     * <pre>
//     * 获取用户信息接口
//     * </pre>
//     */
//    @GetMapping("/info")
//    public String info(@PathVariable String appid, String sessionKey,
//                       String signature, String rawData, String encryptedData, String iv) {
//        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
//
//        // 用户信息校验
//        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
//            return "user check failed";
//        }
//        // 解密用户信息
//        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
//        List<User> users = userService.list(Wrappers.<User>lambdaQuery().eq(User::getOpenId, userInfo.getOpenId()));
//        if(CollectionUtils.isEmpty(users)){
//            //插入数据
//            userService.saveOrUpdate(User.builder()
//                    .id(UUID.randomUUID().toString())
//                    .gender(userInfo.getGender())
//                    .nickName(userInfo.getNickName())
//                    .openId(userInfo.getOpenId())
//                    .province(userInfo.getProvince())
//                    .avatarUrl(userInfo.getAvatarUrl())
//                    .build());
//        }
//        return JsonUtils.toJson(userInfo);
//    }

    @PostMapping("/wxLogin")
    public Result<?> wxLogin(@RequestBody WxMaUserInfo wxMaUserInfo) {
        List<User> users = userService.list(Wrappers.<User>lambdaQuery().eq(User::getOpenId, wxMaUserInfo.getOpenId()));
        if (CollectionUtils.isEmpty(users)) {
            User build = User.builder()
                .id(UUID.randomUUID().toString())
                .gender(wxMaUserInfo.getGender())
                .nickName(wxMaUserInfo.getNickName())
                .openId(wxMaUserInfo.getOpenId())
                .province(wxMaUserInfo.getProvince())
                .avatarUrl(wxMaUserInfo.getAvatarUrl())
                .status("0")
                .type("0")
                .build();
            //插入数据
            userService.saveOrUpdate(build);
            users.add(build);
        }
        return Result.createSuccess(users.get(0));
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }

}
