package com.android.ccq.easynet.api;

import com.android.ccq.easynet.DoRequst;
import com.android.ccq.easynet.callbcak.IRequestCallBack;
import com.android.ccq.easynet.config.UrlConfig;
import com.android.ccq.easynet.reponse.DogBean;

public interface TestApi {

    @DoRequst(code=10001,url=UrlConfig.URL_VIDEOS)
    DogBean getVideo(Object param,IRequestCallBack callBack);

}
