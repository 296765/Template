package com.lc.template.base;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.bulong.rudeness.RudenessScreenHelper;
import com.lc.template.BuildConfig;
import com.lc.template.bugly.BuglyManager;
import com.lc.template.http.OKHttpUpdateHttpService;
import com.lc.template.utils.MToast;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.tencent.mmkv.MMKV;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.strategy.impl.AlertDialogStrategy;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class CommonAppContext extends MultiDexApplication {
    private static CommonAppContext app;
    private boolean haveAgree;
    private static Handler sMainThreadHandler;
    public static CommonAppContext getInstance() {
        if (app == null) {
            app = new CommonAppContext();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initUpdate();
        app = this;

        //设计图标注的宽度
        int designWidth = 750;
        new RudenessScreenHelper(this, designWidth).activate();

        SharedPreferences preferences = getSharedPreferences("haveAgree", Context.MODE_PRIVATE);
        initOkGo();

        if (haveAgree) {

        }
        if (CommonAppConfig.getInstance().getAgree()) {
            BuglyManager.init(this, Constants.Bugly_appid, true);

        }
    }

    private void initUpdate() {
        XUpdate.get()
                .debug(true)
                .isWifiOnly(true)                                               //默认设置只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("versionCode", UpdateUtils.getVersionCode(this))         //设置默认公共请求参数
                .param("appKey", getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() {     //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                            MToast.show(error.toString());
                        }
                    }
                })
                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this);

    }

    @SuppressLint("RestrictedApi")
    private void init() {
        XUI.init(this);
        XUI.debug(BuildConfig.DEBUG);
        DialogLoader.getInstance().setIDialogStrategy(new AlertDialogStrategy());

        Utils.init(this);
        MToast.init(this);
        MMKV.initialize(this);

//        //防止项目崩溃，崩溃后打开错误界面
//        CaocConfig.Builder.create()
//                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
//                .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
//                .showErrorDetails(true) //是否必须显示包含错误详细信息的按钮 default: true
//                .showRestartButton(true) //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
//                .logErrorOnRestart(true) //是否必须重新堆栈堆栈跟踪 default: true
//                .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
//                .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
//                .restartActivity(MainActivity.class) // 重启的activity
//                .errorActivity(ErrorActivity.class) //发生错误跳转的activity
//                .apply();
    }

    private void initOkGo() {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数
    }

    public static void postDelayed(Runnable runnable, long delayMillis) {
        if (sMainThreadHandler == null) {
            getMainThreadHandler();
        }
        if (sMainThreadHandler != null) {
            sMainThreadHandler.postDelayed(runnable, delayMillis);
        }
    }
    /**
     * 获取主线程的Handler
     */
    private static void getMainThreadHandler() {
        sMainThreadHandler = new Handler();
    }



}
