package com.android.ccq.easynet;

import android.app.Application;

import com.android.ccq.easynet.callback.ModelCallback;
import com.android.ccq.easynet.log.LogUtils;
import com.android.ccq.easynet.request.HttpRequest;
import com.android.ccq.easynet.config.RequestMethod;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import okhttp3.OkHttpClient;

public class OkGoClient  {
    private static OkGoClient mOkGoClient;
    private Enum dMethod = RequestMethod.POST;  //默认请求方式

    public OkGo init(Application app){
     return init(app,null);
    }

    public OkGo init(Application app, OkHttpClient mOkHttpClient){
        OkGo okGo = OkGo.getInstance().init(app)                       //必须调用初始化
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);
      if(null != mOkHttpClient){
          okGo.setOkHttpClient(mOkHttpClient);
      }
      return okGo;
    }

    public static OkGoClient getInstence(){
        if (mOkGoClient==null){
            synchronized (OkGoClient.class) {
                if (mOkGoClient == null)
                    mOkGoClient = new OkGoClient();
            }
        }
        return mOkGoClient;
    }




    //请求Model
    public  void requestModel(int tag, String path, Object params, Class<?> cls, ModelCallback callback){
        request(tag,dMethod,path,params,cls,callback);
    }
    //自定义请求方式（json作为request只支持post）
    public  <T>void request(int tag,Enum method,String path, Object params,Class<?> cls, ModelCallback callback){
        HttpRequest.getInstence().requestModel(tag,method,path,params,cls,callback);
    }

    public OkGoClient setLog(String tag){
        LogUtils.setLogTag(tag);
        return this;
    }


}
