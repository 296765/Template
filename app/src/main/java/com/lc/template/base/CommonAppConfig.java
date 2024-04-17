package com.lc.template.base;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.lc.template.model.UserinfoBean;
import com.lc.template.utils.Constants;
import com.lc.template.utils.SpUtil;
import com.lc.template.utils.WordUtil;
import com.lc.template.utils.Y;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class CommonAppConfig {

    public static final String PACKAGE_NAME = "com.lc.template";

    //Http请求头 Header
    public static final Map<String, String> HEADER = new HashMap<>();

    //域名
    public static final String HOST = getHost();
    //public static final String HOST = getHost();

    //外部sd卡
    public static final String DCMI_PATH = getOutPath();
    //内部存储 /data/data/<application package>/files目录
    public static final String INNER_PATH = CommonAppContext.getInstance().getFilesDir().getAbsolutePath();
    //文件夹名字
    private static final String DIR_NAME = "zhibo";
    //拍照时图片保存路径
    public static final String CAMERA_IMAGE_PATH = DCMI_PATH + "/" + DIR_NAME + "/camera/";
    //log保存路径
    public static final String LOG_PATH = DCMI_PATH + "/" + DIR_NAME + "/log/";

    public static final String GIF_PATH = INNER_PATH + "/gif/";
    public static final String WATER_MARK_PATH = INNER_PATH + "/water/";

    //是否登录
    private boolean misLogin = false;
    //是否同意协议
    private boolean ishaveAgree = false;

    private static String getOutPath() {
        String outPath = null;
        try {
            File externalFilesDir = CommonAppContext.getInstance().getExternalFilesDir(DIR_NAME);
            if (externalFilesDir != null) {
                if (!externalFilesDir.exists()) {
                    externalFilesDir.mkdirs();
                }
                outPath = externalFilesDir.getAbsolutePath();
            }
        } catch (Exception e) {
            outPath = null;
        }
        if (TextUtils.isEmpty(outPath)) {
            outPath = CommonAppContext.getInstance().getFilesDir().getAbsolutePath();
        }
        return outPath;
    }

    private static CommonAppConfig sInstance;

    private CommonAppConfig() {

    }

    public static CommonAppConfig getInstance() {
        if (sInstance == null) {
            synchronized (CommonAppConfig.class) {
                if (sInstance == null) {
                    sInstance = new CommonAppConfig();
                }
            }
        }
        return sInstance;
    }

    private String mUid;
    private String mToken;
    private double mLng;
    private double mLat;
    private String mProvince;//省
    private String mCity;//市
    private String mDistrict;//区
    private String mVersion;
    private boolean mLaunched;//App是否启动了

    private boolean mFrontGround;
    private int mAppIconRes;
    private String mAppName;
    private UserinfoBean mUserBean;

    public String getUid() {
        if (TextUtils.isEmpty(mUid)) {
            String[] uidAndToken = SpUtil.getInstance().getMultiStringValue(new String[]{SpUtil.UID, SpUtil.TOKEN});
            if (uidAndToken != null) {
                if (!TextUtils.isEmpty(uidAndToken[0]) && !TextUtils.isEmpty(uidAndToken[1])) {
                    mUid = uidAndToken[0];
                    mToken = uidAndToken[1];
                }
            } else {
                return "-1";
            }
        }
        return mUid;
    }

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            String[] uidAndToken = SpUtil.getInstance().getMultiStringValue(new String[]{SpUtil.UID, SpUtil.TOKEN});
            if (uidAndToken != null) {
                if (!TextUtils.isEmpty(uidAndToken[0]) && !TextUtils.isEmpty(uidAndToken[1])) {
                    mUid = uidAndToken[0];
                    mToken = uidAndToken[1];
                }
            } else {
                return "";
            }
        }
        return mToken;
    }

    /**
     * 经度
     */
    public double getLng() {
        if (mLng == 0) {
            String lng = SpUtil.getInstance().getStringValue(SpUtil.LOCATION_LNG);
            if (!TextUtils.isEmpty(lng)) {
                try {
                    mLng = Double.parseDouble(lng);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mLng;
    }

    /**
     * 纬度
     */
    public double getLat() {
        if (mLat == 0) {
            String lat = SpUtil.getInstance().getStringValue(SpUtil.LOCATION_LAT);
            if (!TextUtils.isEmpty(lat)) {
                try {
                    mLat = Double.parseDouble(lat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mLat;
    }

    /**
     * 省
     */
    public String getProvince() {
        if (TextUtils.isEmpty(mProvince)) {
            mProvince = SpUtil.getInstance().getStringValue(SpUtil.LOCATION_PROVINCE);
        }
        return mProvince == null ? "" : mProvince;
    }

    /**
     * 市
     */
    public String getCity() {
        if (TextUtils.isEmpty(mCity)) {
            mCity = SpUtil.getInstance().getStringValue(SpUtil.LOCATION_CITY);
        }
        return mCity == null ? "" : mCity;
    }

    /**
     * 区
     */
    public String getDistrict() {
        if (TextUtils.isEmpty(mDistrict)) {
            mDistrict = SpUtil.getInstance().getStringValue(SpUtil.LOCATION_DISTRICT);
        }
        return mDistrict == null ? "" : mDistrict;
    }

    public void setUserBean(String userBeanJson) {
        SpUtil.getInstance().setStringValue(SpUtil.USER_INFO, userBeanJson);
        mUserBean = JSON.parseObject(userBeanJson, UserinfoBean.class);
    }


    public UserinfoBean getUserBean() {
        if (mUserBean == null) {
            String userBeanJson = SpUtil.getInstance().getStringValue(SpUtil.USER_INFO);
            if (!TextUtils.isEmpty(userBeanJson)) {
                mUserBean = JSON.parseObject(userBeanJson, UserinfoBean.class);
            }
        }
        return mUserBean;
    }






    /**
     * 设置登录信息
     */
    public void setLoginInfo(String uid, String token, boolean save) {
        Y.e("登录成功" + "uid------>" + uid);
        Y.e("登录成功" + "token------>" + token);
        mUid = uid;
        mToken = token;
        if (save) {
            Map<String, String> map = new HashMap<>();
            map.put(SpUtil.UID, uid);
            map.put(SpUtil.TOKEN, token);
            SpUtil.getInstance().setMultiStringValue(map);
        }
    }

    /**
     * 清除登录信息
     */
    public void clearLoginInfo() {
        mUid = null;
        mToken = null;
        SpUtil.getInstance().removeValue(
                SpUtil.UID, SpUtil.TOKEN, SpUtil.USER_INFO, SpUtil.IM_LOGIN,
                Constants.CASH_ACCOUNT_ID, Constants.CASH_ACCOUNT, Constants.CASH_ACCOUNT_TYPE
        );
    }


    /**
     * 设置位置信息
     *
     * @param lng      经度
     * @param lat      纬度
     * @param province 省
     * @param city     市
     */
    public void setLocationInfo(double lng, double lat, String province, String city, String district) {
        mLng = lng;
        mLat = lat;
        mProvince = province;
        mCity = city;
        mDistrict = district;
        Map<String, String> map = new HashMap<>();
        map.put(SpUtil.LOCATION_LNG, String.valueOf(lng));
        map.put(SpUtil.LOCATION_LAT, String.valueOf(lat));
        map.put(SpUtil.LOCATION_PROVINCE, province);
        map.put(SpUtil.LOCATION_CITY, city);
        map.put(SpUtil.LOCATION_DISTRICT, district);
        SpUtil.getInstance().setMultiStringValue(map);
    }

    /**
     * 清除定位信息
     */
    public void clearLocationInfo() {
        mLng = 0;
        mLat = 0;
        mProvince = null;
        mCity = null;
        mDistrict = null;
        SpUtil.getInstance().removeValue(
                SpUtil.LOCATION_LNG,
                SpUtil.LOCATION_LAT,
                SpUtil.LOCATION_PROVINCE,
                SpUtil.LOCATION_CITY,
                SpUtil.LOCATION_DISTRICT);

    }

    /**
     * 获取版本号
     */
    public String getVersion() {
        if (TextUtils.isEmpty(mVersion)) {
            try {
                PackageManager manager = CommonAppContext.getInstance().getPackageManager();
                PackageInfo info = manager.getPackageInfo(PACKAGE_NAME, 0);
                mVersion = info.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mVersion;
    }

    public static boolean isYunBaoApp() {
        if (!TextUtils.isEmpty(PACKAGE_NAME)) {
            return PACKAGE_NAME.contains("com.lc.template");
        }
        return false;
    }

    /**
     * 获取App名称
     */
    public String getAppName() {
        if (TextUtils.isEmpty(mAppName)) {
            int res = CommonAppContext.getInstance().getResources().getIdentifier("app_name", "string", "com.lc.template");
            mAppName = WordUtil.getString(res);
        }
        return mAppName;
    }


    /**
     * 获取App图标的资源id
     */
    public int getAppIconRes() {
        if (mAppIconRes == 0) {
            mAppIconRes = CommonAppContext.getInstance().getResources().getIdentifier("icon_app", "mipmap", "com.lc.template");
        }
        return mAppIconRes;
    }


    private static String getHost() {
        String host = "";//正式服务器地址
//        String host = "";//测试服务器地址
        return host;
    }

    /**
     * 判断某APP是否安装
     */
    public static boolean isAppExist(String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            PackageManager manager = CommonAppContext.getInstance().getPackageManager();
            List<PackageInfo> list = manager.getInstalledPackages(0);
            for (PackageInfo info : list) {
                if (packageName.equalsIgnoreCase(info.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isLaunched() {
        return mLaunched;
    }

    public void setLaunched(boolean launched) {
        mLaunched = launched;
    }

    public boolean getHaveAgree() {
        return ishaveAgree;
    }

    public void setHaveAgree(boolean haveAgree) {
        ishaveAgree = haveAgree;
    }

    //app是否在前台
    public boolean isFrontGround() {
        return mFrontGround;
    }

    //app是否在前台
    public void setFrontGround(boolean frontGround) {
        mFrontGround = frontGround;
    }

    public void setLogin(boolean isLogin) {
        misLogin = isLogin;
    }

    public boolean isLogin() {
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
            misLogin = true;
        } else {
            misLogin = false;
        }
        return misLogin;
    }

}
