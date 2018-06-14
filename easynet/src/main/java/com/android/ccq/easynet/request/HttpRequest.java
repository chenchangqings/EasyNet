package com.android.ccq.easynet.request;

import android.util.Log;

import com.android.ccq.easynet.config.NetConstants;
import com.android.ccq.easynet.analysis.GsonSerializer;
import com.android.ccq.easynet.callback.ModelCallback;
import com.android.ccq.easynet.config.RequestMethod;
import com.android.ccq.easynet.log.LogUtils;
import com.android.ccq.easynet.utils.NetworkUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.io.IOException;

import static com.android.ccq.easynet.config.NetConstants.Error_Code_Analysis;
import static com.android.ccq.easynet.config.NetConstants.Error_Code_Base;
import static com.android.ccq.easynet.config.NetConstants.Error_Code_Null;

public class HttpRequest implements IBaseRequest{
    private static IBaseRequest mHttpRequest;

    public static final IBaseRequest getInstence(){
        if (mHttpRequest==null){
            synchronized (HttpRequest.class) {
                if (mHttpRequest == null)
                    mHttpRequest = new HttpRequest();
            }
        }
        return mHttpRequest;
    }


    /**
     * 请求数据并自动解析成实体类对象，回调方法执行在UI线程，可直接刷新UI
     * @param tag，请求的tag
     * @param path，请求的url中除去主机地址和项目名后的path部分
     * @param params，请求参数
     * @param callback，请求回调
     */


    @Override
    public  void requestModel(final int tag,Enum method,String path,Object params,final Class<?> cls,final ModelCallback callback) {
        if(callback.onNetworkDisconnected()){
            return;
        }
        getCallBack(tag,method,path,params,new com.lzy.okgo.callback.StringCallback() {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                callback.onStart(request);
                super.onStart(request);

            }
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    Object model = GsonSerializer.getInstance().fromJson(response.body(), cls);
                    if(null != model){
                        callback.doSussess(tag,response,model);
                    }else{
                        callback.doFailed(tag,Error_Code_Null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.doFailed(tag,Error_Code_Analysis);
                    // TODO: 2017/10/18 : 解析返回数据失败
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                callback.doFailed(tag,Error_Code_Base,getResult(response));
                // TODO: 2017/10/18  : 请求失败
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });
    }






    //区分请求方式
    private <T>void getCallBack(int tag,Enum method, String path, Object params, Callback<T> callback) {
      if(params instanceof HttpParams){
          LogUtils.d("OkHttpRequester", "RequestCode--->"+tag+"\n" + method + "----------> " + path +"\nParam(HttpParam)--->:" + params.toString());
          //参数化请求
          if(RequestMethod.POST == method){
              OkGo.<T>post(path)
                      .tag(tag)
                      .params((HttpParams)params)
                      .execute(callback);
          }else if(RequestMethod.GET == method){
              OkGo.<T>get(path)
                      .tag(tag)
                      .params((HttpParams)params)
                      .execute(callback);
          }
      }else {
            if(RequestMethod.GET == method){
                throw new RuntimeException("json请求方式仅支持POST");
            }
            String json = GsonSerializer.getInstance().toJson(params);
            LogUtils.d("OkHttpRequester", "RequestCode--->"+tag+"\n" + method + "----------> " + path +"\nParam(json)--->:" +json);
            //json形式请求
            OkGo.<T>post(path)
                  .tag(tag)
                  .upJson(json)
                  .execute(callback);
      }
    }


    //解析错误结果
    private String getResult(Response<String> response){
        String result = null;
        if(null != response.getRawResponse() && null != response.getRawResponse().body()){
            try {
                result = response.getRawResponse().body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(null != response.getException()){
            result = response.getException().getMessage();
        }

        if(null == result || result.length() == 0){
            result = "未知错误";
        }
        return result;
    }

}
