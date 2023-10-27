package com.test.notificationmanager;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotificationManager manager;
    private NotificationChannel channel;

    private NotificationCompat.Builder builder;
    private String CHANNEL_ID = "notification_channel_id";
    private int NOTIFICATION_ID = 111;
    private int id = NOTIFICATION_ID;
    private String channel_name = "消息";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //发送通知
        Button send = findViewById(R.id.send_notice);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.send_notice){
            buildNotification();
            id++;
            manager.notify(id, builder.build());
        }

    }


    /**
     * 发送普通通知消息
     */
    private void buildNotification() {
        channel_name = "普通通知消息";
        channel = null;
        manager = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //设配8.0以上设备
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //id是一个string自己随意设置，name是消息的名称，第三个参数是消息的重要程度。默认就是普通消息
            channel = new NotificationChannel(CHANNEL_ID, channel_name, NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("这是一个普通通知消息");//消息描述
            channel.setShowBadge(true);//是否在桌面显示角标
            manager.createNotificationChannel(channel);
        }
        //点击意图，就是点击了消息后需要跳转的意图
        Intent intent = new Intent(this, PropertyAnimationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        //构建通知
        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("普通通知")//标题
                .setContentText("这是一条普通通知")//内容
                .setSmallIcon(R.mipmap.ic_launcher)//小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))//大图标
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//优先级
                .setAutoCancel(true)//自动消失
                .setSound(Uri.fromFile(new File("/MIUI/.ringtone/穿越时空的思念（DiESi_Remix）(单曲版)-DiESi-14257672.mp3")))//播放音频(未生效)
                .setVibrate(new long[]{0,1000,1000,1000}) //手机震动 0手机静止时间 1000 手机震动1秒(未生效)
                .setLights(Color.GREEN,1000,1000) //手机LED灯(未生效)
                .setDefaults(NotificationCompat.DEFAULT_ALL) //默认通知效果(未生效)
                .setContentIntent(pendingIntent);//跳转配置
    }
}