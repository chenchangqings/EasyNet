package com.android.ccq.easynet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.ccq.easynet.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

public class MainActivity extends AppCompatActivity {
    private HttpParams httpParams = new HttpParams();
    private int s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetHelper.getInstance().requestString(this, RequestMethod.GET,"https://www.sojson.com/open/api/weather/json.shtml?city=%E5%8C%97%E4%BA%AC", httpParams,true, new StringCallback() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {

            }
        });
    }
}
