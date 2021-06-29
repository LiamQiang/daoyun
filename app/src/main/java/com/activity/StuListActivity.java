package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.CommomList;
import com.CommonBaseAdapter;
import com.CommonViewHolder;
import com.HttpUtils;
import com.MyApplication;
import com.bean.studen;
import com.example.trade.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StuListActivity extends Activity {

    ListView listView;
    CommomList<List<studen>> mcommomList;
    int couresId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favor);
        listView = findViewById(R.id.ListView);
        couresId=getIntent().getExtras().getInt("courseID");
        Toast.makeText(StuListActivity.this, String.valueOf(couresId), Toast.LENGTH_SHORT).show();
        HttpUtils.get(APIInterface.STU_LIST+couresId,MyApplication.getUser().getToken(),responseHandlerstu);
    }

    JsonHttpResponseHandler responseHandlerstu = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            //利用gson把json转成bean
            try {
                if (response.getInt("code") == 200) {
                    Gson gson = new Gson();
                    mcommomList = gson.fromJson(response.toString(), new TypeToken<CommomList<List<studen>>>() {
                    }.getType());
                    listView.setAdapter(new CommonBaseAdapter<studen>(StuListActivity.this, mcommomList.getList(), R.layout.sub_message) {
                        @Override
                        protected void convert(CommonViewHolder viewHolder, studen item) {
                            viewHolder.setTextView(R.id.tvDepartment, item.getDepartment());
                            //viewHolder.setTextView(R.id.className,item.getSchoolName());
                            viewHolder.setTextView(R.id.tvStudentName, item.getStudentName());
                            viewHolder.setTextView(R.id.tvExp, String.valueOf(item.getCourseExp()));

                        }
                    });
                } else
                    Toast.makeText(StuListActivity.this, "code 500 error", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}