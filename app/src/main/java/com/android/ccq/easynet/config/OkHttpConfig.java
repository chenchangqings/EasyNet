package com.android.ccq.easynet.config;

import android.content.Context;

import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

public class OkHttpConfig {
    public static  final String LogTag = "OkGoClient";  //日志输出TAG
    public static  final long CONNECT_TIMEOUT = 30;  //连接超时时间
    public static  final long READ_TIMEOUT = 30;     //读取超时时间
    public static  final long WRITE_TIMEOUT = 30;    //写入超时时间


    //初始化okHttpClient
    public static OkHttpClient getOkHttpClient(Context context){
        HttpsUtils.SSLParams https = getHttps();
        return new OkHttpClient.Builder()
                //添加日志拦截器
//                .addInterceptor(getInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(getCookie(1,context))
                .sslSocketFactory(https.sSLSocketFactory,https.trustManager)
                .build();
    }


    //获取日志
    public static  HttpLoggingInterceptor getInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(LogTag);
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        return  loggingInterceptor;
    }

    //获取cookie
    public static  CookieJar getCookie(int type,Context context) {
        if (type == 1) {
            //使用sp保持cookie，如果cookie不过期，则一直有效
            return new CookieJarImpl(new SPCookieStore(context));
        } else if (type == 2) {
            //使用数据库保持cookie，如果cookie不过期，则一直有效
            return new CookieJarImpl(new DBCookieStore(context));
        } else{
            //使用内存保持cookie，app退出后，cookie消失
            return new CookieJarImpl(new MemoryCookieStore());
        }
    }

    //获取https设置
    public static  HttpsUtils.SSLParams getHttps(){
            //方法一：信任所有证书,不安全有风险
            return HttpsUtils.getSslSocketFactory();
            //方法二：自定义信任规则，校验服务端证书
            //return HttpsUtils.getSslSocketFactory(new SafeTrustManager());
            //方法三：使用预埋证书，校验服务端证书（自签名证书）
            //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
            //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
            //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
    }


    public static  HttpHeaders getHeades(){
        /* 创建OkGO请求头 */
        HttpHeaders headers = new HttpHeaders();
        headers.put("Accept-Encoding", "identity");
        return headers;
    }


    //获取全局参数
    public static  HttpParams getHttpParams(){
        HttpParams httpParams = new HttpParams();
        return httpParams;
    }


}
