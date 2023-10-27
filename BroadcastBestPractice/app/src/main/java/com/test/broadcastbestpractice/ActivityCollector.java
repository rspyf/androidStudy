package com.test.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理所有活动
 */
public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<>();


    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        activities.stream().filter(e->!e.isFinishing()).forEach(Activity::finish);
    }
}
