package com.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.APIInterface;
import com.CommomList;
import com.CommonBaseAdapter;
import com.CommonViewHolder;
import com.HttpUtils;
import com.MyApplication;
import com.MyListView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;

import com.baidu.location.LocationClientOption;

import com.bean.SignRecord;
import com.bean.object;
import com.example.trade.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.HttpDelete;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class AsignSigninActivity extends Activity implements View.OnClickListener {
    private BaseAdapter adapter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    RelativeLayout timeLimited,handMake;
    ListView mlv;
    double latitude,longitude;
    ImageView oneTime,limitTime;
    int couresId,signId;
    TextView refresh,endSign,state;
    CommomList<List<SignRecord>> mcommomList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_signin_page);
        initUI();
    }

    /*public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 0, "已签到");
        menu.add(0, 2, 0, "未签到");
        super.onCreateContextMenu(menu, v, menuInfo);
    }*/

   /* @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Toast.makeText(AsignSigninActivity.this,"1",Toast.LENGTH_SHORT).show();

                break;
            case 2:
                Toast.makeText(AsignSigninActivity.this,"2",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }*/

    private List<String> getHSVDataCreate(){
        List<String> data=new ArrayList<String>();
        data.add("林炜智");
        data.add("林炜智1");
        return  data;
    }
    public void initUI(){
        couresId=getIntent().getExtras().getInt("id");
        HttpUtils.get(APIInterface.SIGN_RECORD+couresId,MyApplication.getUser().getToken(),responseHandler);
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
        //注册监听函数
        mlv = findViewById(R.id.signinHistory);
        limitTime=findViewById(R.id.limitTime);
        state=findViewById(R.id.state);
        limitTime.setOnClickListener(this);
        oneTime=findViewById(R.id.oneTime);
        oneTime.setOnClickListener(this);
        refresh=findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        endSign=findViewById(R.id.endSign);
        endSign.setOnClickListener(this);



          /*      adapter=new CommonBaseAdapter<String>(getBaseContext(),getHSVDataCreate(),R.layout.sub_comment) {
            @Override
            protected void convert(CommonViewHolder viewHolder, String comment) {
                viewHolder.setTextView(R.id.tvUsername,comment);
            }
        };*/
        //mlv.setAdapter(adapter);
       // registerForContextMenu(mlv);
        /*HttpUtils.get(APIInterface.TEC_COURSE_ID+ MyApplication.getUser().getUid()+"/"+couresId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("code")==200){
                        signId=response.getJSONObject("object").getInt("id");
                        Toast.makeText(AsignSigninActivity.this,"id success",Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(AsignSigninActivity.this,"id failed",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });*/


    }

    JsonHttpResponseHandler responseHandler=new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                if (response.getString("message").equals("201"))
                    Toast.makeText(AsignSigninActivity.this,"该班课没有学生",Toast.LENGTH_SHORT).show();
                else if (response.getString("message").equals("202"))
                    Toast.makeText(AsignSigninActivity.this,"该班课没有发起签到",Toast.LENGTH_SHORT).show();
                else if (response.getString("message").equals("查询成功！总共有2个学生的签到情况。")){

                    Gson gson=new Gson();
                    mcommomList=gson.fromJson(response.toString(),new TypeToken<CommomList<List<SignRecord>>>(){}.getType());
                    mlv.setAdapter(new CommonBaseAdapter<SignRecord>(getBaseContext(),mcommomList.getList(),R.layout.sub_comment){

                        @Override
                        protected void convert(CommonViewHolder viewHolder, SignRecord signRecord) {
                            viewHolder.setTextView(R.id.tvUsername,signRecord.getStudentName());
                            viewHolder.setTextView(R.id.state,signRecord.getHasSignIn());
                            viewHolder.getView(R.id.state).setOnClickListener(v->{
                                HttpUtils.put(APIInterface.STU_SIGN+"/"+signRecord.getSignInId()+"/"+signRecord.getStudentId(),MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        try {
                                            if (response.getInt("code")==200){
                                                Toast.makeText(AsignSigninActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                                viewHolder.setTextView(R.id.state,"已签到");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            });
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

   /* public static void openQuanXian(final Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            //--------------设置白名单
            Intent ignore = new Intent();
            ignore.setData(Uri.parse("package:"+activity.getPackageName()));
            ignore.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            activity.startActivity(ignore);
            //判断是否为android6.0系统版本，如果是，需要动态添加权限
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PERMISSION_GRANTED) {// 没有权限，申请权限。
                ActivityCompat.requestPermissions(activity, LOCATIONGPS,321);


            }
        }

    }*/

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.oneTime:
                mLocationClient.stop();
                JSONObject jsonObject = new JSONObject();
                ByteArrayEntity entity = null;
                try {
                    jsonObject.put("latitude",latitude);
                    jsonObject.put("longitude",longitude);
                    jsonObject.put("signInType",0);
                    jsonObject.put("teacherCourseId",couresId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
                    entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                HttpUtils.post(getBaseContext(),APIInterface.SIGN,MyApplication.getUser().getToken(),entity,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getInt("code")==200){
                                Toast.makeText(AsignSigninActivity.this,"发起签到成功",Toast.LENGTH_SHORT).show();
                                /*Intent it=new Intent();
                                //Bundle bundle=new Bundle();
                                //bundle.putInt("flag",1);
                                //it.putExtras(bundle);
                                it.setClass(AsignSigninActivity.this,LimitTimeSign.class);
                                startActivity(it);
                                finish();*/
                            }
                            else Toast.makeText(AsignSigninActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.limitTime:
                mLocationClient.stop();
                JSONObject jsonObject1 = new JSONObject();
                ByteArrayEntity entity1 = null;
                try {
                    jsonObject1.put("latitude",latitude);
                    jsonObject1.put("longitude",longitude);
                    jsonObject1.put("signInType",1);
                    jsonObject1.put("teacherCourseId",couresId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    entity1 = new ByteArrayEntity(jsonObject1.toString().getBytes("UTF-8"));
                    entity1.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                HttpUtils.post(getBaseContext(),APIInterface.SIGN,MyApplication.getUser().getToken(),entity1,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getInt("code")==200){
                                Toast.makeText(AsignSigninActivity.this,"发起签到成功",Toast.LENGTH_SHORT).show();
                                /*Intent it=new Intent();
                                //Bundle bundle=new Bundle();
                                //bundle.putInt("flag",1);
                                //it.putExtras(bundle);
                                it.setClass(AsignSigninActivity.this,LimitTimeSign.class);
                                startActivity(it);
                                finish();*/
                            }
                            else Toast.makeText(AsignSigninActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.refresh:
                HttpUtils.get(APIInterface.SIGN_RECORD+couresId,MyApplication.getUser().getToken(),responseHandler);
                Toast.makeText(AsignSigninActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.endSign:
                HttpUtils.put(APIInterface.SIGN_STOP+couresId,MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if(response.getInt("code")==200){
                                Toast.makeText(AsignSigninActivity.this,"签到停止成功",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.state:

                break;
        }



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
