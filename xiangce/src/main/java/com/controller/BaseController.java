package com.controller;

import com.entity.Result;
import com.service.BaseService;
import com.utils.ResultUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController<E, P> {

    public BaseService<E, P> baseService;

    public String basePath = "/";//控制器前缀名称


    @RequestMapping(value = "get/{id}", method = {RequestMethod.GET})
    public String get(@PathVariable P id, Model model) {
        E entity = baseService.getModel(id);
        model.addAttribute("model", entity);
        return basePath + "_detail";
    }


    @RequestMapping(value = "get/list")
    public String getList(E entity) {
        Map<String, Object> map = putEntityInMap(entity);
        List<E> modelList = baseService.getModelList(map);
        return basePath + "_list";
    }

    /**
     * @Author admin
     * @Description 跳转到添加页面
     * @Date 2019/1/25 17:01
     **/
    @RequestMapping(value = "add")
    public String add() {
        return basePath + "_add";
    }

    /**
     * @Author admin
     * @Description post请求保存接口，json返回结果
     * @Date 2019/1/25 16:58
     **/
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    @ResponseBody
    public Result save(E entity) {
        int i = baseService.insertModelWithoutNull(entity);
        if (i == 0) {
            return ResultUtil.error(500, "失败");
        } else {
            return ResultUtil.success(entity);
        }
    }

    /**
     * @Author admin
     * @Description 获取需要修改的值，跳转到修改页面
     * @Date 2019/1/25 17:05
     **/
    @RequestMapping(value = "edit/{id}")
    public String edit(@PathVariable P id, Model model) {
        E entity = baseService.getModel(id);
        model.addAttribute("entity", entity);
        return basePath + "_edit";
    }

    /**
     * @Author admin
     * @Description post进行修改，返回json结果200为成功
     * @Date 2019/1/25 17:06
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(E entity) {
        int i = baseService.insertModelWithoutNull(entity);
        if (i == 0) {
            return ResultUtil.error(500, "失败");
        } else {
            return ResultUtil.success(entity);
        }
    }

    /**
     * @Author admin
     * @Description 根据id删除
     * @Date 2019/1/25 17:09
     **/
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") P id) {
        int i = baseService.deleteModel(id);
        if (i == 0) {
            return ResultUtil.error(500, "失败");
        } else {
            return ResultUtil.success(id);
        }
    }


    /**
     * @Author admin
     * @Description 此方法剔除空值传""的情况
     * @Date 2019/1/25 15:44
     **/
    public String coverBlankToNull(String input) {
        if ("".equals(input)) {
            return null;
        }
        return input;
    }

    /**
     * @Author admin
     * @Description 将实体类中的值放入map
     * @Date 2019/1/25 16:45
     **/
    public Map<String, Object> putEntityInMap(E entity) {
        Class<?> aClass = entity.getClass();//获取类对象
        Field[] fields = aClass.getDeclaredFields();//获取对象的属性名数组
        Map<String, Object> query = new HashMap<>();//查询map
        try {
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                String firstLetter = name.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + name.substring(1);//构造get方法的方法名
                Method method = aClass.getMethod(getter, new Class[]{});
                Object value = method.invoke(entity, new Object[]{});//获取属性的值
                if (value != null) {
                    query.put(name, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }


    public static boolean isIn(double n, double m, double x, double y) {

        double sum;
        sum = Math.sqrt((n - x) * (n - x) + (m - y) * (m - y));

        if (sum <= 20) {
            return true;
        }else {
            return false;
        }
    }


}
