package com.test.servicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyService.DownloadBinder downloadBinder;
    private static final String TAG="ServiceMainActivity";

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG,"onServiceConnected");
          //使用Service进行通信
         /**   downloadBinder=(MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
          */
         //使用AIDL进行通信
            IMyAidlInterface imi = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                int i = imi.showProgress();
                Log.e(TAG,"当前MyService进度： "+i);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = findViewById(R.id.start_service);
        Button stopService = findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        //活动和服务之间通信
        Button bindService = findViewById(R.id.bind_service);
        Button unbindService = findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.start_service){
            Intent startIntent = new Intent(this, MyService.class);
            //启动服务
            startService(startIntent);
        }else if(view.getId()==R.id.stop_service){
            Intent stopIntent = new Intent(this, MyService.class);
            //关闭服务
            stopService(stopIntent);
        } else if (view.getId()==R.id.bind_service) {
            //绑定服务
            Intent bindIntent = new Intent(this, MyService.class);
            bindService(bindIntent,connection,BIND_AUTO_CREATE);
        } else if (view.getId() == R.id.unbind_service) {
            //解绑服务
            unbindService(connection);
        }
    }
}