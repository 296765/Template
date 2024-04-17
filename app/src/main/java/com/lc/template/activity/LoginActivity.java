package com.lc.template.activity;

import android.content.Context;
import android.content.Intent;

import com.lc.template.base.BaseVBActivity;
import com.lc.template.databinding.ActLoginBinding;

/**
 * Created by Wei Ting
 * on 2022/9/15
 * Description
 */
public class LoginActivity extends BaseVBActivity<ActLoginBinding> {
    @Override
    protected void initView() {

    }
    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
