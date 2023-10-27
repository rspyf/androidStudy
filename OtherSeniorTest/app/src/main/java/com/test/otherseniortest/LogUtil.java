package com.test.otherseniortest;

import android.util.Log;

/**
 * 日志工具
 */
public class LogUtil {
    public static final int VERBOSE=1;
    public static final int DEBUG=2;
    public static final int INFO=3;

    public static final int WARN=4;

    public static final int ERROR=5;

    public static final int NOTHING=6;

    public static final int level=7;

    public static void v(String tag,String msg){
        if(level<=VERBOSE){
            Log.v(tag,msg);
        }
    }

    public static void d(String tag,String mg){
        if(level<=DEBUG){
            Log.d(tag,mg);
        }
    }
    public static void i(String tag,String mg){
        if(level<=INFO){
            Log.i(tag,mg);
        }
    }
    public static void w(String tag,String mg){
        if(level<=WARN){
            Log.w(tag,mg);
        }
    }
    public static void e(String tag,String mg){
        if(level<=ERROR){
            Log.e(tag,mg);
        }
    }


}
