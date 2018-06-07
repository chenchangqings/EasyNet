package com.android.ccq.easynet.api;

import com.android.ccq.easynet.OkGoClient;
import com.android.ccq.easynet.callback.ModelCallback;
import com.android.ccq.easynet.callbcak.IRequestCallBack;
import com.android.ccq.easynet.config.RequestMethod;
import com.android.ccq.easynet.config.ResponseCode;
import com.android.ccq.easynet.config.UrlConfig;
import com.android.ccq.easynet.reponse.BaseBean;
import com.android.ccq.easynet.response.ErrorResponse;
import com.android.ccq.easynet.response.SuccessResponse;
import com.android.ccq.easynet.utils.Recover;

public class BaseApi {


    protected void requestModel(int tag, String path, Object params, Recover recover, Class<?> cls, final IRequestCallBack callback){
        if(null != recover){
            recover.addTag(tag);
        }
        OkGoClient.getInstence().request(tag, RequestMethod.POST,path, params, cls, new ModelCallback<BaseBean>() {
            @Override
            public boolean onNetworkDisconnected() {   //返回true则直接拦截请求
                return super.onNetworkDisconnected();
            }

            @Override
            public void onSuccess(SuccessResponse response) {
                BaseBean bResponse = (BaseBean) response.getEntity();
                if (bResponse.code == ResponseCode.SUCCESS) {
                    callback.onSuccess(response.getReCode(), bResponse, response);
                }else {
                    callback.onError(new ErrorResponse(-1,""));
                }
            }
            @Override
            public void onError(ErrorResponse errorResponse) {
                callback.onError(errorResponse);
            }


        });
    }





    /**
     * 设置网络请求地址，当传入path以“http”开头，则直接返回path，否则将主机地址与path拼接返回
     *
     * @param path，传入路径
     * @return 设置完成的请求地址
     */
    protected String genUrl(String path) {
        if (path.startsWith("http")) return path;
        return UrlConfig.SERVER_DEBUG_BASE_URL + path;
    }








}
