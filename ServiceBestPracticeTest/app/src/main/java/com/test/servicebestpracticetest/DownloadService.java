package com.test.servicebestpracticetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadService extends Service {


    private NotificationManager manager;
    private int NOTIFICATION_ID = 111;
    private int id = NOTIFICATION_ID;
    private String channel_name = "消息";
    private DownloadTask downloadTask;

    private static final String TAG="DownloadService";

    private String downloadUrl;

    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            Log.e("DownloadService","progress: "+progress);
            manager.notify(id,getNotification("下载中...",progress));
        }


        @Override
        public void onSuccess() {
            //下载成功将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            manager.notify(id,getNotification("下载成功",-1));
            Toast.makeText(DownloadService.this, "下载文件成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            stopForeground(true);
            manager.notify(id,getNotification("下载失败",-1));
            Toast.makeText(DownloadService.this, "下载文件失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            Toast.makeText(DownloadService.this, "下载文件暂停", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            stopForeground(true);
            Toast.makeText(DownloadService.this, "下载文件取消", Toast.LENGTH_SHORT).show();
        }
    };

    private DownloadBinder downloadBinder=new DownloadBinder();
    class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if(downloadTask==null){
                downloadUrl=url;
                downloadTask=new DownloadTask(listener,getApplicationContext());
                downloadTask.execute(downloadUrl);
                Log.e(TAG,"startDownload executed");
                //开启前台通知服务
                startForeground(id,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this,"Downloading...",Toast.LENGTH_SHORT).show();
            }else{
                Log.e(TAG,"startDownload not null");
            }
        }
        public void pauseDownload(){
            if(downloadTask!=null){
                downloadTask.pauseDownload();
            }
        }
        public void cancelDownload(){
            if(downloadTask!=null){
                downloadTask.cancelDownload();
            }
            if(downloadUrl!=null){
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);
                if(file.exists()){
                    file.delete();
                }
                manager.cancel(id);
                stopForeground(true);
                Toast.makeText(DownloadService.this,"取消下载",Toast.LENGTH_SHORT).show();
            }
        }
    }






    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //获取通知对象
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Notification getNotification(String title, int progress){
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        if(!notificationManagerCompat.areNotificationsEnabled()) {
            Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(title)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher);
        if(progress>=0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        if (Build.VERSION.SDK_INT > 26) {
            NotificationChannel notificationChannel = new NotificationChannel("1", "download", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId("1");
        }
        return builder.build();
    }
}