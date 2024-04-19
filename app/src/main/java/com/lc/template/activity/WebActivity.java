package com.lc.template.activity;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebSettings;

import com.alibaba.fastjson.JSON;
import com.lc.template.base.BaseVBActivity;
import com.lc.template.databinding.ActWebBinding;
import com.lc.template.utils.StringUtil;
import com.lc.template.utils.TextUtil;

/**
 * Created by Wei Ting
 * on 2024/4/19
 * Description
 */
public class WebActivity extends BaseVBActivity<ActWebBinding> {
    private int type;

    @Override
    protected void initView() {
        binding.top.rlBack.setOnClickListener(view -> finish());
        type = getIntent().getIntExtra("type", 0);
        WebSettings webSettings = binding.mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
//不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
//不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);

        binding.mWebView.loadDataWithBaseURL(null, TextUtil.getHtmlData(""), "text/html", "utf-8", null);
    }


    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, int type) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

}
