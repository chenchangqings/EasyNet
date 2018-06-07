package com.android.ccq.easynet.config;

/**
 * Created by Chenchangqing on 2018/3/2.
 */

public class NetConstants {
    public final static int Error_Code_Base = 4001;  //请求异常
    public final static int Error_Code_Net = Error_Code_Base + 1;  //网络异常
    public final static int Error_Code_Server = Error_Code_Base + 2;  //服务器异常
    public final static int Error_Code_Analysis = Error_Code_Base + 3;  //解析异常
    public final static int Error_Code_Null = Error_Code_Base + 4;  //空指针异常
}
