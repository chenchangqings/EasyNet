package com.android.ccq.easynet.api;

import com.android.ccq.easynet.DoRequst;
import com.android.ccq.easynet.callbcak.IRequestCallBack;
import com.android.ccq.easynet.config.UrlConfig;
import com.android.ccq.easynet.reponse.DogBean;

import java.util.List;

public interface TestApi {

    @DoRequst(code=10001,url=UrlConfig.URL_VIDEOS)
    DogBean getVideo(Object param, IRequestCallBack callBack);

    @DoRequst(code=10002,url=UrlConfig.URL_VIDEOS)
    DogBean getDogs(Object param,IRequestCallBack callBack);



}
