package com.android.ccq.easynet.config;

/**
 * Api请求回复代码
 *
 * @author bingyang
 * @date 2017/5/19 17:11
 */
public class ResponseCode {
    
    /**
     * 请求成功
     */
    public static final int SUCCESS = 1001;
    
    /**
     * 鉴权失败
     */
    public static final int ERROR_FAIL = 60000;
    
    /**
     * 鉴权TOKEN不存在
     */
    public static final int ERROR_TOKEN_NOT_EXIST = 60001;
    
    /**
     * 刷新token失败
     */
    public static final int ERROR_REFRESH_FAIL = 60002;
    
    /**
     * refresh token不合法
     */
    public static final int ERROR_REFRESH_TOKEN_ILLEGAL = 60003;
    
    /**
     * 下架信息1
     */
    public static final int SOLD_OUT1 = 30003;
    
    /**
     * 下架信息2
     */
    public static final int SOLD_OUT2 = 30041;
    
    /**
     * 下架信息3 视频状态错误
     */
    public static final int SOLD_OUT3 = 30011;
    
    /**
     * 下架信息4 剧集状态错误
     */
    public static final int SOLD_OUT4 = 30012;
}
