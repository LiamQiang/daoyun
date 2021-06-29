package com;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.entity.ByteArrayEntity;


public class HttpUtils {

    static AsyncHttpClient client=new AsyncHttpClient();

    public static void setCookieStore(CookieStore cookieStore){
        client.setCookieStore(cookieStore);
    }

    public static void get(String url, JsonHttpResponseHandler responseHandler){
        client.get(getUrl(url),responseHandler);
    }

    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler){
        client.get(getUrl(url),params,responseHandler);
    }

    public static void post(String url, JsonHttpResponseHandler responseHandler){
        client.post(getUrl(url),responseHandler);
    }

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler){
        client.post(getUrl(url),params,responseHandler);
    }
    public static void post(Context context, String url, ByteArrayEntity entity , JsonHttpResponseHandler responseHandler){
        client.post(context,getUrl(url),entity,"application/json",responseHandler);
    }
    public static void get(String url ,String token,JsonHttpResponseHandler responseHandler){
        client.addHeader("Authorization", "Bearer " +token);
        client.get(getUrl(url),responseHandler);
    }

    public static void post(Context context,String url ,String token,ByteArrayEntity entity,JsonHttpResponseHandler responseHandler){
        client.addHeader("Authorization", "Bearer " +token);
        client.post(context,getUrl(url),entity,"application/json",responseHandler);
    }
    public static void post(String url ,String token,RequestParams params,JsonHttpResponseHandler responseHandler){
        client.addHeader("Authorization", "Bearer " +token);
        client.post(getUrl(url),params,responseHandler);

    }

    public static void delete(String url ,String token,JsonHttpResponseHandler responseHandler){
        client.addHeader("Authorization", "Bearer " +token);
        client.delete(getUrl(url),responseHandler);
    }

    public static void put(String url ,String token,JsonHttpResponseHandler responseHandler){
        client.addHeader("Authorization", "Bearer " +token);
        client.put(getUrl(url),responseHandler);
    }
    public static void put(Context context,String url ,String token,ByteArrayEntity entity,JsonHttpResponseHandler responseHandler){
        client.addHeader("Authorization", "Bearer " +token);
        client.put(context,getUrl(url),entity,"application/json",responseHandler);
    }





    public static String getUrl(String url){
        return APIInterface.API_HOST+url;
    }
}
