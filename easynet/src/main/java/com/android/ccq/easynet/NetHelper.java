package com.android.ccq.easynet;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.ccq.easynet.callback.BitmapCallback;
import com.android.ccq.easynet.callback.FileCallback;
import com.android.ccq.easynet.callback.ModelCallback;
import com.android.ccq.easynet.callback.RawCallback;
import com.android.ccq.easynet.callback.StringCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Description : 网络请求帮助类：
 *               提供请求String、Model、Bitmap、File、原始Response等方法，
 *               提供根据tag取消请求、取消全部网络请求等方法。
 * Author :      Gaozhanzhong
 * Create :      2017/10/17 11:29
 * Update :      2017/10/17 11:29
 * Version :     V1.1.1
 */

public class NetHelper {

    private static NetHelper instance;
    public NetHelper() {

    }

    /* 获取NetHelper实例 */
    public static NetHelper getInstance() {
        if (instance == null) {
            instance = new NetHelper();
        }
        return instance;
    }

    /**
     * 网络请求开始公共业务
     *
     * @param callback，请求回调
     * @return true：满足执行网络请求基本条件，将执行后续请求操作；否则返回false
     */
    private boolean commonPrepare(com.android.ccq.easynet.callback.BaseCallback callback) {

//        if (!NetworkUtils.isConnected()) {
//            callback.onNetworkDisconnected();
//            return false;
//        }
        return true;
    }

    //区分请求方式
    private <T>void getCallBack( Object tag,Enum method, String path, HttpParams params, com.lzy.okgo.callback.Callback<T> callback) {
        //头部信息传输
        HttpHeaders httpHeaders = new HttpHeaders();

        if(RequestMethod.POST == method){
            OkGo.<T>post(genUrl(path))
                    .tag(tag)
                    .headers(httpHeaders)
                    .params(params)
                    .execute(callback);
        }else if(RequestMethod.GET == method){
            OkGo.<T>get(genUrl(path))
                    .tag(tag)
                    .headers(httpHeaders)
                    .params(params)
                    .execute(callback);
        }
    }

    //默认请求方法
    public <T> void requestString(Context tag, String path, HttpParams params,final StringCallback callback){
        requestString(tag,RequestMethod.POST,path,params,true,callback);
    }
    public <T> void requestModel(Context tag, String path, HttpParams params,final ModelCallback<T> callback){
        requestModel(tag,RequestMethod.POST,path,params,true,callback);
    }
    public <T> void requestBitmap(Context tag, String path, HttpParams params,final BitmapCallback callback){
        requestBitmap(tag,RequestMethod.POST,path,params,true,callback);
    }
    public <T> void requestFile(Context tag, String path, HttpParams params,final FileCallback callback){
        requestFile(tag,RequestMethod.POST,path,params,true,callback);
    }

    //是否显示diaog的请求
    public <T> void requestString(Context tag, String path, HttpParams params,boolean loadShow,final StringCallback callback){
        requestString(tag,RequestMethod.POST,path,params,loadShow,callback);
    }
    public <T> void requestModel(Context tag, String path, HttpParams params,boolean loadShow,final ModelCallback<T> callback){
        requestModel(tag,RequestMethod.POST,path,params,loadShow,callback);
    }
    public <T> void requestBitmap(Context tag, String path, HttpParams params,boolean loadShow,final BitmapCallback callback){
        requestBitmap(tag,RequestMethod.POST,path,params,loadShow,callback);
    }
    public <T> void requestFile(Context tag, String path, HttpParams params,boolean loadShow,final FileCallback callback){
        requestFile(tag,RequestMethod.POST,path,params,loadShow,callback);
    }

    //默认转换成callBack指定的范型
    public <T> void requestModel(Context tag, Enum method,String path,HttpParams params,boolean loadShow,ModelCallback<T> callback){
        requestModel(tag,method,path,params,loadShow,callback.getGenericClass(),callback);
    }


