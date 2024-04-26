package com.lc.template.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.lc.template.R;
import com.lc.template.custom.CustomUpdateParser;
import com.lc.template.custom.CustomUpdatePrompter;
import com.lc.template.custom.LifeCycleListener;
import com.lc.template.dialog.LordingDialog;
import com.lc.template.utils.ActivityCollector;
import com.xuexiang.xupdate.XUpdate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public abstract  class BaseVBActivity<VB extends ViewBinding> extends AppCompatActivity {
    protected Context mContext;
    protected ImmersionBar mImmersionBar;
    protected List<LifeCycleListener> mLifeCycleListeners;
    protected VB binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //在活动管理器添加当前Activity
        ActivityCollector.addActivity(this);
        initBar();
        initLifeListener();
        Type superclass = getClass().getGenericSuperclass();
        Class<VB> aClass = (Class<VB>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (VB) method.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        initView();
    }
    protected abstract void initView();
    private void initLifeListener() {
        mLifeCycleListeners = new ArrayList<>();
        if (mLifeCycleListeners != null) {
            for (LifeCycleListener listener : mLifeCycleListeners) {
                listener.onCreate();
            }
        }
    }

    protected void initBar() {
        mImmersionBar = ImmersionBar.with(this).transparentStatusBar()
                .statusBarDarkFont(true)
                .navigationBarEnable(false)
                .keyboardEnable(true)
                .fullScreen(true);
        mImmersionBar.init();
    }


    @Override
    protected void onDestroy() {
        if (mLifeCycleListeners != null) {
            for (LifeCycleListener listener : mLifeCycleListeners) {
                listener.onDestroy();
            }
            mLifeCycleListeners.clear();
            mLifeCycleListeners = null;
        }
        //从活动管理器删除当前Activity
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLifeCycleListeners != null) {
            for (LifeCycleListener listener : mLifeCycleListeners) {
                listener.onStart();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mLifeCycleListeners != null) {
            for (LifeCycleListener listener : mLifeCycleListeners) {
                listener.onReStart();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLifeCycleListeners != null) {
            for (LifeCycleListener listener : mLifeCycleListeners) {
                listener.onResume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLifeCycleListeners != null) {
            for (LifeCycleListener listener : mLifeCycleListeners) {
                listener.onPause();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLifeCycleListeners != null) {
            for (LifeCycleListener listener : mLifeCycleListeners) {
                listener.onStop();
            }
        }
    }

    public void addLifeCycleListener(LifeCycleListener listener) {
        if (mLifeCycleListeners != null && listener != null) {
            mLifeCycleListeners.add(listener);
        }
    }

    public void addAllLifeCycleListener(List<LifeCycleListener> listeners) {
        if (mLifeCycleListeners != null && listeners != null) {
            mLifeCycleListeners.addAll(listeners);
        }
    }

    public void removeLifeCycleListener(LifeCycleListener listener) {
        if (mLifeCycleListeners != null) {
            mLifeCycleListeners.remove(listener);
        }
    }


    private LordingDialog lordingDialog;

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String msg) {
        if (lordingDialog == null) {
            lordingDialog = new LordingDialog(mContext);
        }
        lordingDialog.setTextMsg(msg);

        if (!lordingDialog.isShowing()) {
            lordingDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (lordingDialog != null) {
            try {
                lordingDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    protected void update() {
        XUpdate.newBuild(this).updateUrl("http://at.bx8023.top/api/index/androidInfo").updateParser(new CustomUpdateParser()) //设置自定义的版本更新解析器
//                .updatePrompter(new CustomUpdatePrompter())
        .promptThemeColor(Color.parseColor("#D20011"))
                .promptButtonTextColor(Color.parseColor("#ffffff"))
                .promptTopResId(R.mipmap.logo)
                .promptWidthRatio(0.7F)
                .update();
    }
}
