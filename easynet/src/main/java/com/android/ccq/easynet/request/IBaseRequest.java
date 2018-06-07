package com.android.ccq.easynet.request;

import com.android.ccq.easynet.callback.ModelCallback;

public interface IBaseRequest {

    /*
     *请求model数据
     */
   void requestModel(int tag,Enum method,String path,Object params, Class<?> cls,ModelCallback callback);

}
