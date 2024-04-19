package com.lc.template.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class TextUtil {

    public static boolean isNull(String str) {

        return str == null || str.length() == 0 || str.equals("null");
    }

    //TODO 设置文字 判空
    public static String setTextStr(String str) {
        String endString = "";
        if (!isNull(str)) {
            endString = str;
        }
        return endString;
    }

    //TODO 设置手机号 脱敏
    public static String setCardNumberStr(String str) {
        String endString = "";
        if (!isNull(str)) {
            endString = str.substring(0, 4) + "**** ****" + str.substring(str.length() - 5, str.length() - 1);
        }
        return endString;
    }

    //TODO 设置性别
    public static String setGenderStr(int sex) {
        return (sex == 1) ? "男" : "女";
    }

    //TODO 设置个人信息
    public static String setInfoStr(String info) {
        return (isNull(info)) ? "保密" : info;
    }


    /**
     * @param context
     * @param color   变色
     * @param text1
     * @param text2
     * @param text3
     * @return
     */
    public static Spanned getHtmlTextview(Context context, String color, String text1, String text2, String text3) {
        String ljq = text1 + "<font color='" + color + "'>" + text2 + "</font>" + text3;
        return Html.fromHtml(ljq);
    }


    /**
     * 加密
     * oldWord：需要加密的文字/比如密码
     */
    public void setEncryption(String oldWord) {


        try {
            String encodeWord = Base64.encodeToString(oldWord.getBytes("utf-8"), Base64.NO_WRAP);
            Log.i("Tag", " encode wrods = " + encodeWord);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    /**
     * 解密
     * encodeWord：加密后的文字/比如密码
     */
    public void setDecrypt(String encodeWord) {

        try {
            String decodeWord = new String(Base64.decode(encodeWord, Base64.NO_WRAP), "utf-8");
            Log.e("解密base64 decode=", decodeWord);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param textView
     * @param iconId
     * @param status   0=left 1=top 2=right 3=bottom
     */
    public static void setTextDrawableImg(Context context, TextView textView, int iconId, int status) {
        Drawable drawable = context.getResources().getDrawable(iconId);
        if (status == 0) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else if (status == 1) {
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        } else if (status == 2) {
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else if (status == 3) {
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
        }
        textView.setCompoundDrawablePadding(ConvertUtils.dp2px(4f));
    }

    //复制剪切板
    public static void setClipData(Context context, String content) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

    }


    /**
     * @param str 以逗号分隔的字符串，变成list
     * @return
     */
    public static List<String> getStrToList(String str) {
        List<String> list = new ArrayList<>();
        if (!isNull(str) && str.contains(",")) {
            String[] strArray = null;
            strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
            for (int i = 0; i < strArray.length; i++) {
                list.add(strArray[i]);
            }
        } else {
            list.add(setTextStr(str));
        }
        return list;
    }


    /**
     * 设置文字渐变色
     *
     * @param textView
     */
    public static void setGradient(TextView textView, int color1, int color2) {
        float endX = textView.getPaint().getTextSize() * textView.getText().length();
        LinearGradient linearGradient = new LinearGradient(0f, 0f, endX, 0f, textView.getResources().getColor(color1)
                , textView.getResources().getColor(color2), Shader.TileMode.CLAMP);
        textView.getPaint().setShader(linearGradient);
        textView.invalidate();
    }

    /**
     * @param str
     * @param specifiedStr
     * @return 截取指定字符后面的
     */
    public static String getEndStr(String str, String specifiedStr) {
        String str1 = str.substring(0, str.indexOf(specifiedStr));
        String str2 = str.substring(str1.length(), str.length());
        return str2;
    }

    public static String replaceEmpty(String str) {
        return TextUtils.isEmpty(str) || str.equals("null") ? "-" : str;
    }

    public static String replaceEmpty(String str, String default_str) {
        return TextUtils.isEmpty(str) || str.equals("null") ? default_str : str;
    }

    public static String replaceEmptyCount(String str) {
        return TextUtils.isEmpty(str) || str.equals("null") ? "0" : str;
    }

    public static String replaceEmpty2(String str) {

        return TextUtils.isEmpty(str) || str.equals("null") ? "" : str;
    }

    public static boolean getPasswordString(EditText appPassword, EditText appPassword2) {
        String string = appPassword.getEditableText().toString();
        if (string.length() == 0) {
            ToastUtils.showShort(appPassword.getHint());
            return false;
        } else if (string.length() < 6) {
            ToastUtils.showShort("密码不能小于6位");
            return false;
        } else if (appPassword2 != null && !string.equals(appPassword2.getEditableText().toString())) {
            ToastUtils.showShort("两次密码输入不符");
            return false;
        }
        return true;
    }


    public static String getMonth(String str) {
        if (!isNull(str)) {
            return str.substring(5, 7);
        }
        return "";
    }

    public static String getDate(String str) {
        if (!isNull(str)) {
            return str.substring(8, 10);
        }
        return "";
    }

    public static String getyyyMMdd(String str) {
        if (!isNull(str)) {
            //            Log.e("getMMdd",str.substring(5,10));
            return str.substring(0, 10);
        }
        return "";
    }

    public static String getMMdd(String str) {
        if (!isNull(str)) {
            //            Log.e("getMMdd",str.substring(5,10));
            return str.substring(5, 10);
        }
        return "";
    }

    public static String getHHmm(String str) {
        if (!isNull(str)) {
            //            Log.e("getMMdd",str.substring(5,10));
            return str.substring(str.length() - 5, str.length());
        }
        return "";
    }

    public static String getActivityTime(String str) {
        if (!isNull(str)) {
            return str.substring(5, 10).replace("-", ".");
        }
        return "";
    }

    //收起 |展开
    public static String getPickUp(boolean isUp) {
        if (isUp) {
            return "收起";
        } else {
            return "展开";

        }
    }

    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "<style>video{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

}
