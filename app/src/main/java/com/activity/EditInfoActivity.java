package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.HttpUtils;
import com.MyApplication;
import com.example.trade.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class EditInfoActivity extends Activity implements  View.OnClickListener {
    RadioButton radioButtonrmen,radioButtonwomen,radioButtontec,radioButtonstu;
    String phone;
    int role,sex;
    EditText etUsername,etTel,etPassword,etPasswordConfirn,etSchool,etDept;
    Button btconfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        btconfirm=findViewById(R.id.regbutton);
        btconfirm.setOnClickListener(this);
        RadioGroup radioGroupsex = (RadioGroup)findViewById(R.id.radioGroupsex);
        RadioGroup radioGrouprole = (RadioGroup)findViewById(R.id.radioGrouprole);
        radioButtonrmen = findViewById(R.id.radiomen);
        radioButtonwomen = findViewById(R.id.radiofemale);
        radioButtontec = findViewById(R.id.radiotec);
        radioButtonstu = findViewById(R.id.radiostu);
        etUsername=findViewById(R.id.nickname);
        etPassword=findViewById(R.id.password);
        etSchool=findViewById(R.id.school);
        etDept=findViewById(R.id.dept);
        etTel=findViewById(R.id.phoneNumber);
        HttpUtils.get(APIInterface.USER_INFO, MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("code")==200){
                        etUsername.setText(response.getJSONObject("object").getString("nickname"));
                        etDept.setText(response.getJSONObject("object").getString("department"));
                        role=response.getJSONObject("object").getInt("role");
                        sex=response.getJSONObject("object").getInt("sex");
                        etSchool.setText(response.getJSONObject("object").getString("school"));
                        etTel.setText(response.getJSONObject("object").getString("phonenumber"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
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
    }

    @Override
    public void onClick(View view) {
        RequestParams requestParams=new RequestParams();
        requestParams.put("password",etPassword.getText().toString());
        requestParams.put("phonenumber",etTel.getText().toString());
        requestParams.put("role", role);
        requestParams.put("sex", sex);
        requestParams.put("school",etSchool.getText().toString());
        requestParams.put("department",etDept.getText().toString());
        HttpUtils.put(APIInterface.EDIT_INFO,MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("code")==200){
                        MyApplication.getUser().setToken("");
                        MyApplication.getUser().setRole(2);
                        MyApplication.getUser().setNickname("");
                        MyApplication.getUser().setUid(0);
                        MyApplication.getUser().setLogin(false);
                        Intent it=new Intent();
                        it.setClass(EditInfoActivity.this,LoginActivity.class);
                        Toast.makeText(getBaseContext(),"修改成功，请重新登陆",Toast.LENGTH_SHORT).show();
                        startActivity(it);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //requestParams.add("tel",tel);
    }
}
