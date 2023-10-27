package com.test.otherseniortest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class LongRunningService extends Service {
    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(()->{

        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=60*60*1000;//1小时毫秒数
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i = new Intent(this, LongRunningService.class);
        PendingIntent service = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,service);
        return super.onStartCommand(intent,flags,startId);
    }
}