package com.anzhen.common;

import lombok.Getter;

/**
 * @author
 * @date 2022/5/16
 */
@Getter
public class ResponseMsg {

    private Integer status;
    private String msg;
    private Object obj;

    public static ResponseMsg build() {
        return new ResponseMsg();
    }

    public static ResponseMsg ok(String msg) {
        return new ResponseMsg(200, msg, null);
    }

    public static ResponseMsg ok(String msg, Object obj) {
        return new ResponseMsg(200, msg, obj);
    }

    public static ResponseMsg error(String msg) {
        return new ResponseMsg(500, msg, null);
    }

    public static ResponseMsg error(String msg, Object obj) {
        return new ResponseMsg(500, msg, obj);
    }

    private ResponseMsg() {
    }

    private ResponseMsg(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public ResponseMsg setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ResponseMsg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResponseMsg setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}
