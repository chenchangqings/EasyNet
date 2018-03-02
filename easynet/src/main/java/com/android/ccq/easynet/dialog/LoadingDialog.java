package com.android.ccq.easynet.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.android.ccq.easynet.R;
import com.victor.loading.rotate.RotateLoading;


/**
 * Created by Chenchangqing on 2017/11/6.
 */

public class LoadingDialog extends Dialog {
    private Context context;
    private RotateLoading rotateLoading;

    public LoadingDialog(@NonNull Context context, int themeId) {
        super(context,themeId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化界面控件
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.item_loading_dialog, null);
        setContentView(view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
//        setCancelable(false);
        rotateLoading = view.findViewById(R.id.v_load);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
//        dialogWindow.setWindowAnimations(R.style.animDialog);
    }

    @Override
    public void show() {
        if(context!= null && !((Activity)context).isFinishing()){
            super.show();
            rotateLoading.start();
        }
    }

    @Override
    public void dismiss() {
        if(context!= null && !((Activity)context).isFinishing()){
            super.dismiss();
            rotateLoading.stop();
        }
    }
}