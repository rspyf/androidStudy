package com.test.servicebestpracticetest;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalThreadPools {
    private static ExecutorService executorService;

    public static  synchronized  ExecutorService getExecutorService(){
        if(executorService==null){
            executorService= Executors.newFixedThreadPool(2);
        }
        return executorService;
    }
    public static void ShutdownLocalThreadPools(){
        if(!executorService.isShutdown()){
            executorService.shutdown();
            Log.i("LocalThreadPools","shutdown");
        }
    }
}
