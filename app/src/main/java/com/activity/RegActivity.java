package com.activity;

import android.annotation.SuppressLint;
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
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class RegActivity extends Activity {

    RadioButton radioButtonrmen,radioButtonwomen,radioButtontec,radioButtonstu;
    String phone;
    int role,sex;
    EditText etUsername,etTel,etPassword,etPasswordConfirn,etSchool,etDept;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        /*RadioGroup radioGroupsex = (RadioGroup)findViewById(R.id.radioGroupsex);
        RadioGroup radioGrouprole = (RadioGroup)findViewById(R.id.radioGrouprole);
        radioButtonrmen = findViewById(R.id.radiomen);
        radioButtonwomen = findViewById(R.id.radiofemale);
        radioButtontec = findViewById(R.id.radiotec);
        radioButtonstu = findViewById(R.id.radiostu);*/
        /*etUsername=findViewById(R.id.regname);
        etPassword=findViewById(R.id.regnickname);
        etSchool=findViewById(R.id.regschool);
        etDept=findViewById(R.id.regdept);*/
        phone=getIntent().getExtras().getString("phone");



        findViewById(R.id.RegBack).setOnClickListener(v -> finish());
        findViewById(R.id.regbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(RegActivity.this, sex,Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                intent.setClass(RegActivity.this,MainActivity.class);
                startActivity(intent);*/
                //String phone=etUsername.getText().toString();
                //String tel=etTel.getText().toString();
                String password=etPassword.getText().toString();
                /*String school=etSchool.getText().toString();
                String dept=etDept.getText().toString();*/
                //String passwordConfirn=etPasswordConfirn.getText().toString();

                /*if (!Pattern.matches("^\\w{6,20}$",phone)){
                    Toast.makeText(getBaseContext(),"用户名在6到20位之间",Toast.LENGTH_SHORT).show();
                    return;
                }
                else */
                if (!Pattern.matches("^1\\d{10}$",phone)){
                    Toast.makeText(getBaseContext(),"手机号格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!Pattern.matches("^\\w{6,20}$",password)){
                    Toast.makeText(getBaseContext(),"密码在6到20位之间",Toast.LENGTH_SHORT).show();
                    return;
                }
                /*else if (!password.equals(passwordConfirn)){
                    Toast.makeText(getBaseContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }*/

                RequestParams requestParams=new RequestParams();
                requestParams.put("password",password);
                requestParams.put("phonenumber",phone);
                requestParams.put("role", 0);
                //requestParams.add("tel",tel);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("phonenumber",phone);
                    jsonObject.put("password",password);
                    /*jsonObject.put("role",role);
                    jsonObject.put("sex",sex);
                    jsonObject.put("school",school);
                    jsonObject.put("department",dept);*/

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

                HttpUtils.post(getBaseContext(),APIInterface.REG,entity,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if(response.getInt("code")==200){
                                Toast.makeText(getBaseContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                finish();
                            } else  if(response.getInt("code")==201){
                                Toast.makeText(getBaseContext(),"已注册",Toast.LENGTH_SHORT).show();
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


        });

    }
  /*  public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        switch (view.getId()) {
            case R.id.radiostu:
                if (isChecked){
                    role=0;
                    Toast.makeText(getBaseContext(),""+role,Toast.LENGTH_SHORT).show();
                }

                break;
            case  R.id.radiotec:
                if (isChecked){
                    role=1;
                    Toast.makeText(getBaseContext(),""+role,Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.radiomen:
                if (isChecked) {
                    sex=1;
                    Toast.makeText(getBaseContext(),""+sex,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radiofemale:
                if (isChecked) {
                    sex=2;
                    Toast.makeText(getBaseContext(),""+sex,Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }*/
}
