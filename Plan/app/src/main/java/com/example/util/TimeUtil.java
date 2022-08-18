package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtil {

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        return time;
    }

}
