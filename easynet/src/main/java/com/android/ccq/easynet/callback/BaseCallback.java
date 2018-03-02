package com.android.ccq.easynet.callback;


import com.android.ccq.easynet.ErrorResponse;

/**
 * Description : 网络请求基础回调接口
 * Author :      Gaozhanzhong
 * Create :      2017/10/18 9:46
 * Update :      2017/10/18 9:46
 * Version :     V1.1.1
 */

public interface BaseCallback<T> {
    void onStart();
    void onSuccess(T result);
    void onError(ErrorResponse errorResponse);
    void onFinish();
    void onNetworkDisconnected();
}
