package com.yyc.aidldemo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PhoneReceiver extends BroadcastReceiver {
    private static final String TAG="PhoneReceiver";
    public PhoneReceiver() {
        super();
    }

    @SuppressLint({"ServiceCast", "SoonBlockedPrivateApi"})
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"来电话了");
        //获取电话服务
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //读取状态
        switch (manager.getCallState()){
            case TelephonyManager.CALL_STATE_RINGING:
                //响铃
                String number = intent.getStringExtra("incoming_number");
                Log.e(TAG,"响铃："+number);
                //获取TelephonyManager类对象
                Class cls = TelephonyManager.class;
                //获取对应的方法
                try {
                    Method m = cls.getDeclaredMethod("getITelephony");
                    //设置方法可以被操作
                    m.setAccessible(true);
                    try {
                        ITelephony iTelephony = (ITelephony) m.invoke(manager);
                        //调用endCall方法
                        try {
                            iTelephony.endCall();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //通话中
                Log.e(TAG,"通话中");
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.e(TAG,"闲置");
                break;
        }
    }
}