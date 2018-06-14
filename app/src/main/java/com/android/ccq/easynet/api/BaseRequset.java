package com.android.ccq.easynet.api;

import com.android.ccq.easynet.EasyNetFactory;
import com.android.ccq.easynet.OkGoClient;
import com.android.ccq.easynet.callback.IMethod;
import com.android.ccq.easynet.callback.ModelCallback;
import com.android.ccq.easynet.callbcak.IRequestCallBack;
import com.android.ccq.easynet.config.RequestMethod;
import com.android.ccq.easynet.config.ResponseCode;
import com.android.ccq.easynet.config.UrlConfig;
import com.android.ccq.easynet.reponse.BaseBean;
import com.android.ccq.easynet.response.ErrorResponse;
import com.android.ccq.easynet.response.SuccessResponse;
import com.android.ccq.easynet.utils.Recover;

public class BaseRequset implements IMethod {
    private Recover recover;
    public static BaseRequset build() {
        return new BaseRequset();
    }


    //根据类动态创建实例
    public <T> T create(final Class<T> service,Recover recover){
        this.recover = recover;
        return  create(service);
    }

    //根据类动态创建实例
    public <T> T create(final Class<T> service){
        try {
            return new EasyNetFactory().create(service,this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void doMethod(int code, String url, Object params, Class<?> cls,Object callback) {
        if (recover != null) {
            recover.addTag(code);
        }
        requestModel(code, genUrl(url), params,null,cls,(IRequestCallBack) callback);
    }

    private String genUrl(String path) {
        if (path.startsWith("http")) return path;
        return UrlConfig.SERVER_DEBUG_BASE_URL + path;
    }


    //发起请求
    public void requestModel(int tag, String path, Object params, Recover recover, Class<?> cls, final IRequestCallBack callback){
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
                    callback.onSuccess(response.getReCode(), bResponse.getData(), response);
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

}
