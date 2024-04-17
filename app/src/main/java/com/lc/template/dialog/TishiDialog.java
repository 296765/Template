package com.lc.template.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lc.template.R;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class TishiDialog extends Dialog implements View.OnClickListener {

    private TextView mContent;
    private TextView btn_cancel;
    private TextView btn_confirm;
    private View view_line;

    private TishiDialogListener mListener;
    protected boolean dismissAfterClick = true;//是否点击按钮后关闭

    public TishiDialog(Context context, TishiDialogListener mListener) {
        this(context, R.style.dialog);
        this.mListener = mListener;
        init();
    }

    private TishiDialog(Context context, int theme) {
        super(context, theme);
    }

    private void init() {
        setContentView(R.layout.dialog_tishi);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        mContent = findViewById(R.id.content);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_cancel = findViewById(R.id.btn_cancel);
        view_line = findViewById(R.id.view_line);

        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }


    public TishiDialog setCancel(String text) {//取消按钮  传空字符串隐藏
        if (TextUtils.isEmpty(text)) {
            btn_cancel.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
        } else {
            btn_cancel.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
            btn_cancel.setText(text);
        }
        return this;
    }

    public TishiDialog setConfirm(String text) {//确定按钮
        btn_confirm.setText(text);
        return this;
    }

    public TishiDialog setContent(String text) {//提示内容
        mContent.setText(text);
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_cancel) {
            clickCancel(v);
        } else if (v == btn_confirm) {
            clickConfirm(v);
        }
    }

    private void clickCancel(View v) {//取消按钮
        if (mListener != null) {
            mListener.onClickCancel(v, TishiDialog.this);
        }
        dismissAfterClick();
    }

    private void clickConfirm(View v) {//确定按钮
        if (mListener != null) {
            mListener.onClickConfirm(v, TishiDialog.this);
        }
        dismissAfterClick();
    }

    protected void dismissAfterClick() {
        if (dismissAfterClick) {
            dismiss();
        }
    }

    /**
     * 设置是否点击按钮后自动关闭窗口,默认true(是)
     */
    public TishiDialog setDismissAfterClick(boolean dismissAfterClick) {
        this.dismissAfterClick = dismissAfterClick;
        return this;
    }


    public interface TishiDialogListener {
        void onClickCancel(View v, TishiDialog dialog);

        void onClickConfirm(View v, TishiDialog dialog);

        void onDismiss(TishiDialog dialog);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (mListener != null) {
            mListener.onDismiss(TishiDialog.this);
        }
    }
}
