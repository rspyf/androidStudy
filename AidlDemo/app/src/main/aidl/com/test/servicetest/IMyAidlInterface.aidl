// IMyAidlInterface.aidl
package com.test.servicetest;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    //定义自己所需要的方法：显示当前服务的进度
    int showProgress();
}