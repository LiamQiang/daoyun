package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.HttpUtils;
import com.example.trade.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class RegPhoneActivity extends Activity implements View.OnClickListener {

    EditText phone;
    TextView tip;
    String thirdPartyId;
    int bool;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_phone);
        initUI();

    }
    private void initUI(){
        Button regbutton=findViewById(R.id.regbutton);
        phone=findViewById(R.id.regtel);
        tip=findViewById(R.id.phoneTip);
        regbutton.setOnClickListener(this);
        bool=getIntent().getExtras().getInt("id");
        if(bool==0){
            tip.setText("登录");
        }else if(bool==2)
            tip.setText("忘记密码");
        else if(bool==3) {
            tip.setText("绑定已有帐号");
            regbutton.setText("绑定账号");
            thirdPartyId=getIntent().getExtras().getString("thirdPartyId");
        }
    }

    @Override
    public void onClick(View view) {
        if(bool==3){
            String tel=phone.getText().toString();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("thirdPartyId",thirdPartyId);
                jsonObject.put("userid",tel);

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
            HttpUtils.post(getBaseContext(),APIInterface.GITHUB_BIND,entity,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if(response.getInt("code")==200){
                            Toast.makeText(getBaseContext(),response.getString("绑定成功"),Toast.LENGTH_SHORT).show();
                            Intent i=new Intent();
                            i.setClass(RegPhoneActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }else {
            Intent intent=new Intent();
            Bundle extras=new Bundle();
            String tel=phone.getText().toString();
            if (!Pattern.matches("^1\\d{10}$",tel)){
                Toast.makeText(getBaseContext(),"手机号格式不正确",Toast.LENGTH_SHORT).show();
                return;
            }



            HttpUtils.get(APIInterface.SMS+tel,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if(response.getInt("code")==200){
                            Toast.makeText(getBaseContext(),"发送成功",Toast.LENGTH_SHORT).show();
                            extras.putString("tel",tel);
                            extras.putInt("id",bool);
                            intent.putExtras(extras);
                            intent.setClass(RegPhoneActivity.this,RegVerifyActivity.class);
                            startActivity(intent);
                            finish();
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
}
