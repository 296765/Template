package com.lc.template.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lc.template.R;
import com.lc.template.activity.WebActivity;

/**
 * 创建人：songjiatong
 * 创建时间：2021/04/15
 */
public abstract class AgreenDialog extends Dialog {

    private TextView appNameTv,agreementTv1,agreementTv2;

    public AgreenDialog(@NonNull Activity activity) {
        super(activity, R.style.dialog);
        setContentView(R.layout.dialog_agreen_layout);
        setCanceledOnTouchOutside(false);
        appNameTv = findViewById(R.id.tips_app_name_tv);
        agreementTv1 = findViewById(R.id.agreement_tv1);
        agreementTv2 = findViewById(R.id.agreement_tv2);
        appNameTv.setText("欢迎来到"+activity.getResources().getString(R.string.app_name)+"!");
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancels();
                dismiss();
            }
        });

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
                dismiss();
            }
        });
        agreementTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(activity, 1);
            }
        });
        agreementTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(activity, 2);
            }
        });
    }

    public abstract void cancels();

    public abstract void confirm();

}