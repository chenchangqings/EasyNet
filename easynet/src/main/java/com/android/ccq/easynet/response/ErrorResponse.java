package com.android.ccq.easynet.response;

/**
 * Description : 请求失败时onError传递的错误信息类
 * Author :      Gaozhanzhong
 * Create :      2017/10/18 19:48
 * Update :      2017/10/18 19:48
 * Version :     V1.1.1
 */

public class ErrorResponse {
    private int errorCode;
    private String errorMsg;
    private String json;

    public ErrorResponse(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ErrorResponse(int errorCode, String errorMsg,String json) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.json = json;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
