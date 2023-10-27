package com.test.androidthreadtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int UPDATE_TEXT=1;

    private  TextView text;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==UPDATE_TEXT){
                //在这里进行UI操作
                text.setText("NICE To Meet you");
            }
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeText = findViewById(R.id.change_text);
        text = findViewById(R.id.text);
        changeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.change_text){
            new Thread(()->{
                Message message = new Message();
                message.what=UPDATE_TEXT;
                //将Message对象发送出去
                handler.sendMessage(message);
            }).start();
        }
    }
}