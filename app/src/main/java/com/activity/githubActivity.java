package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.APIInterface;
import com.AccessToken;
import com.HttpUtils;
import com.bean.GitHubClient;
import com.example.trade.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class githubActivity extends Activity {

    private ListView listView;

    private String clientId     = "ba9bf9792ff5403656ac";
    private String clientSecret = "b9c8c3bab399449fcf850344ad37d00bded84612";
    private String redirectUri  = "http://localhost:8081/oauth/redirect&state=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.github);
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
                    String accessToken = response.body().getAccessToken();

                    /*TextView textView = findViewById(R.id.textView);
                    textView.setText("Access token: " + accessToken);*/
                    RequestParams params = new RequestParams();
                    params.put("accessToken", accessToken);
                    HttpUtils.get(APIInterface.GITHUB,params,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                Toast.makeText(githubActivity.this,
                                        response.getString("message") ,
                                        Toast.LENGTH_SHORT
                                ).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });

                    Toast.makeText(githubActivity.this,
                            "access_token: " + accessToken,
                            Toast.LENGTH_SHORT
                    ).show();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(githubActivity.this, "No!", Toast.LENGTH_SHORT).show();
                }
            });

            Toast.makeText(githubActivity.this, "Yeah!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(githubActivity.this, "Null!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickGetAccessToken(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://github.com/login/oauth/authorize" + "?client_id=" + clientId
                        + "&scope=repo" + "&redirect_uri=" + redirectUri
        ));

        startActivity(intent);
    }
}
