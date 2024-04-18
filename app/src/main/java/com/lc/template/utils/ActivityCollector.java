package com.lc.template.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Ting
 * on 2023/5/22
 * Description
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    /**
     * 添加Activity
     * @param activity 添加的Activity对象
     * */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 删除Activity
     * @param activity 删除的Activity对象
     * */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    /**
     * 关闭指定的Activity
     * @param activityName 需要关闭的Activity包名类名
     * */
    public static void finishOneActivity(String activityName){
        //在activities集合中找到类名与指定类名相同的Activity就关闭
        for (Activity activity : activities){
            String name= activity.getClass().getName();//activity的包名+类名
            if(name.equals(activityName)){
                if(activity.isFinishing()){
                    //当前activity如果已经Finish，则只从activities清除就好了
                    activities.remove(activity);
                } else {
                    //没有Finish则Finish
                    activity.finish();
                }
            }
        }
    }
}
