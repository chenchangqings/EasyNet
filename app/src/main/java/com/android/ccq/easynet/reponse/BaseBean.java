package com.android.ccq.easynet.reponse;

import android.content.Context;

/**
 * 返回消息体基类
 * Created by yangbing 2017/3/28.
 */
public class BaseBean {

    /**
     * 返回码
     */
    public int code;
    /**
     * 错误信息
     */
    public String msg;


    public  int getCode() {
        return code;
    }

    public  void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}