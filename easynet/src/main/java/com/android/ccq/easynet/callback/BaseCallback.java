package com.android.ccq.easynet.callback;


import com.android.ccq.easynet.response.ErrorResponse;
import com.android.ccq.easynet.response.SuccessResponse;

/**
 * Description : 网络请求基础回调接口
 * Author :      Gaozhanzhong
 * Create :      2017/10/18 9:46
 * Update :      2017/10/18 9:46
 * Version :     V1.1.1
 */

public interface BaseCallback<T> {
    void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request);
    void onSuccess(SuccessResponse result);
    void onError(ErrorResponse errorResponse);
    void onFinish();
    boolean onNetworkDisconnected();
}
