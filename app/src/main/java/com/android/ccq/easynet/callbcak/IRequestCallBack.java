package com.android.ccq.easynet.callbcak;

import com.android.ccq.easynet.response.ErrorResponse;
import com.android.ccq.easynet.response.SuccessResponse;

public interface IRequestCallBack<T> {
    void onSuccess(int code,Object object,SuccessResponse response);
    void onError(ErrorResponse response);
}
