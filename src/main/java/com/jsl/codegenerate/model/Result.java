package com.jsl.codegenerate.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author piaopiao
 * @description
 * @date 2021/9/24 13:07
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> succ(T data) {
        return succ(200, "操作成功", data);
    }

    public static <T> Result<T> succ() {
        return succ(200, "操作成功", null);
    }

    public static <T> Result<T> succ(int code, String msg, T data) {
        Result<T> r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> fail(String msg) {
        return fail(400, msg, null);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return fail(400, msg, data);
    }

    public static <T> Result<T> fail(int code, String msg, T data) {
        Result<T> r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}