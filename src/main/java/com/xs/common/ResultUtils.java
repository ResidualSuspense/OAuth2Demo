package com.xs.common;

/**
 * Created by xs on 2017-03-22.
 * 封装json返回
 */
public class ResultUtils {

    public static ResultJson success( Object o ) {
        ResultJson resultJson = new ResultJson(RestEnum.SUCCESS.getCode(),RestEnum.SUCCESS.getMsg());
        resultJson.setData(o);
        return resultJson;
    }


    public static ResultJson error(int code, String msg) {
        ResultJson resultJson = new ResultJson(code,msg);
        return resultJson;
    }

    public static ResultJson error(RestEnum restEnum) {
        ResultJson resultJson = new ResultJson(restEnum.getCode(),restEnum.getMsg());
        return resultJson;
    }
}
