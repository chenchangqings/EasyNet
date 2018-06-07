package com.android.ccq.easynet.api;

import com.android.ccq.easynet.callbcak.IRequestCallBack;
import com.android.ccq.easynet.config.RequsetCode;
import com.android.ccq.easynet.config.UrlConfig;
import com.android.ccq.easynet.reponse.DogBean;
import com.android.ccq.easynet.utils.Recover;

public class VideoApi extends BaseApi {

    /**
     * 获取实例
     *
     * @return
     */
    public static VideoApi build() {
        return new VideoApi();
    }

    /**
     * 获取我的上传，我拍，有象故事列表
     *
     * @param param
     * @param
     * @return
     */
    public void getVideoList(Object param, Recover recover, IRequestCallBack callback) {
         requestModel(RequsetCode.request_getVideo, genUrl(UrlConfig.URL_VIDEOS), param,recover,DogBean.class,callback);
    }


}
