package com.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.APIInterface;
import com.CommomList;
import com.CommonBaseAdapter;
import com.CommonViewHolder;
import com.HttpUtils;
import com.MyApplication;
import com.activity.ClassInfoActivity;
import com.activity.CreateClassActivity;
import com.activity.GenerateQRcodeActivity;
import com.activity.JoinClass;
import com.activity.PostActivity;
import com.activity.SearchActivity;
import com.activity.AsignSigninActivity;
import com.activity.StuSign;
import com.bean.Constant;
import com.bean.object;
import com.example.trade.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.activity.CaptureActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ListView listView;
    View mivSearch,mheght;
    ImageView ivJoinClass;
    CommomList<List<object>> mcommomList;
    List<object> course;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    TextView create,join;
    PopupWindow mpopupWindow,QRpopup;
    boolean isLogin=false;
    String tecName;
    int scanId = 0,tecCourseid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_home,container,false);
        initUI(rootview);
        //sendRequest();
        return rootview;
    }

    private void sendRequest(){
        HttpUtils.get(APIInterface.USER_INFO,MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response1) {
                super.onSuccess(statusCode, headers, response1);
                if (MyApplication.getUser().getRole()==0)
                    HttpUtils.get(APIInterface.STU_COURSE, MyApplication.getUser().getToken(),responseHandlerstu);
                else {
                    HttpUtils.get(APIInterface.TEC_COURSE, MyApplication.getUser().getToken(),responseHandler);

                   /* HttpUtils.get(APIInterface.USER_INFO, MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.getInt("code")==200){
                                    tecName=response.getJSONObject("object").getString("nickname");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });*/
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
            sendRequest();

    }


    //教师班课Handeler
    JsonHttpResponseHandler responseHandler= new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            //利用gson把json转成bean
            try {

                if (response.getInt("code")==200){
                    Gson gson=new Gson();
                    mcommomList=gson.fromJson(response.toString(),new TypeToken<CommomList<List<object>>>(){}.getType());
                    for (int i=0;i<mcommomList.getList().size();i++){
                        for (int j=0;j<mcommomList.getList().size();j++)
                        if (mcommomList.getList().get(j).getActivity()==0)
                            mcommomList.getList().remove(j);
                    }
                    listView.setAdapter(new CommonBaseAdapter<object>(getActivity(),mcommomList.getList(),R.layout.sub_home_list) {
                        @Override
                        protected void convert(CommonViewHolder viewHolder, object item) {
                            if(item.getActivity()==1){
                                viewHolder.setTextView(R.id.className,item.getCourse_name());
                                //viewHolder.setTextView(R.id.className,item.getSchoolName());
                                viewHolder.setTextView(R.id.classTime,item.getSemester());
                                viewHolder.setTextView(R.id.stuNmae,MyApplication.getUser().getNickname());
                                    viewHolder.setTextView(R.id.classSignin, "发起签到");
                                    viewHolder.getView(R.id.classSignin).setOnClickListener(v -> {
                                        Bundle bundle=new Bundle();
                                        bundle.putInt("id",item.getTeacher_course_id());
                                        Intent it =new Intent();
                                        it.putExtras(bundle);
                                        it.setClass(getActivity(), AsignSigninActivity.class);
                                        startActivity(it);
                    /*Intent intent=new Intent();
                    intent.setClass(getActivity(), SigninActivity.class);
                    startActivity(intent);*/
                                    });
                                viewHolder.setTextView(R.id.classTime,item.getSemester());

                                viewHolder.getView(R.id.tvCreated).setOnClickListener(v -> {

                                    Bundle bundle=new Bundle();
                                    bundle.putString("name",MyApplication.getUser().getNickname());
                                    bundle.putString("schoolName",item.getSchool_name());
                                    bundle.putString("courseName",item.getCourse_name());
                                    bundle.putString("semester",item.getSemester());
                                    bundle.putInt("classId",item.getCourse_id());
                                    bundle.putInt("allow_join",item.getAllow_join());
                                    bundle.putInt("flag",MyApplication.getUser().getRole());
                                    Intent it1 =new Intent();
                                    it1.putExtras(bundle);
                                    it1.setClass(getActivity(), ClassInfoActivity.class);
                                    startActivity(it1);
                                });
                            }

                            //位列表项设置监听
                   /* viewHolder.getView(R.id.tvTest).setOnClickListener(v -> {
                        Intent intent=new Intent(getActivity(), ItemDetailActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    });*/
                            //收藏监听器
                    /*viewHolder.getView(R.id.tvCollections).setOnClickListener(v -> {
                        if (MyApplication.getUser().getUid()!=0){//判断是否登录
                            HttpUtils.get(APIInterface.FAVOUR_ITEM+"?uid="+MyApplication.getUser().getUid()+"&itemId="+item.getId(),new JsonHttpResponseHandler(){
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    TextView tv=(TextView)v;
                                    try {
                                        if (response.getInt("code")==200){
                                            if (response.getInt("data")==1){
                                                Toast.makeText(getActivity(),"收藏成功", Toast.LENGTH_SHORT).show();
                                                tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.love_red,0,0,0);
                                                tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())+1));
                                            }else {
                                                Toast.makeText(getActivity(),"取消收藏成功", Toast.LENGTH_SHORT).show();
                                                tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.love_gray,0,0,0);
                                                tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())-1));
                                            }
                                            super.onSuccess(statusCode, headers, response);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    });*/
                        }
                    });
                }
                else Toast.makeText(getActivity(),"code 500 error",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };
    //学生班课Handeler
    JsonHttpResponseHandler responseHandlerstu= new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            //利用gson把json转成bean
            try {

                if (response.getInt("code")==200){
                    Gson gson=new Gson();
                    mcommomList=gson.fromJson(response.toString(),new TypeToken<CommomList<List<object>>>(){}.getType());
                    listView.setAdapter(new CommonBaseAdapter<object>(getActivity(),mcommomList.getList(),R.layout.sub_home_list) {
                        @Override
                        protected void convert(CommonViewHolder viewHolder, object item) {
                            viewHolder.setTextView(R.id.className,item.getCourse_name());
                            //viewHolder.setTextView(R.id.className,item.getSchoolName());
                            viewHolder.setTextView(R.id.classTime,item.getSemester());
                            viewHolder.setTextView(R.id.stuNmae,item.getTeacher_name());
                                viewHolder.setTextView(R.id.classSignin, "签到");
                                viewHolder.getView(R.id.classSignin).setOnClickListener(v -> {
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("teacherCourseId",item.getTeacher_course_id());
                                    Intent it =new Intent();
                                    it.putExtras(bundle);
                                    it.setClass(getActivity(), StuSign.class);
                                    startActivity(it);
                    /*Intent intent=new Intent();
                    intent.setClass(getActivity(), SigninActivity.class);
                    startActivity(intent);*/
                                });
                            viewHolder.setTextView(R.id.classTime,item.getSemester());
                            viewHolder.getView(R.id.tvCreated).setOnClickListener(v -> {
                                Bundle bundle=new Bundle();
                                bundle.putString("courseName",item.getCourse_name());
                                bundle.putString("tecName",item.getTeacher_name());
                                bundle.putString("name",item.getCourse_name());
                                bundle.putString("schoolName",item.getSchool_name());
                                bundle.putString("semester",item.getSemester());
                                bundle.putInt("classId",item.getTeacher_course_id());
                                bundle.putInt("flag",MyApplication.getUser().getRole());
                                Intent it1 =new Intent();
                                it1.putExtras(bundle);
                                it1.setClass(getActivity(), ClassInfoActivity.class);
                                startActivity(it1);
                            });

                            //位列表项设置监听
                   /* viewHolder.getView(R.id.tvTest).setOnClickListener(v -> {
                        Intent intent=new Intent(getActivity(), ItemDetailActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    });*/
                            //收藏监听器
                    /*viewHolder.getView(R.id.tvCollections).setOnClickListener(v -> {
                        if (MyApplication.getUser().getUid()!=0){//判断是否登录
                            HttpUtils.get(APIInterface.FAVOUR_ITEM+"?uid="+MyApplication.getUser().getUid()+"&itemId="+item.getId(),new JsonHttpResponseHandler(){
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    TextView tv=(TextView)v;
                                    try {
                                        if (response.getInt("code")==200){
                                            if (response.getInt("data")==1){
                                                Toast.makeText(getActivity(),"收藏成功", Toast.LENGTH_SHORT).show();
                                                tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.love_red,0,0,0);
                                                tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())+1));
                                            }else {
                                                Toast.makeText(getActivity(),"取消收藏成功", Toast.LENGTH_SHORT).show();
                                                tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.love_gray,0,0,0);
                                                tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())-1));
                                            }
                                            super.onSuccess(statusCode, headers, response);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    });*/
                        }
                    });
                }
                else Toast.makeText(getActivity(),"code 500 error",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    private void initUI(View rootview){
        mivSearch=rootview.findViewById(R.id.ivSearch);
        mheght=rootview.findViewById(R.id.popupHeigth);
        mivSearch.setOnClickListener(this);
        create=rootview.findViewById(R.id.create);
        join=rootview.findViewById(R.id.join);
        create.setOnClickListener(this);
        join.setOnClickListener(this);
        listView=rootview.findViewById(R.id.ListView);
        listView.setSelector(R.drawable.bg_mysell);
        ivJoinClass=rootview.findViewById(R.id.ivJoinClass);
        ivJoinClass.setOnClickListener(this);
        mpopupWindow=new PopupWindow();
        mivSearch.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (mpopupWindow.isShowing()) {
                    //设置高度并生效
                    mpopupWindow.update(ViewGroup.LayoutParams.MATCH_PARENT, mivSearch.getHeight());
                }
            }
        });


        /*rootview.findViewById(R.id.tvRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });*/
    }


   /* private List<String> getHSVData(){
        List<String> data=new ArrayList<String>();
        data.add("赵坤");
        data.add("赵2坤");
        data.add("赵3坤");
        data.add("赵3坤");
        data.add("赵3坤");
        data.add("赵3坤");
        data.add("赵3坤");
        data.add("赵3坤");
        data.add("赵3坤");
        data.add("赵3坤");data.add("赵3坤");
        data.add("赵3坤");data.add("赵3坤");data.add("赵3坤");data.add("赵3坤");


        return  data;
    }*/

    /*private List<String> getHSVDataCreate(){
        List<String> data=new ArrayList<String>();

        data.add("林炜智");
        data.add("林炜智1");
        return  data;
    }*/



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivSearch:
                Intent it=new Intent();
                it.setClass(getActivity(), SearchActivity.class);
                startActivity(it);
                break;
            case R.id.create:
                if (MyApplication.getUser().getRole()==0)
                    Toast.makeText(getActivity(),"没有权限创建班课",Toast.LENGTH_SHORT).show();
                else{
                    create.setTextColor(this.getResources().getColor(R.color.base_color));
                    join.setTextColor(this.getResources().getColor(R.color.black));
                    HttpUtils.get(APIInterface.TEC_COURSE, MyApplication.getUser().getToken(),responseHandler);
                }
                break;
            case R.id.join:
                create.setTextColor(this.getResources().getColor(R.color.black));
                join.setTextColor(this.getResources().getColor(R.color.base_color));
                if (MyApplication.getUser().getRole()==1)
                    HttpUtils.get(APIInterface.TEC_COURSE, MyApplication.getUser().getToken(),responseHandler);
                else
                    HttpUtils.get(APIInterface.STU_COURSE, MyApplication.getUser().getToken(),responseHandlerstu);
                break;
            case R.id.ivJoinClass:
                initPopup(v);
                break;

        }
    }
    /*public void  initWindow(List<String> list){
        listView.setAdapter(new CommonBaseAdapter<String>(getActivity(), list, R.layout.sub_home_list) {
            @Override
            protected void convert(CommonViewHolder viewHolder, String s) {
                viewHolder.setTextView(R.id.stuNmae,s.toString());
                viewHolder.setTextView(R.id.classTime,s.toString());
                viewHolder.getView(R.id.classSignin).setOnClickListener(v -> {
                    HttpUtils.get(APIInterface.USER_INFO, MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                Toast.makeText(getActivity(),response.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                    *//*Intent intent=new Intent();
                    intent.setClass(getActivity(), SigninActivity.class);
                    startActivity(intent);*//*
                });
            }
        });
    }*/
    public void initPopup(View view){
        ArrayList<HashMap<String,String>> data= new ArrayList<>();
        ArrayList<HashMap<String,String>> QRcode= new ArrayList<>();
        HashMap<String, String> map0=new HashMap<>();
        HashMap<String, String> map1=new HashMap<>();
        HashMap<String, String> map2=new HashMap<>();

        map0.put("name","创建班课");
        map0.put("id","0");
        map1.put("name","二维码加入班课");
        map1.put("id","1");
        map2.put("name","班课号加入班课");
        map2.put("id","2");
        data.add(map0);
        data.add(map1);
        data.add(map2);

        HashMap<String, String> code0=new HashMap<>();
        HashMap<String, String> code1=new HashMap<>();
        HashMap<String, String> code2=new HashMap<>();

        code0.put("name","创建班课");
        code0.put("id","00");
        code1.put("name","二维码加入班课");
        code1.put("id","01");
        code2.put("name","班课号加入班课");
        code2.put("id","02");
        QRcode.add(code0);
        QRcode.add(code1);
        QRcode.add(code2);

        View contentView=LayoutInflater.from(getActivity()).inflate(R.layout.popup_listview,null);
        contentView.setOnClickListener(v -> {
            if (mpopupWindow.isShowing())
                mpopupWindow.dismiss();
        });
        ListView listView=contentView.findViewById(R.id.ListView);

        //点击弹出窗
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            if (data.get(position).get("id").equals("0")){
                Intent intent=new Intent();
                intent.setClass(getActivity(), CreateClassActivity.class);
                startActivity(intent);
                mpopupWindow.dismiss();
            }
            if (data.get(position).get("id").equals("1")){

                startQrCode();
                mpopupWindow.dismiss();
                /*Intent intent=new Intent();
                intent.setClass(getActivity(), CaptureActivity.class);
                startActivity(intent);*/
            }
            if (data.get(position).get("id").equals("2")){
                Intent intent=new Intent();
                intent.setClass(getActivity(), JoinClass.class);
                startActivity(intent);
                mpopupWindow.dismiss();
            }
            else if (mpopupWindow.isShowing()) {
                mpopupWindow.dismiss();
            }
        });

        listView.setAdapter(new SimpleAdapter(getActivity(),data,R.layout.sub_tab_sort,new String[]{"name"},new int[]{R.id.name}));
        mpopupWindow =new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mpopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mpopupWindow.setOutsideTouchable(true);
        mpopupWindow.setAnimationStyle(0);
        mpopupWindow.setFocusable(true);
        mpopupWindow.setHeight(mheght.getHeight());
        mpopupWindow.showAsDropDown(ivJoinClass);
    }


    // 开始扫码
    public void startQrCode() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                Toast.makeText(getActivity(), "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
            }
            // 申请权限
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        int code=resultCode;
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            try{
                scanId=Integer.parseInt(scanResult);
            }catch (Exception e){
                Toast.makeText(getActivity(), "班课号不正确", Toast.LENGTH_SHORT).show();
            }
            //
            /*JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("courseId",scanId);

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
            HttpUtils.post(getContext(),APIInterface.JOIN_COURSE,entity,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if (response.getInt("code")==200){
                            Toast.makeText(getActivity(), "加入成功", Toast.LENGTH_LONG).show();
                        }
                        else if (response.getInt("code")==500)
                            Toast.makeText(getActivity(), "只有学生可以加入班课", Toast.LENGTH_LONG).show();
                        else Toast.makeText(getActivity(), "加入失败", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(getActivity(), "加入失败", Toast.LENGTH_LONG).show();
                }
            });*/
            Bundle bundle1=new Bundle();
            Intent it=new Intent();
            HttpUtils.get(APIInterface.COURSE_INFO+scanResult,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if (response.getInt("code")==200){
                            bundle1.putString("name",response.getJSONObject("object").getString("className"));
                            bundle1.putString("schoolName",response.getJSONObject("object").getString("schoolName"));
                            bundle1.putString("semester",response.getJSONObject("object").getString("semester"));
                            bundle1.putInt("classId", scanId);
                            bundle1.putInt("allow_join", response.getJSONObject("object").getInt("allowJoin"));
                            //bundle1.putInt("classId", Integer.parseInt(scanResult));
                            bundle1.putInt("flag",2);
                            // bundle.putInt("tag",response.getJSONObject("object").getInt("tag"));
                            // bundle.putInt("end",response.getJSONObject("object").getInt("end"));
                            it.putExtras(bundle1);
                            it.setClass(getActivity(),ClassInfoActivity.class);
                            startActivity(it);
                        }
                        else Toast.makeText(getActivity(), "班课号不存在", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    try {
                        Log.v("1",errorResponse.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
           // Toast.makeText(getActivity(), scanResult, Toast.LENGTH_LONG).show();
            //将扫描出的信息显示出来

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(getActivity(), "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
