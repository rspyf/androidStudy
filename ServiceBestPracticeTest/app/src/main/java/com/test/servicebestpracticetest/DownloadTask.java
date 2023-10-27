package com.test.servicebestpracticetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask  {
    private static final int TYPE_SUCCESS=100;
    private static final int TYPE_FAILED=101;
    private static final int TYPE_PAUSED=102;
    private static final int TYPE_CANCELED=103;
    private DownloadListener downloadListener;

    private boolean isCanceled = false;
    private boolean isPaused = false;
    private boolean isReading = false;

    private static final String TAG = "DownloadTask";

    private Context downContext;

    public DownloadTask(DownloadListener listener,Context context) {
        downloadListener = listener;
        downContext=context;
    }

    private int lastProgress=0;

    public void execute(String downloadUrl){
        Log.e(TAG,"execute: "+downloadUrl);
        //创建被观察者
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @SuppressLint("CheckResult")
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                InputStream is = null;
                RandomAccessFile saveFile = null;
                File file = null;

                try {
                    //下载文件长度
                    long downloadLength = 0;
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));

                    //TODO Android13系统不支持此文件路径读取数据
                    //String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    file = new File(downContext.getApplicationContext().getExternalFilesDir(null)+fileName);
                    if (file.exists()) {
                        downloadLength = file.length();
                    }
                    long contentLength = getContentLength(downloadUrl);
                    if(contentLength==0){
                        //获取文件长度为0，失败
                        emitter.onNext(TYPE_FAILED);;
                    } else if (contentLength == downloadLength) {
                        //下载成功
                        emitter.onNext(TYPE_SUCCESS); ;
                    }
                    //断点下载
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .addHeader("RANGE", "bytes=" + downloadLength + "-")
                            .url(downloadUrl)
                            .build();
                    Response response = client.newCall(request).execute();
                    if(response!=null&&response.isSuccessful()){
                        is=response.body().byteStream();
                        saveFile = new RandomAccessFile(file, "rw");
                        saveFile.seek(downloadLength);
                        byte[] bytes = new byte[1024];
                        int total=0;
                        int len;
                        while ((len=is.read())!=-1){
                            isReading=true;
                            if (isCanceled){
                                emitter.onNext(TYPE_CANCELED);
                                break;
                            }else if (isPaused){
                                emitter.onNext(TYPE_PAUSED);
                                break;
                            }else{
                                total+=len;
                                saveFile.write(bytes,0,len);
                                //计算以下载的百分比
                                int progress = (int) ((total + downloadLength) * 100 / contentLength);
                                Log.e("DownloadTask","execute progress: "+progress);
                                emitter.onNext(progress);
                            }
                        }
                        isReading=false;
                        response.body().close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(is!=null){
                            is.close();
                        }
                        if(saveFile!=null){
                            saveFile.close();
                        }
                        if(isCanceled&&file!=null){
                            //主动停止删除
                            file.delete();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())//耗时操作再IO线程
                .observeOn(AndroidSchedulers.mainThread())//再主线程中更新UI
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Log.e(TAG,"onNext: "+integer);
                        //根据接收到的类型进行处理
                        if(integer==TYPE_SUCCESS){
                            downloadListener.onSuccess();
                            lastProgress=integer;
                        }else if(integer==TYPE_FAILED){
                            downloadListener.onFailed();
                        }else if(integer==TYPE_PAUSED){
                            downloadListener.onPaused();
                        } else if (integer==TYPE_CANCELED) {
                            downloadListener.onCanceled();
                        }else {
                            //更新进度
                            if(integer>lastProgress){
                                downloadListener.onProgress(integer);
                                lastProgress=integer;
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void pauseDownload() {
        if (isReading) {
            isPaused = true;
        } else {
            downloadListener.onPaused();
        }
    }

    public void cancelDownload() {
        if (isReading) {
            isCanceled = true;
        } else {
            downloadListener.onCanceled();
        }
    }
    private long getContentLength(String downloadUrl) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(downloadUrl).build();
            Response response = client.newCall(request).execute();
            if(response!=null&&response.isSuccessful()){
                long contentLength=response.body().contentLength();
                response.close();
                return contentLength;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
