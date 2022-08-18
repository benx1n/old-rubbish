package com.controller;

import com.entity.Result;
import com.entity.User;
import com.service.UserService;
import com.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

@Controller
@RequestMapping("")
public class MainController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("admin/login")
    public String adminLogin(){
        return "/admin/login";
    }

    @RequestMapping("login")
    public String login(User user, HttpServletRequest request, Integer type, Model model){

            User user1 = userService.selectOneModel(putEntityInMap(user));
            if(user1 != null && user1.getState() == 0){
                request.getSession().setAttribute("user",user1);
                return "/admin/index";
            }else if (user1 != null && user1.getState() == 1){
                model.addAttribute("message","账号被禁用");
                return "/admin/login";
            }else {
                model.addAttribute("message","账号或者密码错误");
                return "/admin/login";
            }


    }

    @RequestMapping("welcome")
    public String welcome(HttpServletRequest request){

        return "/admin/welcome";
    }

    @RequestMapping("logout")
    public String loout(HttpServletRequest request){
        request.getSession().invalidate();
        return "/admin/login";
    }

    @RequestMapping("reg/user")
    public String regUser(){
        return "/admin/reguser";
    }

    @RequestMapping("reg/driver")
    public String regdriver(){
        return "/admin/regdriver";
    }


    @RequestMapping("reg")
    @ResponseBody
    public Integer reguser(HttpServletRequest request,User user,Integer type){
        user.setCreatetime(new Date());
        int i = userService.insertModelWithoutNull(user);
        return i;
    }



    @RequestMapping("upload")
    @ResponseBody
    public Result imageUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResultUtil.error("文件不能为空");
        } else {
            // 上传的路径
            String rootPath = this.getClass().getResource("/").getPath();
            System.out.println(rootPath);
            rootPath = rootPath.replace("WEB-INF/", "");
            rootPath = rootPath.replace("classes/", "");
            // 原始全名称
            String originalFileName = file.getOriginalFilename();
            //文件名不带后缀
            String fileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            // 后缀名
            String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
            // 新文件
            String pathSuffix = "static/images/" + fileName + suffixName;
            File newFile = new File(rootPath + pathSuffix);
            //获取文件大小
            // 判断目标文件所在目录是否存在
            if (!newFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建父目录
                newFile.getParentFile().mkdirs();
            }
            System.out.println(newFile);
            // 上传
            try {
                file.transferTo(newFile);
            } catch (Exception e) {
                return ResultUtil.error(e.getMessage());
            }
            return ResultUtil.success("/static/images/" + fileName + suffixName);
        }
    }

}
