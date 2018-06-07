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

    public ErrorResponse(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
