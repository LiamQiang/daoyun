package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.HttpUtils;
import com.MyApplication;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.trade.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class StuSign extends Activity implements View.OnClickListener {

    Button click;
    int teacherCourseId;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    double latitude,longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_signin);
        click=findViewById(R.id.btnOne);
        click.setOnClickListener(this);
        initUI();
    }

    public  void initUI(){
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        option.setNeedNewVersionRgc(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        teacherCourseId=getIntent().getExtras().getInt("teacherCourseId");
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(StuSign.this,"lon"+longitude+"lat"+latitude,Toast.LENGTH_SHORT).show();
        mLocationClient.stop();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("latitude",latitude);
            jsonObject.put("longitude",longitude);
            jsonObject.put("teacherCourseId",teacherCourseId);
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
        HttpUtils.post(getBaseContext(), APIInterface.STU_SIGN, MyApplication.getUser().getToken(),entity,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("code")==200){
                        Toast.makeText(StuSign.this,"签到成功",Toast.LENGTH_SHORT).show();
                        Intent it=new Intent();
                        //Bundle bundle=new Bundle();
                        //bundle.putInt("flag",1);
                        //it.putExtras(bundle);
                        it.setClass(StuSign.this,LimitTimeSign.class);
                        startActivity(it);
                        finish();
                    }
                    else Toast.makeText(StuSign.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();    //获取纬度信息
            longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            int errorCode = location.getLocType();
            //Toast.makeText(AsignSigninActivity.this,"lat"+latitude+"lon"+longitude,Toast.LENGTH_SHORT).show();
        }
    }
}
