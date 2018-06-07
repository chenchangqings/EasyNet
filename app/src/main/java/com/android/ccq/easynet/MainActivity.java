package com.android.ccq.easynet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ccq.easynet.api.VideoApi;
import com.android.ccq.easynet.callbcak.IRequestCallBack;
import com.android.ccq.easynet.config.RequsetCode;
import com.android.ccq.easynet.reponse.DogBean;
import com.android.ccq.easynet.response.ErrorResponse;
import com.android.ccq.easynet.response.SuccessResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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


    //第一种请求方式：json作为参数
    private void getJson(){
//        DogParam param = new DogParam();
//        param.setHot(99);
//        param.setSkin("蓝色");
        VideoApi.build().getVideoList(null,recover,this);
    }

    //第二种请求方式：HttpParams作为参数:适合简单请求
    private void getParams(){
        HttpParams param = new HttpParams();
        param.put("type",2);
        param.put("limit",10);
        param.put("offset",0);
        VideoApi.build().getVideoList(param,recover,this);
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
