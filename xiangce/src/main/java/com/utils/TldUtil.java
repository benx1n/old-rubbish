package com.utils;

import com.dao.*;
import com.entity.*;
import com.service.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TldUtil {
    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static PhotosDao photosDao = SpringContextHolder.getBean(PhotosDao.class);


    public static int getListLength(List list) {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }


    public static boolean inArray(String[] array, String val) {
        if (array == null || val == null) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(val)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @return
     * @Author
     * @Description 将金额改成千分位分隔符
     * @Param
     **/
    public static String parseNumber(String money) {
        if (StringUtils.isEmpty(money)) {
            return "0.0";
        }
        if ("0".equals(money)) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat(",###,###.00");
        return df.format(new BigDecimal(money));
    }


    /**
     * 根据id获取用户
     */
    public static User getUser(Integer id) {
        User user = userDao.getModel(id);
        if (user != null) {
            return user;
        }
        return null;
    }

    /**
     * 根据id获取用户昵称
     */
    public static String getUsername(Integer id) {
        User user = userDao.getModel(id);
        if (user != null) {
            return user.getRealname();
        }
        return "暂无";
    }

    /**
     * 根据id获取相册
    */
    public static Photos getById(Integer id){
        Photos model = photosDao.getModel(id);
        return model;
    }


}