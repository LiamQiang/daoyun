package com.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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


import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements View.OnClickListener {

    String clientId     = "a584e05fe43ff00bc912";
    String clientSecret = "7a9a596b03c1a131cc2f09c3afd1e2bb1eeeb2be";
    String redirectUri  = "huyaoyu://callback";
    TextView mTvReg,mVerifyLogin,forgetpw;
    ImageView mGithubLogin;
    EditText inputaccount,inputpw;
    Button Login;
    View back;
    ProgressDialog dialog;
    public static String TAG="LoginActivity:";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (MyApplication.getUser().isFirst()){
            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            finish();
        }
        if (MyApplication.getUser().isLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        initUI();
    }
    private void initUI(){
        back=findViewById(R.id.LoginBack);
        forgetpw=findViewById(R.id.forgetpassword);
        forgetpw.setOnClickListener(this);
        back.setOnClickListener(this);
        mTvReg=findViewById(R.id.tvReg);
        mTvReg.setOnClickListener(this);
        inputaccount=findViewById(R.id.inputaccount);
        inputpw=findViewById(R.id.inputpassword);
        Login=findViewById(R.id.loginbutton);
        Login.setOnClickListener(this);
        mVerifyLogin=findViewById(R.id.verifycodeLogin);
        mVerifyLogin.setOnClickListener(this);
        mGithubLogin=findViewById(R.id.tvGithub);
        mGithubLogin.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();

        if ( uri != null && uri.toString().startsWith(redirectUri)) {
            String code = uri.getQueryParameter("code");

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://github.com/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            GitHubClient client = retrofit.create(GitHubClient.class);

            Call<AccessToken> accessTokenCall =
                    client.getAccessToken(clientId, clientSecret, code);

            accessTokenCall.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    ProgressDialog dialog1;
                    String accessToken = response.body().getAccessToken();
                   /* Toast.makeText(LoginActivity.this,
                            "access_token: " + accessToken,
                            Toast.LENGTH_SHORT
                    ).show();*/
                    RequestParams params = new RequestParams();
                    params.put("accessToken", accessToken);
                    dialog1=ProgressDialog.show(LoginActivity.this,"","登陆中");
                    dialog1.show();

                    HttpUtils.post(APIInterface.GITHUB,params,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if(response.getJSONObject("object").getInt("loginMode")==1){
                                    String thirdPartyId= response.getJSONObject("object").getString("thirdPartyId");
                                    MyApplication.getUser().setLogin(true);
                                    Intent intent=new Intent();
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("id",3);
                                    bundle.putString("thirdPartyId",thirdPartyId);
                                    intent.putExtras(bundle);
                                    intent.setClass(LoginActivity.this,RegPhoneActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,
                                            response.getString("message") ,
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    MyApplication.getUser().setToken(response.getJSONObject("object").getString("token"));
                                    MyApplication.getUser().setLogin(true);
                                    Intent intent=new Intent();
                                    intent.setClass(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    dialog1.dismiss();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            dialog1.dismiss();
                        }
                    });
                    dialog1.dismiss();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "No!", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(LoginActivity.this, "Null!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvReg:
                Intent intent=new Intent();
                Bundle extras=new Bundle();
                extras.putInt("id",1);
                intent.putExtras(extras);
                intent.setClass(this,RegPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.LoginBack:
                finish();
                break;
            /*case R.id.forgetpassword:
                Intent forget=new Intent();
                Bundle extras2=new Bundle();
                extras2.putInt("id",2);
                forget.setClass(this,RegPhoneActivity.class);
                startActivity(forget);
                finish();
                break;*/
            case R.id.loginbutton:
                dialog=ProgressDialog.show(this,"","登陆中");
                dialog.show();
                validate();
                break;
            case R.id.tvGithub:
                Intent intentgit = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "https://github.com/login/oauth/authorize" + "?client_id=" + clientId
                                + "&scope=repo" + "&redirect_uri=" + redirectUri
                ));

                startActivity(intentgit);
                break;
            case R.id.verifycodeLogin:
                Intent intentlogin=new Intent();
                Bundle extra=new Bundle();
                extra.putInt("id",0);
                intentlogin.putExtras(extra);
                intentlogin.setClass(this,RegPhoneActivity.class);
                startActivity(intentlogin);
                break;
          default:
              break;
        }
    }
    /*校验登录信息*/
    private void  validate(){
        String account,pw;
        account=inputaccount.getText().toString();
        pw=inputpw.getText().toString();
        if (account.equals("")) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        if (pw.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        RequestParams params=new RequestParams();
        params.add("account",account);
        params.add("password",pw);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",account);
            jsonObject.put("password",pw);

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
        //持久化保存cookie到客户端
        HttpUtils.setCookieStore(new PersistentCookieStore(getApplicationContext()));
        HttpUtils.post(getBaseContext(),APIInterface.LOGIN,entity,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("code")==200){
                        PersistentCookieStore cookieStore=new PersistentCookieStore(getApplicationContext());
                        List<Cookie> cookies=cookieStore.getCookies();
                        MyApplication.getUser().setToken(response.getJSONObject("object").getString("token"));
                        HttpUtils.get(APIInterface.USER_INFO,MyApplication.getUser().getToken(),new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response1) {
                                super.onSuccess(statusCode, headers, response1);
                                try {
                                    MyApplication.getUser().setUid(response1.getJSONObject("object").getInt("id"));
                                    MyApplication.getUser().setRole(response1.getJSONObject("object").getInt("role"));
                                    MyApplication.getUser().setNickname(response1.getJSONObject("object").getString("nickname"));
                                    MyApplication.getUser().setLogin(true);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                            }
                        });
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
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), response.getJSONObject("object").getString("tokenHead"), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent it=new Intent();
                        it.setClass(LoginActivity.this,MainActivity.class);
                        startActivity(it);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    Log.e(TAG,"异常");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("fail"+throwable.getMessage());
            }
        });

        /*if (account.equals("123") && pw.equals("123")) {
            SharedPreferences sp=getSharedPreferences(MyApplication.SP_USER,0);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("account",account);
            editor.putInt("isOnLine",1);
            editor.commit();
            dialog.dismiss();
            finish();
        }else {
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }*/

    }
}
