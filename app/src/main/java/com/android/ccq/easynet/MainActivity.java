package com.android.ccq.easynet;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ccq.easynet.api.BaseRequset;
import com.android.ccq.easynet.api.TestApi;
import com.android.ccq.easynet.callbcak.IRequestCallBack;
import com.android.ccq.easynet.config.RequsetCode;
import com.android.ccq.easynet.config.UrlConfig;
import com.android.ccq.easynet.reponse.DogBean;
import com.android.ccq.easynet.response.ErrorResponse;
import com.android.ccq.easynet.response.SuccessResponse;
import com.lzy.okgo.model.HttpParams;

public class MainActivity extends BaseActivity implements IRequestCallBack {
    private Button btnClick;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClick = findViewById(R.id.btn);
        textView =  findViewById(R.id.txt);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJson();
            }
        });
    }

    private void Test(){
        Toast.makeText(this,"测试",Toast.LENGTH_SHORT).show();
    }

    //第一种请求方式：json作为参数
    private void getJson(){
//        DogParam param = new DogParam();
//        param.setHot(99);
//        param.setSkin("蓝色");
        BaseRequset.build().create(TestApi.class).getVideo(null,this);

    }

    IRequestCallBack iRequestCallBack = new IRequestCallBack() {
        @Override
        public void onSuccess(int code, Object object, SuccessResponse response) {

        }

        @Override
        public void onError(ErrorResponse response) {

        }
    };




    //第二种请求方式：HttpParams作为参数:适合简单请求
    private void getParams(){
        HttpParams param = new HttpParams();
        param.put("type",2);
        param.put("limit",10);
        param.put("offset",0);
//        VideoApi.build().getVideoList(param,recover,this);
    }



    @Override
    public void onSuccess(int code, Object object, SuccessResponse response) {
        if(code == RequsetCode.request_getVideo){
            DogBean info = (DogBean)object;
            Toast.makeText(this,response.getJson(),Toast.LENGTH_SHORT).show();
            textView.setText(response.getJson());
        }


    }

    @Override
    public void onError(ErrorResponse response) {
        Toast.makeText(this,response.getErrorMsg(),Toast.LENGTH_SHORT).show();
        textView.setText(response.getErrorMsg());
    }
}
