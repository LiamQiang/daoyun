package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.trade.R;

public class RegVerifyActivity  extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_verify);
        initUI();
    }

    private void initUI(){
        Button regbutton=findViewById(R.id.regbutton);
        regbutton.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        intent.setClass(this,RegActivity.class);
        startActivity(intent);
    }
}
