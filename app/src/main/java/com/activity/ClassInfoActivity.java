package com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.HttpUtils;
import com.MyApplication;
import com.bean.QRCodeUtil;
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

public class ClassInfoActivity extends Activity  {
    TextView className,tecName,semester,schoolName,title,tvclassId,nonTvClassId,qiuteClass;
    Button joinClass,stuList;
    int classId,flag,allow_join,end,tec_course_id,tec_id;
    RelativeLayout tecCheck;
    CheckBox checkbox;
    ImageView classQRcode;
    Bitmap qrcode_bitmap;
    String tvclassname,tvsemester,tvschoolName,tecname,tvClassName;
    int courseId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_classinfo);
        nonTvClassId=findViewById(R.id.nontvclassId);
        stuList=findViewById(R.id.stuLsit);
        qiuteClass=findViewById(R.id.quiteClass);


        className=findViewById(R.id.className);
        checkbox=findViewById(R.id.checkBox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    allow_join=1;
                }else {
                    allow_join = 0;
                    //Toast.makeText(getApplicationContext(), "unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        title=findViewById(R.id.classInfoTitle);
        tvclassId=findViewById(R.id.classId);
        classQRcode=findViewById(R.id.classQRcode);
        tecCheck=findViewById(R.id.tecCheck);
        schoolName=findViewById(R.id.schoolNmae);
        tecName=findViewById(R.id.tecName);
        semester=findViewById(R.id.semester);
        joinClass=findViewById(R.id.joinClasss);
        joinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Joinclass();
            }
        });

        courseId=getIntent().getExtras().getInt("classId");


        allow_join=getIntent().getExtras().getInt("allow_join");
        if (allow_join==1){
            checkbox.setChecked(true);
        }else checkbox.setChecked(false);
        end=getIntent().getExtras().getInt("end");
        flag=getIntent().getExtras().getInt("flag");
         tvclassname=getIntent().getExtras().getString("name");
         tvsemester=getIntent().getExtras().getString("semester");
         tvschoolName=getIntent().getExtras().getString("schoolName");
         className.setText(getIntent().getExtras().getString("courseName"));
        HttpUtils.get(APIInterface.COURSE_INFO+"info/"+MyApplication.getUser().getUid()+"/"+courseId,MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    tec_course_id=response.getJSONObject("object").getInt("id");
                    classId=tec_course_id;
                    tvclassId.setText(String.valueOf(tec_course_id));
                    qrcode_bitmap = QRCodeUtil.createQRCodeBitmap(String.valueOf(tec_course_id), 150, 150, "UTF-8",
                            "H", "1",  Color.BLACK,  Color.WHITE);
                    classQRcode.setImageBitmap(qrcode_bitmap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        stuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                Bundle bundle=new Bundle();
                bundle.putInt("courseID",courseId);
                it.putExtras(bundle);
                it.setClass(ClassInfoActivity.this,StuListActivity.class);
                startActivity(it);
            }
        });
        tecName.setText(tvclassname);
        semester.setText(tvsemester);
        schoolName.setText(tvschoolName);

        if (flag==0){  //学生视角
            qiuteClass.setText("退出班课");
            title.setText("班课信息");
            tecCheck.setVisibility(View.GONE);
            //endClass.setVisibility(View.GONE);
            tecName.setText(getIntent().getExtras().getString("tecName"));
            tvclassId.setText(String.valueOf(getIntent().getExtras().getInt("classId")));
            qrcode_bitmap = QRCodeUtil.createQRCodeBitmap(String.valueOf(tec_course_id), 150, 150, "UTF-8",
                    "H", "1",  Color.BLACK,  Color.WHITE);
            classQRcode.setImageBitmap(qrcode_bitmap);
            qiuteClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpUtils.delete(APIInterface.JOIN_COURSE+"/"+MyApplication.getUser().getUid()+"/"+courseId, MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.getInt("code")==200){
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                }else if (response.getInt("code")==500)
                                    Toast.makeText(getApplicationContext(),  response.getString("message"), Toast.LENGTH_SHORT).show();
                                else Toast.makeText(getApplicationContext(),  response.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }

                        @SuppressLint("WrongConstant")
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            try {
                                Toast.makeText(getApplicationContext(), errorResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

        }else if (flag==1){  //老师视角
            title.setText("班课信息");
            qiuteClass.setText("结束班课");
            qiuteClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpUtils.put(APIInterface.COURSE_INFO+"activity/"+classId+"/0", MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.getInt("code")==200){
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                }else if (response.getInt("code")==500)
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                else Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }

                        @SuppressLint("WrongConstant")
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            try {
                                Toast.makeText(getApplicationContext(), errorResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });



        }else if (flag==2){
            qiuteClass.setVisibility(View.GONE);
            //tec_course_id=getIntent().getExtras().getInt("classId");
            nonTvClassId.setVisibility(View.GONE);
            tvclassId.setText(String.valueOf(courseId));
            schoolName.setText(getIntent().getExtras().getString("schoolName"));
            tec_id=getIntent().getExtras().getInt("teacherId");
            HttpUtils.get(APIInterface.COURSE_INFO+"teacherName/"+courseId,MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        tecname=response.getString("object");
                        tecName.setText(tecname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            tecCheck.setVisibility(View.GONE);
            stuList.setVisibility(View.GONE);
        }




    }



    public void Joinclass() {
        if (flag==0){
            finish();
        }else if (flag==1){  //更新课程信息
            /*if (allow_join==1){
                checkbox.setChecked(true);
            }else checkbox.setChecked(false);*/

            /*JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("className",className.getText().toString());
                jsonObject.put("semester",semester.getText().toString());
                jsonObject.put("schoolName",schoolName.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ByteArrayEntity entity = null;
            try {
                entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/

            HttpUtils.put(APIInterface.COURSE_ALLOW+classId+"/"+allow_join, MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if (response.getInt("code")==200){
                            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        }else if (response.getInt("code")==500)
                            Toast.makeText(getApplicationContext(), "500 error", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finish();
                }

                @SuppressLint("WrongConstant")
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    try {
                        Toast.makeText(getApplicationContext(), errorResponse.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {  //加入班课
            tec_course_id=getIntent().getExtras().getInt("classId");
            if (end==0){
                Toast.makeText(getApplicationContext(), "该班课已结束", Toast.LENGTH_SHORT).show();
                finish();
            }else if (allow_join==0) Toast.makeText(getApplicationContext(), "该班课不允许加入", Toast.LENGTH_SHORT).show();
            else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("teacherCourseId",tec_course_id);

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
                HttpUtils.post(getBaseContext(), APIInterface.JOIN_COURSE, MyApplication.getUser().getToken(),entity,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getInt("code")==200){
                                Toast.makeText(getApplicationContext(), "加入班课成功", Toast.LENGTH_SHORT).show();
                            }else if (response.getInt("code")==500)
                                Toast.makeText(getApplicationContext(), "已加入班课", Toast.LENGTH_SHORT).show();
                            else Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }

                    @SuppressLint("WrongConstant")
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        try {
                            Toast.makeText(getApplicationContext(), errorResponse.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
