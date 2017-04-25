package com.xs.common;


/**
 * Created by xs on 2017-04-25.
 * Restful 枚举异常
 */
public enum RestEnum {
    SUCCESS(0,"成功"),
    UNKONW_ERROR(-1,"未知错误"),
    PARAMETER_INVALID(5000,"参数错误"),     ;


    private Integer code;
    private String msg;

    RestEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
