package com.xs.common;

import lombok.*;


/**
 * Created by xs on 2017-04-25.
 */
@Data
@NoArgsConstructor
public class ResultJson<T> {
    /**  错误码    **/
    private Integer code;

    /**  错误信息  **/
    private String  msg;

    /** 具体内容   **/
    private T data;

    public ResultJson(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
