package com.lc.template.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.lc.template.base.BaseVBActivity;
import com.lc.template.base.CommonAppConfig;
import com.lc.template.databinding.ActLoginInvalidBinding;
import com.lc.template.dialog.TishiDialog;

/**
 * Created by cxf on 2017/10/9.
 * 登录失效的时候以dialog形式弹出的activity
 */
public class LoginInvalidActivity extends BaseVBActivity<ActLoginInvalidBinding> {

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(String tip, Context context) {
        Intent intent = new Intent(context, LoginInvalidActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("tip", tip);
        context.startActivity(intent);
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void initView() {
        CommonAppConfig.getInstance().clearLoginInfo();
        String tip = getIntent().getStringExtra("tip");

        TishiDialog dialog = new TishiDialog(mContext, new TishiDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, TishiDialog dialog) {

            }

            @Override
            public void onClickConfirm(View v, TishiDialog dialog) {

                LoginActivity.actionStart(mContext);
                finish();
            }

            @Override
            public void onDismiss(TishiDialog dialog) {

            }
        });
        dialog.setCancel("");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContent(tip);
        dialog.show();

        binding.btnConfirm.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);

//        binding.content.setText(tip);
//        CommonAppConfig.getInstance().clearLoginInfo();
//        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}
