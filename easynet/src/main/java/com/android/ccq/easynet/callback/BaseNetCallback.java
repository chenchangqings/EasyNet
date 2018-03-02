package com.android.ccq.easynet.callback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.ccq.easynet.ErrorResponse;
import com.android.ccq.easynet.R;
import com.android.ccq.easynet.dialog.LoadingDialog;

/**
 * Created by Chenchangqing on 2017/12/14.
 */

public abstract class BaseNetCallback<T> implements BaseCallback<T>{
    protected LoadingDialog loadingDialog = null; //加载dialog
    protected Context context = null;

    public final void init(Context context,boolean loadShow){{
            this.context = context;
            if(loadShow){ //初始化加载动画
                loadingDialog = new LoadingDialog(context, R.style.loadingDialog);
                loadingDialog.show();
            }
        }
        onStart();
    }

    @Override
    public void onStart() {

    }

    //处理服务器成功返回参数
    public final void doSussess(T result){
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
        onSuccess(result);
    }

    public final void doFailed(ErrorResponse errorResponse){
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
        onError(errorResponse);
    }

    @Override
    public void onNetworkDisconnected() {

    }

    @Override
    public void onFinish() {

    }
}
