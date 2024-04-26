package com.lc.template.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.lc.template.R;
import com.lc.template.custom.CustomUpdateParser;
import com.lc.template.custom.CustomUpdatePrompter;
import com.lc.template.dialog.LordingDialog;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xupdate.XUpdate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public abstract class BaseVBFragment<VB extends ViewBinding> extends Fragment {
    protected VB binding;
    private LordingDialog lordingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Type superclass = getClass().getGenericSuperclass();
        Class<VB> aClass = (Class<VB>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (VB) method.invoke(null, getLayoutInflater());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected abstract void initView();

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String msg) {
        if (lordingDialog == null) {
            lordingDialog = new LordingDialog(getContext());
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
        XUpdate.newBuild(getContext()).updateUrl("http://at.bx8023.top/api/index/androidInfo").updateParser(new CustomUpdateParser()) //设置自定义的版本更新解析器
//                .updatePrompter(new CustomUpdatePrompter())
                .promptThemeColor(ResUtils.getColor(R.color.color_main))
                .promptButtonTextColor(Color.WHITE)
                .promptTopResId(R.mipmap.logo)
                .promptWidthRatio(0.7F)
                .update();
    }
}
