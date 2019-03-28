package com.dong.framelibrary.entity;

import java.io.Serializable;

/**
 * 作者：zuo
 * 时间：2019/2/28 16:58
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "BaseResponse{\n" +//
                "\tcode=" + code + "\n" +
                "\tmsg='" + msg + "\'\n" +
                "\tdata=" + data + "\n" +
                '}';
    }
}
