package com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.HttpUtils;
import com.MyApplication;
import com.example.trade.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JoinClass extends Activity implements View.OnClickListener {
    EditText classNum;
    int classid;
    TextView join;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);
        classNum=findViewById(R.id.classNum);
        join=findViewById(R.id.join);
        join.setOnClickListener(this);
        initUI();
    }

    public void initUI(){

    }
    @Override
    public void onClick(View view) {
        if(!classNum.getText().toString().isEmpty()){
            try{
                classid= Integer.parseInt(classNum.getText().toString());
                Bundle bundle=new Bundle();
                Intent it=new Intent();
                HttpUtils.get(APIInterface.COURSE_INFO+classid,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getInt("code")==200){
                                bundle.putString("name",response.getJSONObject("object").getString("className"));
                                bundle.putString("schoolName",response.getJSONObject("object").getString("schoolName"));
                                bundle.putString("semester",response.getJSONObject("object").getString("semester"));
                                bundle.putInt("teacherId",response.getJSONObject("object").getInt("teacherId"));
                                bundle.putInt("classId",classid);
                                bundle.putInt("flag",2);
                                bundle.putInt("allow_join",response.getJSONObject("object").getInt("allowJoin"));
                                bundle.putInt("end",response.getJSONObject("object").getInt("activity"));
                                it.putExtras(bundle);
                                it.setClass(JoinClass.this,ClassInfoActivity.class);
                                startActivity(it);
                                finish();
                            }
                            else Toast.makeText(getApplicationContext(), "班课号不存在", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "班课号为数字格式,请重新输入", Toast.LENGTH_SHORT).show();
            }

        } else
            Toast.makeText(getApplicationContext(), "请输入班课号", Toast.LENGTH_SHORT).show();

    }
}
