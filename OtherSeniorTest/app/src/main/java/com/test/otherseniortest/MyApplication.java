package com.test.otherseniortest;

import android.app.Application;
import android.content.Context;

/**
 * 自定义MyApplication继承Application ，可用于全局获取Context
 * 需要在AndroidManifest.xml中注册才能使用        android:name=".MyApplication"
 * 项目中只能配置一个Application，如果其他自定义Application可以在一起进行初始化
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
