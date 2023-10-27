package com.test.servicebestpracticetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("MainActivity","onServiceConnected executed");
            downloadBinder=(DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startDownload = findViewById(R.id.start_download);
        Button pauseDownload = findViewById(R.id.pause_download);
        Button cancelDownload = findViewById(R.id.cancel_download);
        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);
        Intent intent = new Intent(this, DownloadService.class);
        //TODO Android13新增
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动服务
        startService(intent);
        //绑定服务
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>1&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"获取权限失败",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.start_download){
            if(downloadBinder!=null){
                Log.e("MainActivity","onCreate success");
                String Url = "http://139.155.0.212:5244/d/home/alistalist_file/z3.mp3\n";
                downloadBinder.startDownload(Url);
            }else{
                Log.e("MainActivity","onCreate fail");
            }
        } else if (view.getId() == R.id.pause_download) {
            if(downloadBinder!=null){
                downloadBinder.pauseDownload();
            }
        } else if (view.getId() == R.id.cancel_download) {
            if(downloadBinder!=null){
                downloadBinder.cancelDownload();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}