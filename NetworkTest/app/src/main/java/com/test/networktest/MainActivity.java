package com.test.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
            sendRequstWithHttpURLConnction();
        }
    }

    private void sendRequstWithHttpURLConnction() {
        //开启县城来发起网络请求
        new Thread(()->{
            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url = new URL("http://www.baidu.com");
                connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                InputStream in=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(in));
                StringBuilder req = new StringBuilder();
                String line;
                while ((line=reader.readLine())!=null){
                    req.append(line);
                }
                showResponse(req.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(connection!=null){
                    connection.disconnect();
                }
            }

        }).start();

    }

    private void showResponse(String req) {
        runOnUiThread(()->responseText.setText(req));
    }
}