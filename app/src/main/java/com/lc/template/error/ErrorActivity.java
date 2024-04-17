package com.lc.template.error;

import com.lc.template.base.BaseVBActivity;
import com.lc.template.databinding.ActivityErrorBinding;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class ErrorActivity extends BaseVBActivity<ActivityErrorBinding> {
    CaocConfig config;//配置对象
    @Override
    protected void initView() {
        config = CustomActivityOnCrash.getConfigFromIntent(getIntent());//获得配置信息,比如设置的程序崩溃显示的页面和重新启动显示的页面等等信息
        binding.toolbar.setTitle("发送错误");
        binding.errorRestart.setOnClickListener(view ->
                CustomActivityOnCrash.restartApplication(ErrorActivity.this, config));

        binding.errorSendError.setOnClickListener(view ->
                CustomActivityOnCrash.getStackTraceFromIntent(getIntent())

        );
    }
}
