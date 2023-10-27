package com.test.okhttptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = findViewById(R.id.send_request);
        responseText = findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.send_request){
//            sendRequstWithOkHttpClient();
            sendRequstWithOkHttpClientToServerJSONObject();
        }

    }

    /**
     * 解析serverJSONObject
     */
    private void sendRequstWithOkHttpClientToServerJSONObject() {
        new Thread(()->{
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:5005/list")
                        .build();
                Response response = client.newCall(request).execute();
                String resData = response.body().string();
                parseJSONWithJSONObject(resData);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();



    }

    private void parseJSONWithJSONObject(String resData) {
        try {
            JSONObject jsonObject = new JSONObject(resData);
            String name = jsonObject.getString("name");
            int age = jsonObject.getInt("age");
            showResponse("name:"+name+";"+"age:"+age);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRequstWithOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        new Thread(()->{
            try {
                Request request = new Request.Builder().url("https://www.baidu.com").build();
                Response execute = client.newCall(request).execute();
                assert execute.body()!=null;
                showResponse(execute.body().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();



    }

    private void showResponse(String req) {
        runOnUiThread(()->responseText.setText(req));
    }

}