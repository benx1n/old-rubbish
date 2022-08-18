package com.utils;

import com.entity.Result;

public class ResultUtil {

    public static Result success(Object o){
        Result result = new Result();
        result.setCode(200);
        result.setMessage("成功");
        result.setData(o);
        return result;
    }

    public  static Result error(String message){
        Result result = new Result();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public  static Result error(int code,String message){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
