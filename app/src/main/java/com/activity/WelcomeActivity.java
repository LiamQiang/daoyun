package com.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.APIInterface;
import com.AccessToken;
import com.HttpUtils;
import com.MyApplication;
import com.bean.GitHubClient;
import com.bean.User;
import com.example.trade.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //PersistentCookieStore cookieStore=new PersistentCookieStore(getApplicationContext());
        //List<Cookie> cookies=cookieStore.getCookies();
            Handler handler=new Handler();
            handler.postDelayed(() -> {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            },1000);
            MyApplication.getUser().setFirst(false);
            MyApplication.setUser(MyApplication.getUser());

        /*for (Cookie cookie:cookies){
            System.out.println(cookie.getName()+cookie.getValue());
            if (cookie.getName().equals("uid")){
                user.setUid(Integer.parseInt(cookie.getValue()));
            }else if (cookie.getName().equals("username")){
                user.setUsername(cookie.getValue());
            }
            else if (cookie.getName().equals("tel")){
                user.setTel(cookie.getValue());
            }
        }*/

    }


}
