package com.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class MallUtil {


    public static Integer getRandom(){
        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("MMmmSS");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum =(int)((Math.random()*9+1)*10);// 获取4位随机数

        String number =  rannum + str;// 当前时间

        rannum = Integer.valueOf(number);

        return rannum;

    }

    public static void main(String[] args) {
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.set(2018,0,1);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate=null;
            if(month<10&&i<10){
                aDate = String.valueOf(year)+"-0"+month+"-0"+i;
            }
            if(month<10&&i>=10){
                aDate = String.valueOf(year)+"-0"+month+"-"+i;
            }
            if(month>=10&&i<10){
                aDate = String.valueOf(year)+"-"+month+"-0"+i;
            }
            if(month>=10&&i>=10){
                aDate = String.valueOf(year)+"-"+month+"-"+i;
            }

            aDate += " 00:00:00";
            list.add(aDate);
        }
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
        }
    }
}
