package com.test.broadcasttest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private AnotherBroadcastReceiver anotherBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.test.broadcasttest.MY_BROADCAST");
        anotherBroadcastReceiver = new AnotherBroadcastReceiver();
        registerReceiver(anotherBroadcastReceiver,filter);
    }
}