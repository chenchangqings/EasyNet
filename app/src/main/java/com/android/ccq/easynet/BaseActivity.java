package com.android.ccq.easynet;

import android.support.v7.app.AppCompatActivity;
import com.android.ccq.easynet.utils.Recover;

public class BaseActivity extends AppCompatActivity {
    public Recover recover = new Recover();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recover.cancelCall();
    }
}
