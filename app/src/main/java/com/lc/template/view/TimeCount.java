package com.lc.template.view;

import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.lc.template.R;
import com.lc.template.base.CommonAppContext;

/**
 * Created by Wei Ting
 * on 2022/9/21
 * Description
 * 验证码倒计时
 */
public class TimeCount extends CountDownTimer {
    private TextView view;
    private int colorId;

    public TimeCount(long millisInFuture, long countDownInterval, TextView view) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        colorId = R.color.color_main;
    }

    @Override
    public void onFinish() {// 计时完毕
        view.setClickable(true);
        view.setText("获取验证码");
        view.setTextColor(ContextCompat.getColor(CommonAppContext.getInstance().getApplicationContext(), colorId));
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程
        view.setClickable(false);
        view.setText(millisUntilFinished / 1000 + "s后重新发送");
        view.setTextColor(ContextCompat.getColor(CommonAppContext.getInstance().getApplicationContext(), colorId));
    }
}
