package com.test.sendbroadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private  MyBroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册按钮，触发发送标准广播
        Button sendButton = findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.test.broadcasttest.MY_BROADCAST");
                //标准广播
                //sendBroadcast(intent);
                //有序广播
                sendOrderedBroadcast(intent,null);
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.test.broadcasttest.MY_BROADCAST");
        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}