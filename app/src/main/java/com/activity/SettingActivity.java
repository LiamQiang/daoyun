package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.MyApplication;
import com.example.trade.R;
import com.loopj.android.http.PersistentCookieStore;

public class SettingActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.resignbutton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resignbutton:
                /*SharedPreferences sp=getSharedPreferences(MyApplication.SP_USER,0);
                SharedPreferences.Editor editor=sp.edit();
                editor.putInt("isOnLine",0);
                editor.commit();*/
                MyApplication.getUser().setToken("");
                MyApplication.getUser().setRole(2);
                MyApplication.getUser().setNickname("");
                MyApplication.getUser().setUid(0);
                MyApplication.getUser().setLogin(false);
                Intent it=new Intent();
                it.setClass(SettingActivity.this,LoginActivity.class);
                Toast.makeText(getBaseContext(),"注销成功",Toast.LENGTH_SHORT).show();
                startActivity(it);
                finish();
                break;
        }
    }
}
