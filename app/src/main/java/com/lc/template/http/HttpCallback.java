package com.lc.template.http;

import com.alibaba.fastjson.JSON;
import com.lc.template.activity.LoginInvalidActivity;
import com.lc.template.base.CommonAppContext;
import com.lc.template.base.Constants;
import com.lc.template.utils.Y;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by Wei Ting
 * on 2022/7/15
 * Description：
 */

public abstract class HttpCallback extends AbsCallback<String> {

    @Override
    public String convertResponse(okhttp3.Response response) throws Throwable {
        return response.body().string();
    }

    @Override
    public void onSuccess(Response<String> response) {
        JsonBean bean = JSON.parseObject(response.body(), JsonBean.class);
        if (bean.getCode() == Constants.CODE_SUCCEED) {
            onSuccess(response.body(), bean.getMessage());
        }  else if (bean.getCode() == Constants.CODE_LOGIN) {
            LoginInvalidActivity.actionStart(bean.getMessage(), CommonAppContext.getInstance());
        }else {
            onError(bean.getMessage());
        }
    }


    @Override
    public void onError(Response<String> response) {
        Throwable t = response.getException();
        Y.e("网络请求错误---->" + t.getClass() + " : " + t.getMessage());
        onError("网络请求失败");
    }

    @Override
    public void onFinish() {
        super.onFinish();
        onFinish("");
    }

    public abstract void onSuccess(String info, String msg);

    public abstract void onError(String msg);

    public abstract void onFinish(String msg);
}