    /**
     * 请求String文本数据，回调方法执行在UI线程，可直接刷新UI
     * @param tag，请求的tag
     * @param path，请求的url中除去主机地址和项目名后的path部分
     * @param params，请求参数
     * @param callback，请求回调
     */
    public void requestString(final Context tag, Enum method,String path, HttpParams params,final boolean loadShow,final com.android.ccq.easynet.callback.StringCallback
            callback) {
        if (!commonPrepare(callback)) return;
        getCallBack(tag,method,path,params,new com.lzy.okgo.callback.StringCallback() {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                callback.init(tag,loadShow);
            }
            @Override
            public void onSuccess(Response<String> response) {
                callback.doSussess(response.body());
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                // TODO: 2017/10/18 : 请求失败
                callback.doFailed(new ErrorResponse());
            }
            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });
    }



    /**
     * 请求数据并自动解析成实体类对象，回调方法执行在UI线程，可直接刷新UI
     * @param tag，请求的tag
     * @param path，请求的url中除去主机地址和项目名后的path部分
     * @param params，请求参数
     * @param loadShow，是否显示加载动画
     * @param callback，请求回调
     * @param <T>，Model泛型
     */
    public <T> void requestModel(final Context tag, Enum method,String path, HttpParams params,final boolean loadShow,final Class<T> tClass,final com.android.ccq.easynet.callback.ModelCallback<T> callback) {
        if (!commonPrepare(callback)) return;

        getCallBack(tag,method,path,params,new com.lzy.okgo.callback.StringCallback() {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                callback.init(tag,loadShow);
            }
            @Override
            public void onSuccess(Response<String> response) {
                try {

//                    T model = new Gson().fromJson(response.body(), callback.getGenericClass());
                    //添加Gson数据筛选器
                    T model =  new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                                .create().fromJson(response.body(), tClass);

                    if(null != model){
                         callback.doSussess(model);
                    }else{
                        callback.doFailed(new ErrorResponse());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.doFailed(new ErrorResponse());
                    // TODO: 2017/10/18 : 解析返回数据失败
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                callback.doFailed(new ErrorResponse());
                // TODO: 2017/10/18  : 请求失败
            }
            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });
    }



    /**
     * 请求Bitmap数据，回调方法执行在UI线程，可直接刷新UI
     * @param tag，请求的tag
     * @param path，请求的url中除去主机地址和项目名后的path部分
     * @param params，请求参数
     * @param callback，请求回调
     */
    public void requestBitmap(final Context tag, Enum method, String path, HttpParams params, final boolean loadShow, final BitmapCallback callback) {
        if (!commonPrepare(callback)) return;
        getCallBack(tag,method,path,params,new com.lzy.okgo.callback.BitmapCallback() {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<Bitmap, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                callback.init(tag,loadShow);
            }

            @Override
            public void onSuccess(Response<Bitmap> response) {
                callback.doSussess(response.body());
            }

            @Override
            public void onError(Response<Bitmap> response) {
                super.onError(response);
                callback.doFailed(new ErrorResponse());
                // TODO: 2017/10/18 :  请求失败
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });
    }

    /**
     * 请求File文件，回调方法执行在UI线程，可直接刷新UI
     *        tips：会将文件缓存至本地。若不要缓存文件，可调用requestRaw获取
     * @param tag，请求的tag
     * @param path，请求的url中除去主机地址和项目名后的path部分
     * @param params，请求参数
     * @param callback，请求回调，callback实例中的targetPath为文件保存路径，fileName为保存的文件名
     */
    public void requestFile(final Context tag, Enum method, String path, HttpParams params, final boolean loadShow, final FileCallback
            callback) {
        if (!commonPrepare(callback)) return;
        getCallBack(tag,method,path,params,new com.lzy.okgo.callback.FileCallback(callback.getTargetPath(), callback.getFileName()) {
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                callback.init(tag,loadShow);
            }

            @Override
            public void onSuccess(Response<File> response) {
                callback.doSussess(response.body());
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                callback.doFailed(new ErrorResponse());
                // TODO: 2017/10/18 : 请求失败
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                callback.onProgress(progress);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });
    }


    /**
     * 设置网络请求地址，当传入path以“http”开头，则直接返回path，否则将主机地址与path拼接返回
     *
     * @param path，传入路径
     * @return 设置完成的请求地址
     */
    private String genUrl(String path) {
        if (path.startsWith("http")) return path;
        return NetConstants.Base_Url + path;
    }
    /**
     * 通过tag取消请求
     * @param tag，请求时传入的tag对象
     */
    public void cancelByTag(Object tag){
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 取消所有请求
     */
    public void cancelAll(){
        OkGo.getInstance().cancelAll();
    }


    //Gson数据筛选器
    public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType == String.class) {
                return (TypeAdapter<T>) new StringNullAdapter();
            }
            return null;
        }
    }

    //字符串为null改为""
    public class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }
        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }


    /**
     * 自定义adapter，解决由于数据类型为Int,实际传过来的值为Float，导致解析出错的问题
     * 目前的解决方案为将所有Int类型当成Double解析，再强制转换为Int
     */
    public class Interger extends TypeAdapter<Number>{
        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }
            try {
                double i = in.nextDouble();//当成double来读取
                return (int) i;//强制转为int
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    };

}
