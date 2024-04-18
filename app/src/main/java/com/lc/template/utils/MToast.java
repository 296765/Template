package com.lc.template.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.lc.template.R;
import com.lc.template.base.CommonAppContext;

import java.util.ArrayList;
import java.util.List;

public class MToast {
    private Application application;
    private static List<Activity> activities = new ArrayList<>();

    public static void init(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activities.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activities.remove(activity);
            }
        });
    }

    public static void show(String message) {
        show(activities.get(activities.size() - 1), message);
    }

    public static void finishShow(String message) {
        if (activities.size() >= 2) {
            show(activities.get(activities.size() - 2), message);
        }
    }

    public static void show(Activity activity, String message) {
        //初始化一个snackbar
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), "", Snackbar.LENGTH_LONG);
        //修改snackbar的背景颜色
        //        snackbar.getView().setBackgroundResource(R.drawable.bg_toast);
        snackbar.getView().setBackgroundColor(CommonAppContext.getInstance().getResources().getColor(R.color.transparent));
        //获取snackbar的源布局
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setPadding(DisplayUtil.dp2px(12), 0, DisplayUtil.dp2px(12), DisplayUtil.dp2px(12));
        //隐藏源布局的控件
//        TextView textView = (TextView) layout.findViewById(R.id.snackbar_text);
//        textView.setVisibility(View.INVISIBLE);
        //获取自定义view
        View snackView = LayoutInflater.from(activity).inflate(R.layout.view_snackbar_layout, null);
        TextView messageTv = snackView.findViewById(R.id.toast_message);
        messageTv.setText(message);
        //添加自定义布局到SnackbarLayout
        layout.addView(snackView, 0);
        //显示snackbar
        snackbar.show();
    }
    public static void show(View view, String message) {
        //初始化一个snackbar
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        //修改snackbar的背景颜色
        //        snackbar.getView().setBackgroundResource(R.drawable.bg_toast);
        snackbar.getView().setBackgroundColor(CommonAppContext.getInstance().getResources().getColor(R.color.transparent));
        //获取snackbar的源布局
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setPadding(DisplayUtil.dp2px(12), 0, DisplayUtil.dp2px(12), DisplayUtil.dp2px(12));
        //隐藏源布局的控件
//        TextView textView = (TextView) layout.findViewById(R.id.snackbar_text);
//        textView.setVisibility(View.INVISIBLE);

        //获取自定义view
        View snackView = LayoutInflater.from(view.getContext()).inflate(R.layout.view_snackbar_layout, null);
        TextView messageTv = snackView.findViewById(R.id.toast_message);
        messageTv.setText(message);
        //添加自定义布局到SnackbarLayout
        layout.addView(snackView, 0);
        //显示snackbar
        snackbar.show();
    }


}