package com.test.otherseniortest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * Peron 序列化进行传输（Serializable）
 * 自定义日志工具LogUtil
 * 创建定时任务Time和Alarm(推荐，可以在系统中长时间运行)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //使用Intent传递对象
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Peron peron = new Peron();
        peron.setName("xiaoming");
        peron.setAge(16);
        intent.putExtra("person_data",peron);
        startActivity(intent);

    }
}