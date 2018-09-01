package com.seven.health.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/8/14.
 */

public class OkhttpUtil {

    private static final String urlHeader = "http://192.168.43.78:8080/HttpServer";

    public static void okGet(final String url, Handler handler) {
        http("GET", urlHeader+url, null, handler);
    }

    public static void okPost(final String url, final Object obj, Handler handler) {
        http("POST", urlHeader+url, obj, handler);
    }

    private static void http(final String method, final String url, final Object obj, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request.Builder requestBuilder = new Request.Builder().url(url);  //请求的url
                    
                    //如果是post请求，需要构造请求体
                    if("POST".equals(method)){
                       // FormBody.Builder requestBodyBuilder = new FormBody.Builder(Charset.defaultCharset());
                        
                        //将类对象转换成json对象发送
                        if(obj != null){
                            String params = new Gson().toJson(obj);
                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            RequestBody requestBody = RequestBody.create(JSON, params);
                            //post请求体
                            requestBuilder.post(requestBody);
                        }
                    }

                    Request request = requestBuilder.build();
                    //获得返回的响应数据
                    Response response = client.newCall(request).execute();

                    if(response.code() == 200){
                        Message message = Message.obtain();
                        if("POST".equals(method)){
                            message.what = 202;
                        }else {
                            message.what = 200;
                        }
                        message.obj = response.body().string();
                        Log.e("Okhttp","Success Code="+response.code()+",res="+message.obj);
                        handler.sendMessage(message);

                    }else{
                        Message message = Message.obtain();
                        message.what = response.code();
                        handler.sendMessage(message);
                      //  handler.sendEmptyMessage(-1);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

