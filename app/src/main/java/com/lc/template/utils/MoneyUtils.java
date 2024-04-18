package com.lc.template.utils;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lc.template.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * Created by Wei Ting
 * on 2024/4/18
 * Description
 */
public class MoneyUtils {

    /**
     * 保留一位小数
     * */
    public static String formatNum(float num) {
        String format = new DecimalFormat("#.#").format(num);
        return format;
    }
    /**
     * 保留一位小数
     * */
    public static String formatNum(Double num) {
        String format = new DecimalFormat("#.#").format(num);
        return format;
    }
    /**
     * 保留两位小数
     * */
    public static String getMoney(double money) {
        String format = new DecimalFormat("#.##").format(money);
        return format;
    }

    /**
     * 保留两位小数
     * */
    public static String getMoney(float money) {
        String format = new DecimalFormat("#.##").format(money);
        return format;
    }

    public static Spannable setMoney(String money, float f) {

        Spannable span = new SpannableString(money);

        if (money.contains(".")) {

            span.setSpan(new RelativeSizeSpan(f), money.length()-2,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            return span;

        } else {

            return span;

        }


    }

    public static Spannable setMoney2(String money,float param) {

        Spannable span = new SpannableString(money);

        if (money.contains(".")) {

            span.setSpan(new RelativeSizeSpan(param), money.length()-2,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            return span;

        } else {

            return span;

        }


    }

    public static Spannable setMoneyAndSymbol(String money) {

        Spannable span = new SpannableString(money);

        if (money.contains(".")) {

            span.setSpan(new RelativeSizeSpan(0.8f), money.length()-2,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            return span;

        } else {

            return span;

        }


    }

    public static Spannable setMoneyAndSymbol(String money,float param) {

        Spannable span = new SpannableString(money);

        if (money.contains(".")) {

            span.setSpan(new RelativeSizeSpan(param), money.length()-2,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new RelativeSizeSpan(param), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            return span;

        } else {

            return span;

        }

    }

    public static Spannable setSalemoney(String money,float param) {
        Spannable span = new SpannableString(money);
        span.setSpan(new RelativeSizeSpan(param), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static Spannable setMoneyAndSymbol2(String money, float param) {
        Spannable span = new SpannableString(money);
        if (money.contains(".")) {
            span.setSpan(new RelativeSizeSpan(param), money.length()-2,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new RelativeSizeSpan(param), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span;
        } else {
            return span;
        }
    }

    public static Spannable setTextBefore(String money, float param, int startlength) {
        Spannable span = new SpannableString(money);
        span.setSpan(new RelativeSizeSpan(param), 0, startlength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static Spannable setSymbol(String money, float param) {
        Spannable span = new SpannableString(money);
        span.setSpan(new RelativeSizeSpan(param), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
    /**
     * @param money
     * @param startLength  文字的开始长度
     * @param param 倍数
     * @return
     */
    public static Spannable setMoneyAndSymbol3(String money,int startLength,float param) {
        Spannable span = new SpannableString(money);
        if (money.contains(".")) {
            span.setSpan(new RelativeSizeSpan(param), money.length()-2,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new RelativeSizeSpan(param), 0, startLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span;
        } else {
            return span;
        }
    }

    public static Spannable setMoneySyt(String money) {
        Spannable span = new SpannableString(money);
        if (money.contains(".")) {
            span.setSpan(new RelativeSizeSpan(0.8f), money.length()-3,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span;
        } else {
            return span;
        }

    }

    public static Spannable setViewPagerPosition(String money) {
        Spannable span = new SpannableString(money);
        if (money.contains("/")) {
            String[] split = money.split("/");
            span.setSpan(new RelativeSizeSpan(1.2f), 0,  split[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span;
        } else {
            return span;
        }
    }

    public static Spannable setLevel(String text,float level,int start,int end) {
        Spannable span = new SpannableString(text);
        span.setSpan(new RelativeSizeSpan(level), start,  end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
    public static Spannable setEnd2Level(String text,float level) {
        Spannable span = new SpannableString(text);
        span.setSpan(new RelativeSizeSpan(level), text.length()-2,  text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static Spannable setIntegral(String level,float f) {
        Spannable span = new SpannableString(level);
        span.setSpan(new RelativeSizeSpan(f), 2,  level.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    /**
     * @param money  改变合计的颜色
     * @return
     */
    public static Spannable setMoney3(String money, Context context) {
        Spannable span = new SpannableString(money);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_3)), 0,  3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }


    /**
     * @param money 字符串
     * @param i 开始index
     * @param i1 结束index
     * @return
     */
    public static Spannable setMiddleRedColor(Context context,String money, int i, int i1,int coloid) {
        Spannable span = new SpannableString(money);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(coloid)), money.length()-i1,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(coloid)), 0, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }


    /**
     * @param money 字符串
     * @param i 开始index
     * @param i1 结束index
     * @return
     */
    public static Spannable setMiddleRedColor2(Context context,String money, int i, int i1,int coloid) {

        Spannable span = new SpannableString(money);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(coloid)), money.length()-i1,  money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(coloid)), 0, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;

    }


    /**
     * @param textView 给价格加线
     */
    public static void setLine(TextView textView) {

        try {
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            textView.getPaint().setAntiAlias(true);// 抗锯齿
        }catch (Exception e){}

    }

    //方法三：
    public static boolean isNumeric(CharSequence str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 从字符串开始 变颜色
     * @param s 字符串
     * @param i 长度
     * @param coloid 颜色
     */
    public static Spannable setStartColor(Context context,String s, int i, int coloid) {
        Spannable span = new SpannableString(s);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(coloid)), 0, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    /**
     * @param price 清除小数点
     * @return
     */
    public static String clearD(String price) {
        if (price.contains(".")){
            price = price.split("\\.")[0];
        }
        return price;
    }

    /**
     * @param s 字符传
     * @param start 开始的位置
     * @param end 截取的位置 （从后面查的）
     * @return
     */
    public static String split(String s,int start ,int end) {
        return  s.substring(start,end);
    }

    /**
     * @param startTime
     * @param st
     * @param se
     * @return  把st 变成 se
     */
    public static String ChangeLineToPoint(String startTime,String st,String se) {
        return startTime.replace(st,se);
    }

    /**
     * @param viewGroup
     * @param goods_num 设置当goods_number = 0 时，viewGroup要消失
     */
    public static void setGoodsVisible(ViewGroup viewGroup, int goods_num) {
        if (goods_num == 0){
            viewGroup.setVisibility(View.GONE);
        } else {
            viewGroup.setVisibility(View.VISIBLE);
            ((TextView)viewGroup.getChildAt(0)).setText("商品("+goods_num+")");
        }
    }

    /**
     * @param messageNumber
     * @param number 当number=0时 messageNumber消失
     */
    public static void setmessage(TextView messageNumber, int number) {
        if (number == 0){
            messageNumber.setVisibility(View.GONE);
        } else {
            messageNumber.setVisibility(View.VISIBLE);
            if (number > 99){
                messageNumber.setText("...");
            } else {
                messageNumber.setText(number+"");
            }
        }
    }


    /**
     * 避免价格小数点后2位 不为整数时，微信价格错误
     * @param price
     * @return
     */
    public static Integer getWXPrice(String price){
        BigDecimal b = new BigDecimal(price);//必须使用字符串,不能使用浮点型数据
        BigDecimal aa = b.multiply(new BigDecimal(100));
        return  (int)aa.floatValue();//获取结果 990.0
    }

    /**
     * - format("###,###.##", 111222.34567)  ==> 111,222.35
     * @param pattern
     * @param value
     * @return
     * @author:
     * @date:
     */
    public static String format(String pattern, double value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    public static String format(double value) {
        DecimalFormat df = new DecimalFormat("###,###.##");
        return df.format(value);
    }

    /**
     * 人民币 羊角号
     *
     * @return ¥
     */
    public static String getYMoney(String money) {
        return "¥" + money;
    }

    /**
     * 人民币 羊角号 后两位缩小
     *
     * @return ¥
     */
    public static Spannable getYMoney2(String money) {
        return setSymbol("¥" + money,0.8f) ;
    }


    /**
     *两位小数补齐
     * @param price
     * @return
     */
    public static String getPrice(String price){
        DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return  decimalFormat.format( Double.parseDouble(price));//获取结果 990.0
    }
    public static String getPrice(int price){
        DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return  decimalFormat.format( Double.valueOf(price));//获取结果 990.0
    }


    public static String getPrice(double  price){
        DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return  decimalFormat.format(price);//获取结果 990.0
    }


    //合计
    public static String getTotal(String number1,String number2){
        if(number1.isEmpty()){
            number1="0";
        }
        if(number2.isEmpty()){
            number2="0";
        }
        BigDecimal num = new BigDecimal(number1);
        BigDecimal num1 =new BigDecimal(number2);
        System.out.print(  num.subtract(num1));
        return  num.subtract(num1)+"";

    }





}
