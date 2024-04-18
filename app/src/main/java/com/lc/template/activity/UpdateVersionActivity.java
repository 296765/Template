package com.lc.template.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.lc.template.base.BaseVBActivity;
import com.lc.template.databinding.ActLoginInvalidBinding;
import com.lc.template.dialog.UpdateVersionDialog;
import com.lc.template.base.Constants;
import com.lc.template.utils.Y;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Ting
 * on 2024/4/18
 * Description
 * 使用方法：UpdateVersionActivity.actionStart("", true,CommonAppContext.getInstance()));
 */
public class UpdateVersionActivity extends BaseVBActivity<ActLoginInvalidBinding> {
    private String downloadUrl = "";//下载链接
    private boolean ifForce = false;//非强制更新

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(String downloadUrl, boolean ifForce, Context context) {
        Intent intent = new Intent(context, UpdateVersionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("downloadUrl", downloadUrl);
        intent.putExtra("ifForce", ifForce);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        downloadUrl = getIntent().getStringExtra("downloadUrl");
        ifForce = getIntent().getBooleanExtra("ifForce", false);//强制更新
        UpdateVersionDialog dialog = new UpdateVersionDialog(mContext, new UpdateVersionDialog.FriendlyReminderDialogListener() {
            @Override
            public void onClickCancel(View v, UpdateVersionDialog dialog) {
                if (!ifForce) {
                    dialog.dismiss();
                    finish();
                }
            }

            @Override
            public void onClickConfirm(View v, UpdateVersionDialog dialog) {
                downLoadApk();
                if (!ifForce) {
                    dialog.dismiss();
                    finish();
                }
            }
        });
        dialog.isCanClose(!ifForce);
        dialog.show();
    }


    private void downLoadApk() {
        if (isAvilible(mContext, "com.tencent.android.qqdownloader")) {
            // 市场存在
            launchAppDetail(mContext, Constants.PACKAGE_NAME, "com.tencent.android.qqdownloader");
        } else {
            Uri uri = Uri.parse("https://sj.qq.com/myapp/detail.htm?apkName=" + Constants.PACKAGE_NAME);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            this.startActivity(it);
        }
    }

    /**
     * 判断市场是否存在的方法
     */
    public boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        Y.e("isAvilible: pName :" + pName + "packageName:" + packageName);
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    /**
     * 启动到app详情界面
     *
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
