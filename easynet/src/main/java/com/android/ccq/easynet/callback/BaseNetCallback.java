package com.android.ccq.easynet.callback;

import com.android.ccq.easynet.log.LogUtils;
import com.android.ccq.easynet.response.ErrorResponse;
import com.android.ccq.easynet.response.SuccessResponse;
import com.lzy.okgo.model.Response;

import java.util.concurrent.TimeUnit;

import static com.android.ccq.easynet.config.NetConstants.Error_Code_Analysis;
import static com.android.ccq.easynet.config.NetConstants.Error_Code_Base;
import static com.android.ccq.easynet.config.NetConstants.Error_Code_Net;
import static com.android.ccq.easynet.config.NetConstants.Error_Code_Null;
import static com.android.ccq.easynet.config.NetConstants.Error_Code_Server;

/**
 * Created by Chenchangqing on 2017/12/14.
 */

public abstract class BaseNetCallback<T> implements BaseCallback<T>{
    //执行请求，计算请求时间
    private long startNs;


    @Override
    public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
        startNs = System.nanoTime();
    }

    @Override
    public boolean onNetworkDisconnected() {  //返回true则直接拦截请求
        return false;
    }

    @Override
    public void onFinish() {

    }

    /**
    *处理服务器成功
    */
    public final void doSussess(int tag,Response<String> response,Object value){
        int code = response.code();
        if (code == 200 || code == 0){
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            LogUtils.d("OkHttpRequester", "ResponseCode--->"+tag + " ("+tookMs+"ms)"+"\nResult--->:" + response.body());
            onSuccess(new SuccessResponse(tag,response.body(),value));
        }else  if (code < 500) {
            doFailed(tag,Error_Code_Net);
        } else {
            doFailed(tag,Error_Code_Server);
        }
    }



    /**
     *处理服务器失败
     * @param code，错误码
     */
    public final void doFailed(int tag,int code){
        doFailed(tag,code,"");
    }

    public final void doFailed(int tag,int code,String error){
        String reason = "未知错误";
        switch (code){
            case Error_Code_Base:
                reason = "请求异常:" + error;
                onError(new ErrorResponse(Error_Code_Base,reason));
                break;
            case Error_Code_Net:
                reason = "网络异常，请稍后再试";
                onError(new ErrorResponse(Error_Code_Net,reason));
                break;
            case Error_Code_Server:
                reason = "服务器内部错误";
                onError(new ErrorResponse(Error_Code_Server,reason));
                break;
            case Error_Code_Null:
                reason = "空指针异常";
                onError(new ErrorResponse(Error_Code_Null,reason));
                break;
            case Error_Code_Analysis:
                reason = "解析异常";
                onError(new ErrorResponse(Error_Code_Analysis,reason));
                break;
        }

        LogUtils.d("OkHttpRequester", "ResponseCode--->"+tag+"\nResult--->:failed:ecode=" + code+" reason=" + reason);
    }


}
