package com.yyc.aidldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.servicetest.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG="AIDL MainActivity";
    private IntentFilter intentFilter;
    private PhoneReceiver phoneReceiver;
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG,"onServiceConnected");
            IMyAidlInterface imi = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                int showProgress = imi.showProgress();
                Log.e(TAG,"调用MyService中的AIDL中进度是: "+showProgress);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG,"onServiceDisconnected");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取通话权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS)!= PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_NUMBERS,Manifest.permission.CALL_PHONE},1);
        }
        Button startService = findViewById(R.id.start_service);
        Button stopService = findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        //活动和服务之间通信
        Button bindService = findViewById(R.id.bind_service);
        Button unbindService = findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        //TODO 监听电话
//        //监听广播,系统发出的android.net.conn.CONNECTIVITY_CHANGE广播
//        intentFilter=new IntentFilter();
//        intentFilter.addAction("android.intent.action.PHONE_STATE_CHANGED");
//        phoneReceiver=new PhoneReceiver();
//        //动态注册广播接收器,实现接收值为android.net.conn.CONNECTIVITY_CHANGE的广播
//        registerReceiver(phoneReceiver,intentFilter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>1&&grantResults[0]!=PackageManager.PERMISSION_GRANTED&&grantResults[1]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"获取权限失败",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.start_service){
            //远程启动服务
            Intent intent = new Intent();
            intent.setAction("com.test.myservice");
            intent.setPackage("com.test.servicetest");
            startService(intent);
        }else if(view.getId()==R.id.stop_service){
            //远程关闭服务
            Intent intent = new Intent("com.test.myservice");
            intent.setPackage("com.test.servicetest");
            stopService(intent);
        }else if(view.getId()==R.id.bind_service){
            //远程绑定服务
            Intent intent = new Intent("com.test.myservice");
            intent.setPackage("com.test.servicetest");
            bindService(intent,conn,BIND_AUTO_CREATE);
        }else if(view.getId()==R.id.unbind_service){
            //远程解绑服务
            unbindService(conn);
        }
    }
}