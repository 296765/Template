package com.lc.template.http;

import com.lc.template.base.CommonAppConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

/**
 * Created by Wei Ting
 * on 2022/7/15
 * Description：
 */
public class MyHttpUtil {

    public static GetRequest<String> get(String serviceName) {
        GetRequest<String> request = OkGo.get(CommonAppConfig.HOST + serviceName);
        request.cacheKey("cacheKey").cacheMode(CacheMode.DEFAULT);
        return request;
    }

    public static GetRequest<String> getWithId(String serviceName) {
        GetRequest<String> request = OkGo.get(CommonAppConfig.HOST + serviceName);
        request.cacheKey("cacheKey").cacheMode(CacheMode.DEFAULT);
        request.headers("token", CommonAppConfig.getInstance().getToken());
        request.params("token", CommonAppConfig.getInstance().getToken());
        return request;
    }

    public static PostRequest<String> post(String serviceName) {
        PostRequest<String> request = OkGo.post(CommonAppConfig.HOST + serviceName);
        request.cacheKey("cacheKey").cacheMode(CacheMode.DEFAULT);
        return request;
    }

    public static PostRequest<String> postWithId(String serviceName) {
        PostRequest<String> request = OkGo.post(CommonAppConfig.HOST + serviceName);
        request.cacheKey("cacheKey").cacheMode(CacheMode.DEFAULT);
        request.headers("token", CommonAppConfig.getInstance().getToken());
        return request;
    }

}
