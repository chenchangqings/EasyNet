package com.android.ccq.easynet;

import android.app.Application;

import com.android.ccq.easynet.config.OkHttpConfig;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       OkGoClient.getInstence().setLog("like").init(this, OkHttpConfig.getOkHttpClient(this));
    }
}
