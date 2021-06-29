package com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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


public class CreateClassActivity  extends Activity implements View.OnClickListener {

    Button Create;
    Spinner spinner;
    EditText classname,coursename;
    TextView schoolname;
    String semester;
    TextView school,dept;
    int schoolFlag=-1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        initUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btCreateClass:
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("className",classname.getText().toString());
                    jsonObject.put("courseName",coursename.getText().toString());
                    jsonObject.put("schoolName",schoolname.getText().toString());
                    jsonObject.put("semester",semester);
                    jsonObject.put("departmentName",dept.getText().toString());

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
                HttpUtils.post(getBaseContext(),APIInterface.ADD_COURSE, MyApplication.getUser().getToken(),entity,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            int id=response.getJSONObject("object").getInt("id");
                            Bundle bundle=new Bundle();
                            Intent it=new Intent();
                            bundle.putString("id", String.valueOf(id));
                            it.putExtras(bundle);
                            it.setClass(CreateClassActivity.this,GenerateQRcodeActivity.class);
                            startActivity(it);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
                finish();
//        Intent intent=new Intent();
//        intent.setClass(this,RegPhoneActivity.class);
//        startActivity(intent);
                break;
            case R.id.etSchoolName:
                AlertDialog.Builder choiceBuilder = new AlertDialog.Builder(this);
                choiceBuilder.setCancelable(false);
                choiceBuilder
                        .setTitle("选择学校")
                        .setSingleChoiceItems(new String[]{"福州大学", "福建师范大学"}, -1,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0://福大
                                               schoolname.setText("福州大学");
                                                schoolFlag=0;
                                                break;
                                            case 1:// 师大
                                                schoolname.setText("福建师范大学");
                                                schoolFlag=1;
                                                break;
                                            default:
                                                break;
                                        }
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                choiceBuilder.create();
                choiceBuilder.show();
                break;
            case R.id.etDept:
                if (schoolFlag==-1)
                    Toast.makeText(this, "抢先选择学校", Toast.LENGTH_SHORT).show();
                else if (schoolFlag==0){
                    AlertDialog.Builder deptBuilder = new AlertDialog.Builder(this);
                    deptBuilder.setCancelable(false);
                    deptBuilder
                            .setTitle("选择学院")
                            .setSingleChoiceItems(new String[]{"计算机学院", "土木工程学院"}, -1,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0://福大
                                                    dept.setText("计算机学院");
                                                    break;
                                                case 1:// 师大
                                                    dept.setText("土木工程学院");
                                                    break;
                                                default:
                                                    break;
                                            }
                                            dialog.dismiss();
                                        }
                                    })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    deptBuilder.create();
                    deptBuilder.show();
                }
                else if (schoolFlag==1){
                    AlertDialog.Builder deptBuilder = new AlertDialog.Builder(this);
                    deptBuilder.setCancelable(false);
                    deptBuilder
                            .setTitle("选择学院")
                            .setSingleChoiceItems(new String[]{"外国语学院", "化学学院"}, -1,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0://福大
                                                    dept.setText("外国语学院");
                                                    break;
                                                case 1:// 师大
                                                    dept.setText("化学学院");
                                                    break;
                                                default:
                                                    break;
                                            }
                                            dialog.dismiss();
                                        }
                                    })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    deptBuilder.create();
                    deptBuilder.show();
                }
                break;
        }
    }

    public void  initUI(){
        dept=findViewById(R.id.etDept);
        dept.setOnClickListener(this);
        Create=findViewById(R.id.btCreateClass);
        Create.setOnClickListener(this);
        spinner=(Spinner)findViewById(R.id.term);
        classname=findViewById(R.id.etClassName);
        coursename=findViewById(R.id.etCourseName);
        schoolname=findViewById(R.id.etSchoolName);
        schoolname.setOnClickListener(this);
        String[] mItems = getResources().getStringArray(R.array.auto);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner .setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] autoInfo = getResources().getStringArray(R.array.auto);
                semester=autoInfo[i];
                //Toast.makeText(CreateClassActivity.this, "你点击的是:"+autoInfo[i], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
