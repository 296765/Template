package com.lc.template.custom;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public interface LifeCycleListener {

    void onCreate();

    void onStart();

    void onReStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
