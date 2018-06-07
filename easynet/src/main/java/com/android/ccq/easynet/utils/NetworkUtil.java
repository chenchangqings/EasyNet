package com.android.ccq.easynet.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
        * 网络工具类
        * Created by chenziqiang on 16/4/21.
        */
public class NetworkUtil {
    private volatile static  NetworkUtil instance;

    public NetworkUtil(){
        instance=this;
    }
    public synchronized static NetworkUtil getInstance() {
        if (instance == null) {
            synchronized (NetworkUtil.class) {
                instance = instance == null ? new NetworkUtil() : instance;
            }
        }
        return instance;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null) {
            return activeInfo.isConnected();
        }
        return false;
    }
//    public static boolean isNetConnected() {
//        ConnectivityManager cm = (ConnectivityManager) JXHDCApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = cm != null ? cm.getActiveNetworkInfo() : null;
//        return info != null && info.isConnected();
//    }

    public static boolean isWifiConnected(Context context) {
        boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            isConnected = info.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    public static boolean isMobileConnected(Context context) {
        boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            isConnected = info.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }


}