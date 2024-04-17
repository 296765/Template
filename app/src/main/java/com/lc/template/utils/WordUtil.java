package com.lc.template.utils;

import android.content.res.Resources;

import com.lc.template.base.CommonAppContext;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class WordUtil {

    private static Resources sResources;

    static {
        sResources = CommonAppContext.getInstance().getResources();
    }

    public static String getString(int res) {
        if(res==0){
            return "";
        }
        return sResources.getString(res);
    }

}
