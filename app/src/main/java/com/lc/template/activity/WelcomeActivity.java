package com.lc.template.activity;

import android.text.TextUtils;

import com.lc.template.MainActivity;
import com.lc.template.base.BaseVBActivity;
import com.lc.template.base.CommonAppConfig;
import com.lc.template.databinding.ActWelcomeBinding;
import com.lc.template.dialog.AgreenDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Wei Ting
 * on 2024/4/19
 * Description
 */
public class WelcomeActivity extends BaseVBActivity<ActWelcomeBinding> {

    @Override
    protected void initView() {
        if (CommonAppConfig.getInstance().getAgree()) {
            toApp();
        } else {
            showAgreenDialog();
        }
    }

    private void showAgreenDialog() {
        new AgreenDialog(this) {
            @Override
            public void cancels() {
                dismiss();
                finish();
            }

            @Override
            public void confirm() {
                CommonAppConfig.getInstance().setAgree(true);
                dismiss();
                toApp();
            }
        }.show();
    }

    private void toApp() {
        String token = CommonAppConfig.getInstance().getToken();
        String Guide = CommonAppConfig.getInstance().getGuide();
        Observable.timer(3, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(Long aLong) { }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() {
                if (TextUtils.isEmpty(Guide)) {
                    GuideActivity.actionStart(mContext);
                } else {
                    MainActivity.actionStart(mContext);
                }
            }
        });
    }
}