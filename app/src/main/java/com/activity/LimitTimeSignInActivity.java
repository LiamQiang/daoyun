package com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.trade.R;

public class LimitTimeSignInActivity extends Activity {
    Button btGiveupSign,btEndSing;
    TextView refresh,signedNum,unSignNum,startTime,endTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.limitedtime_singin);
        initUI();
    }
    public void initUI(){
        btEndSing=findViewById(R.id.btEndSignin);
        btGiveupSign=findViewById(R.id.btGiveupSignin);
        refresh=findViewById(R.id.tvrefresh);
        signedNum=findViewById(R.id.signin_number);
        unSignNum=findViewById(R.id.unSignin_number);
        startTime=findViewById(R.id.Signin_Starttime);
        //endTime=findViewById(R.id.Signin_Endtime);
    }
}
