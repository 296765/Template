package com.lc.template.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lc.template.R;

/**
 * Created by Wei Ting
 * on 2023/12/25
 * Description 版本更新窗口
 */
public class UpdateVersionDialog extends Dialog implements View.OnClickListener {
    private TextView affirm_cancel_tv, affirm_affirm_tv;

    protected boolean dismissAfterClick = true;//是否点击按钮后关闭
    private FriendlyReminderDialogListener mListener;

    public UpdateVersionDialog(Context context, FriendlyReminderDialogListener mListener) {
        this(context, R.style.dialog);
        this.mListener = mListener;
        init();
    }

    private UpdateVersionDialog(Context context, int theme) {
        super(context, theme);
    }

    private void init() {
        setContentView(R.layout.dialog_affirm_layout);
//        setCanceledOnTouchOutside(true);
//        setCancelable(true);
        affirm_cancel_tv=findViewById(R.id.affirm_cancel_tv);
        affirm_affirm_tv=findViewById(R.id.affirm_affirm_tv);

        affirm_cancel_tv.setOnClickListener(this);
        affirm_affirm_tv.setOnClickListener(this);
    }

    /**
     * 设置是否点击按钮后自动关闭窗口,默认true(是)
     */
    public UpdateVersionDialog setDismissAfterClick(boolean dismissAfterClick) {
        this.dismissAfterClick = dismissAfterClick;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.affirm_cancel_tv:
                onCancel(v);
                break;

            case R.id.affirm_affirm_tv:
                onAffirm(v);
                break;

        }
    }

    private void onCancel(View v) {//取消按钮
        if (mListener != null) {
            mListener.onClickCancel(v, UpdateVersionDialog.this);
        }
    }

    private void onAffirm(View v) {//确定按钮
        if (mListener != null) {
            mListener.onClickConfirm(v, UpdateVersionDialog.this);
        }

    }

    public void isCanClose(boolean isCan) {
        setCanceledOnTouchOutside(isCan);
        affirm_cancel_tv.setVisibility(isCan?View.VISIBLE:View.GONE);
    }

    public interface FriendlyReminderDialogListener {
        void onClickCancel(View v, UpdateVersionDialog dialog);

        void onClickConfirm(View v, UpdateVersionDialog dialog);
    }

}
