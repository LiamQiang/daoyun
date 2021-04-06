package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.HttpUtils;
import com.example.trade.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegActivity extends Activity {

    RadioButton radioButton;
    String sex;
    EditText etUsername,etTel,etPassword,etPasswordConfirn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupsex);
        radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        etUsername=findViewById(R.id.regname);
        etPassword=findViewById(R.id.regnickname);


        findViewById(R.id.RegBack).setOnClickListener(v -> finish());
        findViewById(R.id.regbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegActivity.this, sex,Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                intent.setClass(RegActivity.this,MainActivity.class);
                startActivity(intent);
                /*String username=etUsername.getText().toString();
                String tel=etTel.getText().toString();
                String password=etPassword.getText().toString();
                String passwordConfirn=etPasswordConfirn.getText().toString();

                if (!Pattern.matches("^\\w{6,20}$",username)){
                    Toast.makeText(getBaseContext(),"用户名在6到20位之间",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!Pattern.matches("^1\\d{10}$",tel)){
                    Toast.makeText(getBaseContext(),"手机号格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!Pattern.matches("^\\w{6,20}$",password)){
                    Toast.makeText(getBaseContext(),"密码在6到20位之间",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!password.equals(passwordConfirn)){
                    Toast.makeText(getBaseContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestParams requestParams=new RequestParams();
                requestParams.add("username",username);
                requestParams.add("tel",tel);
                requestParams.add("password",password);
                HttpUtils.post(APIInterface.REG,requestParams,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if(response.getInt("code")==200){
                                Toast.makeText(getBaseContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                finish();
                            } else  Toast.makeText(getBaseContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });*/
            }


        });

    }
    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        switch (view.getId()) {
            case R.id.radiomen:
                if (isChecked) {
                    sex="1";
                }
                break;
            case R.id.radiofemale:
                if (isChecked) {
                    sex="2";
                }
                break;
            default:
                break;
        }
    }
}
