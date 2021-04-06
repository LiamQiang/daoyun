package com.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.APIInterface;
import com.CommomList;
import com.CommonBaseAdapter;
import com.CommonViewHolder;
import com.DateUtils;
import com.HttpUtils;
import com.MyApplication;
import com.activity.ItemDetailActivity;
import com.activity.LoginActivity;
import com.activity.SearchActivity;
import com.bean.Item;
import com.example.trade.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ListView listView;
    View mivSearch;
    ImageView ivJoinClass;
    CommomList<List<Item>> mcommomList;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    TextView create,join;
    PopupWindow mpopupWindow,QRpopup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_home,container,false);
        initUI(rootview);
        listView.setAdapter(new CommonBaseAdapter<String>(getActivity(), getHSVData(), R.layout.sub_home_list) {
                            @Override
                            protected void convert(CommonViewHolder viewHolder, String s) {
                                viewHolder.setTextView(R.id.stuNmae,s.toString());
                            }
                        });
        //sendRequest();
        return rootview;
    }

    private void sendRequest(){
        //HttpUtils.get(APIInterface.HOME+"?uid="+MyApplication.getUser().getUid(),responseHandler);
    }

    @Override
    public void onResume() {
        super.onResume();
        //sendRequest();
    }

    //获取商品数据Handeler
    JsonHttpResponseHandler responseHandler= new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            //利用gson把json转成bean
            Gson gson=new Gson();
            mcommomList=gson.fromJson(response.toString(),new TypeToken<CommomList<List<Item>>>(){}.getType());

            Date nowDate=new Date();

            listView.setAdapter(new CommonBaseAdapter<Item>(getActivity(),mcommomList.getList(),R.layout.sub_home_list) {
                @Override
                protected void convert(CommonViewHolder viewHolder, Item item) {
                    if (item.getCreated()!=null&&!item.getCreated().equals("")){
                        Date myDate= DateUtils.toDate(item.getCreated());
                        String created= DateUtils.subTime(nowDate,myDate);
                         viewHolder.setTextView(R.id.tvCreated,created);
                    }
                    viewHolder.setTextView(R.id.tvPrice,"￥"+item.getPrice())
                            .setTextView(R.id.tvContent,item.getContent())
                            .setTextView(R.id.tvCity,item.getCity())
                            .setTextView(R.id.tvComments, String.valueOf(item.getComments()))
                            .setTextView(R.id.tvCollections, String.valueOf(item.getCollections()))
                            .setTextView(R.id.tvTitle,item.getTitle())
                            .setTextView(R.id.tvUserName, item.getUsername());
                    if (item.getFlag()!=null&&item.getFlag().equals("1")){
                        ((TextView)viewHolder.getView(R.id.tvCollections)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.love_red,0,0,0);
                    }else ((TextView)viewHolder.getView(R.id.tvCollections)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.love_gray,0,0,0);
                    
                    if (item.getPicList()!=null&&item.getPicList().size()>0){
                        //viewHolder.setHSVGallery(R.id.hsv,item.getPicList(),item.getPicList());
                    }
                    //位列表项设置监听
                    viewHolder.getView(R.id.tvTest).setOnClickListener(v -> {
                        Intent intent=new Intent(getActivity(), ItemDetailActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    });
                    //收藏监听器
                    viewHolder.getView(R.id.tvCollections).setOnClickListener(v -> {
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
                    });
                }
            });

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    private void initUI(View rootview){
        mivSearch=rootview.findViewById(R.id.ivSearch);
        mivSearch.setOnClickListener(this);
        create=rootview.findViewById(R.id.create);
        join=rootview.findViewById(R.id.join);
        create.setOnClickListener(this);
        join.setOnClickListener(this);
        listView=rootview.findViewById(R.id.ListView);
        listView.setSelector(R.drawable.bg_mysell);
        ivJoinClass=rootview.findViewById(R.id.ivJoinClass);
        ivJoinClass.setOnClickListener(this);


        /*rootview.findViewById(R.id.tvRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });*/
    }


    private List<String> getHSVData(){
        List<String> data=new ArrayList<String>();
        data.add("赵坤");
        data.add("赵2坤");
        data.add("赵3坤");

        return  data;
    }

    private List<String> getHSVDataCreate(){
        List<String> data=new ArrayList<String>();
        data.add("林炜智");
        data.add("林炜智1");
        return  data;
    }

    private ArrayList<Integer> getHSVDataBig(){
        ArrayList<Integer> data=new ArrayList<Integer>();
        data.add(R.mipmap.pic1);
        data.add(R.mipmap.pic2);
        data.add(R.mipmap.pic3);
        data.add(R.mipmap.pic4);
        return  data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivSearch:
                Intent it=new Intent();
                it.setClass(getActivity(), SearchActivity.class);
                startActivity(it);
                break;
            case R.id.create:
                create.setTextColor(this.getResources().getColor(R.color.base_color));
                join.setTextColor(this.getResources().getColor(R.color.black));
                initWindow(getHSVDataCreate());
                break;
            case R.id.join:
                create.setTextColor(this.getResources().getColor(R.color.black));
                join.setTextColor(this.getResources().getColor(R.color.base_color));
                initWindow(getHSVData());
                break;
            case R.id.ivJoinClass:
                initPopup(v);
                break;

        }
    }
    public void  initWindow(List<String> list){
        listView.setAdapter(new CommonBaseAdapter<String>(getActivity(), list, R.layout.sub_home_list) {
            @Override
            protected void convert(CommonViewHolder viewHolder, String s) {
                viewHolder.setTextView(R.id.stuNmae,s.toString());
            }
        });
    }
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
            Toast.makeText(getActivity(),String.valueOf(data.get(position).get("id")),Toast.LENGTH_SHORT).show();
            if (data.get(position).get("id").equals("1")){

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
        mpopupWindow.showAsDropDown(ivJoinClass);
    }
}
