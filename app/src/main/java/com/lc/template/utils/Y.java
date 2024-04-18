package com.lc.template.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.SPUtils;
import com.lc.template.R;
import com.lc.template.base.CommonAppContext;

import per.goweii.layer.core.Layer;
import per.goweii.layer.design.material.MaterialNotificationLayer;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class Y {

    private static final boolean open = true;

    public static void i(String str) {
        if (open)
            Log.i("安卓开发", "----------------------------     " + str + "      -------------------------------");
    }

    public static void e(String bodyMsg) {
        if (open) {
            if (bodyMsg.length() > 4000) {
                for (int i = 0; i < bodyMsg.length(); i += 4000) {
                    if (i + 4000 < bodyMsg.length()) {
                        Log.e("安卓长开发", bodyMsg.substring(i, i + 4000));
                    } else {
                        //当前截取的长度已经超过了总长度，则打印出剩下的全部信息
                        Log.e("安卓长开发", bodyMsg.substring(i, bodyMsg.length()));
                    }
                }
            } else {
                //直接打印
                Log.e("安卓开发:", "----------------------------     " + bodyMsg + "      -------------------------------");
            }
        }
    }

    public static void t(String str) {
        Toast.makeText(CommonAppContext.getInstance().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public static void tLong(String str) {
        Toast.makeText(CommonAppContext.getInstance().getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    public static Resources getResources() {
        return CommonAppContext.getInstance().getApplicationContext().getResources();
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static String getString(int resId) {
        if(resId==0){
            return "";
        }
        return getResources().getString(resId);
    }

    public static int getDimen(int resId) {
        return (int) getResources().getDimension(resId);
    }


    public static int getInt(String content) {
        if (!TextUtils.isEmpty(content)) {
            try {
                return (int) getDouble(content);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static long getLong(String content) {
        if (!TextUtils.isEmpty(content)) {
            try {
                return Long.parseLong(content);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static double getDouble(String content) {
        if (!TextUtils.isEmpty(content)) {
            try {
                return Double.parseDouble(content);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static float getFloat(String content) {
        if (!TextUtils.isEmpty(content)) {
            try {
                return Float.parseFloat(content);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 隐藏输入法
     *
     * @param view
     */
    public static void hideInputMethod(View view) {
        hideInputMethod(view, CommonAppContext.getInstance().getApplicationContext());
    }

    public static void hideInputMethod(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 通知-权限
     */
    public static void showNotification(Activity activity, String permissionName, String title) {
        String activityName = activity.getClass().getSimpleName();
        boolean isNotification = SPUtils.getInstance().getBoolean(activityName + permissionName);
        if (!isNotification) {
            new MaterialNotificationLayer(activity)
                    .setIcon(R.mipmap.logo)
                    .setLabel(R.string.app_name)
                    .setTitle("设备权限使用说明")
//                    .setDesc("用于设备识别,进行信息推送和保障等功能。")
                    .setDesc(title)
                    .setTimePattern("yyyy-MM-dd")
                    .addOnDismissListener(new Layer.OnDismissListener() {
                        @Override
                        public void onPreDismiss(@NonNull Layer layer) {

                        }

                        @Override
                        public void onPostDismiss(@NonNull Layer layer) {
                            SPUtils.getInstance().put(activityName + permissionName, true);
                        }
                    })
                    .show();
        }
    }




}
