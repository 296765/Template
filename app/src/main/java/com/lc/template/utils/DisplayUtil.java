package com.lc.template.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.lc.template.base.CommonAppContext;

/**
 * Created by Wei Ting
 * on 2024/4/18
 * Description
 */
public class DisplayUtil {
    public static int px2dp(float pxValue) {
        float scale = CommonAppContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int dp2px(float dpValue) {
        float scale = CommonAppContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int getScreenWidth(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) CommonAppContext.getInstance().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        return widthPixels;
    }
    public static int getScreenHeight(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) CommonAppContext.getInstance().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        int heightPixel = outMetrics.heightPixels - 2 * getStatusBarHeight();
        return heightPixel;
    }

    public static int getStatusBarHeight(){
        int height = 0;
        int resourceId = CommonAppContext.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = CommonAppContext.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }


    /**
     * 获取屏幕高度,包括状态栏
     */
    public static int getFullScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(dm);
        } else {
            display.getMetrics(dm);
        }
        return dm.heightPixels;
    }



}
