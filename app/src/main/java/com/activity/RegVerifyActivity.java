package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.HttpUtils;
import com.MyApplication;
import com.bean.User;
import com.example.trade.R;
import com.github.Main;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegVerifyActivity  extends Activity implements View.OnClickListener {
    TextView phone,tip;
    EditText smsCode;
    String tel;
    String code;
    Button regbutton,reSendButton;
    int T=20;
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_verify);
        initUI();


    }

    private void initUI(){
        new Thread(new MyCountDownTimer()).start();
        regbutton=findViewById(R.id.regbutton);
        reSendButton=findViewById(R.id.reSendButton);
        reSendButton.setOnClickListener(this);
        phone=findViewById(R.id.verivfTel);
        smsCode=findViewById(R.id.regtel);
        tip=findViewById(R.id.yanzhengma);
        int bool=getIntent().getExtras().getInt("id");
        tel=getIntent().getExtras().getString("tel");
        if(bool==1){
            phone.setText(tel);
        }else {
            tip.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
        }
        regbutton.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        //Toast.makeText(getApplicationContext(), tel, Toast.LENGTH_SHORT).show();
        switch (view.getId()){
            case R.id.regbutton:
                code=smsCode.getText().toString();
                if (code.isEmpty()){
                    Toast.makeText(getBaseContext(),"请输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(getIntent().getExtras().getInt("id")==1){  //短信注册
                    HttpUtils.get(APIInterface.SMS+tel+"/"+code,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.getInt("code")==200){
                                    JSONObject jsonObject1 = new JSONObject();
                                    try {
                                        jsonObject1.put("phonenumber",tel);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    ByteArrayEntity entity1 = null;
                                    try {
                                        entity1 = new ByteArrayEntity(jsonObject1.toString().getBytes("UTF-8"));
                                        entity1.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    HttpUtils.post(getBaseContext(),APIInterface.QUICK_REG,entity1,new JsonHttpResponseHandler(){
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                            super.onSuccess(statusCode, headers, response);
                                            try {
                                                if(response.getInt("code")==200){
                                                    Toast.makeText(getBaseContext(),"注册成功",Toast.LENGTH_SHORT).show();

                                                    JSONObject jsonObject1 = new JSONObject();
                                                    try {
                                                        jsonObject1.put("phonenumber",tel);

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    ByteArrayEntity entity1 = null;
                                                    try {
                                                        entity1 = new ByteArrayEntity(jsonObject1.toString().getBytes("UTF-8"));
                                                        entity1.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                                                    } catch (UnsupportedEncodingException e) {
                                                        e.printStackTrace();
                                                    }
                                                    HttpUtils.post(getBaseContext(),APIInterface.QUICK_LOG,entity1,new JsonHttpResponseHandler(){
                                                        @Override
                                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                            super.onSuccess(statusCode, headers, response);
                                                            try {
                                                                MyApplication.getUser().setToken(response.getJSONObject("object").getString("token"));
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Intent intent=new Intent();
                                                            intent.setClass(RegVerifyActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    });

                                                } else
                                                    Toast.makeText(getBaseContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                            super.onFailure(statusCode, headers, throwable, errorResponse);
                                        }
                                    });
                                }
                                else Toast.makeText(getBaseContext(),response.getString("验证码不正确"),Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });
                }else {   //短信登录
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("phonenumber",tel);
                        jsonObject.put("smsCode",code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ByteArrayEntity entity = null;
                    try {
                        entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
                        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    HttpUtils.post(getBaseContext(),APIInterface.SMS_LOGIN,entity,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if(response.getInt("code")==200){
                                    Toast.makeText(getBaseContext(),"登录成功",Toast.LENGTH_SHORT).show();
                                    try {
                                        MyApplication.getUser().setToken(response.getJSONObject("object").getString("token"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent=new Intent();
                                    intent.setClass(RegVerifyActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if(response.getInt("code")==500){
                                    Toast.makeText(getBaseContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(getBaseContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });

                }
                break;
            case R.id.reSendButton:
                new Thread(new MyCountDownTimer()).start();
                HttpUtils.get(APIInterface.SMS+tel,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if(response.getInt("code")==200){
                                Toast.makeText(getBaseContext(),"发送成功",Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(getBaseContext(),"发送失败",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });
        }
    }

    class MyCountDownTimer implements Runnable{

        @Override
        public void run() {
            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reSendButton.setClickable(false);
                        reSendButton.setBackground(getResources().getDrawable(R.drawable.bg_button_dark));
                        reSendButton.setText(T + "s后可重新发送");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    reSendButton.setClickable(true);
                    reSendButton.setBackground(getResources().getDrawable(R.drawable.bg_button));
                    reSendButton.setText("重新发送验证码");
                }
            });
            T = 10; //最后再恢复倒计时时长
        }
    }

}
