package com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.HttpUtils;
import com.MyApplication;
import com.example.trade.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LimitTimeSign extends Activity {

    TextView tvStartTime,tvEndTime;
    Button endSign,GiveupSign;
    int flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.limitedtime_singin);
        tvStartTime=findViewById(R.id.signin_Starttime);
        tvEndTime=findViewById(R.id.signin_Endtime);
        //flag=getIntent().getExtras().getInt("flag");
        initUI();
    }

    public void initUI(){
        HttpUtils.get(APIInterface.SIGN_INFO, MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("code")==200){


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
