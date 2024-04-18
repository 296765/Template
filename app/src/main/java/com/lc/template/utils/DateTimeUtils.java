package com.lc.template.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Wei Ting
 * on 2024/4/18
 * Description
 */
public class DateTimeUtils {
    public static String getYYMMDD(Date date) {//可根据需要自行截取数据显示
        Log.e("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取日期
     */
    public static Date getData(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = format.parse(date);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 获取日期精确到秒
     */
    public static String getDataS(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return format.format(date);
    }


    //返回当前年月日
    public static String getNowDate() {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        return nowDate;
    }

    //返回当前年月日
    public static String getNowDate2() {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return nowDate;
    }

    //返回当前年份
    public static int getYear() {
        Date date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    //返回当前月份
    public static int getMonth() {
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    //返回当前日期
    public static int getDay() {
        Date date = new Date();
        String day = new SimpleDateFormat("dd").format(date);
        return Integer.parseInt(day);
    }

    //判断闰年
    public static boolean isLeap(int year) {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    //返回当月天数
    public static int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    //返回当月星期天数
    public static int getSundays(int year, int month) {
        int sundays = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Calendar setDate = Calendar.getInstance();
        //从第一天开始
        int day;
        for (day = 1; day <= getDays(year, month); day++) {
            setDate.set(Calendar.DATE, day);
            String str = sdf.format(setDate.getTime());
            if (str.equals("星期日")) {
                sundays++;
            }
        }
        return sundays;
    }

    public static void main(String[] args) {
        System.out.println("今天日期是：" + getNowDate());
        System.out.println("本月有" + getDays(getYear(), getMonth()) + "天");
        System.out.println("本月有" + getSundays(getYear(), getMonth()) + "个星期天");
    }


    public static int getDayofWeek(String dateTime) {
        Calendar cal = Calendar.getInstance();
        if (dateTime.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取日期精确
     */
    public static String getData(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return format.format(date);
    }
    /**
     * 比较当前时间和服务器返回时间大小
     *
     * @param
     * @param compareDate
     * @return
     */
    public static boolean compareDate( String compareDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date now = df.parse(getData(new Date(System.currentTimeMillis())));
            Date compare = df.parse(compareDate);
            if (now.after(compare)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static int compare_date(String DATE1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(getData(new Date(System.currentTimeMillis())));
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    public static String getMonth2(int m){

        return m < 10 ? "0" + m : m + "";
    }
    //后一天
    public static String getTomorrow(Date date) {
        String tomorrow = "";
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, +1);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        tomorrow = formatter.format(date);
        return tomorrow;
    }
    public static String getNewTime(String news_time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        long time = 0l;
        try {
            date = format.parse(news_time);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = new Date();
        long minute = (date2.getTime() - time) / 1000 / 60;
        long hour = minute / 60;
        if (minute < 15) {
            return "刚刚";
        } else if (minute >= 15 && minute < 30) {
            return "半个小时前";
        } else if (minute >= 30 && minute < 60) {
            return "1小时之前";
        } else if (hour >= 1 && hour < 24) {
            if (isSameDate(date, date2)) {
                return "今天";
            } else {
                return "昨天";
            }
        } else if (hour >= 24 && hour < 48) {
            long datanum = date2.getTime() - 24 * 60 * 60 * 1000;
            Date date3 = new Date(datanum);
            if (isSameDate(date, date3)) {
                return "昨天";
            } else {
                String result;
                try {
                    format = new SimpleDateFormat("MM.dd HH:mm");
                    result = format.format(date);
                } catch (Exception e) {
                    return news_time;
                }
                return result;
            }
        } else {
            String result;
            try {
                format = new SimpleDateFormat("MM.dd HH:mm");
                result = format.format(date);
            } catch (Exception e) {
                return news_time;
            }
            return result;
        }
    }


    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 date1
     * @param date2 date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }


    // 根据时间戳 转换成 几天前等
    public static String format(long timeMillis) {
        return format(new Date(timeMillis));
    }
    private static String format(Date date) {
        Calendar calendar = Calendar.getInstance();
        //当前年
        int currYear = calendar.get(Calendar.YEAR);
        //当前日
        int currDay = calendar.get(Calendar.DAY_OF_YEAR);
        //当前时
        int currHour = calendar.get(Calendar.HOUR_OF_DAY);
        //当前分
        int currMinute = calendar.get(Calendar.MINUTE);
        //当前秒
        int currSecond = calendar.get(Calendar.SECOND);

        calendar.setTime(date);
        int msgYear = calendar.get(Calendar.YEAR);
        //说明不是同一年
        if (currYear != msgYear) {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
        }
        int msgDay = calendar.get(Calendar.DAY_OF_YEAR);
        //超过7天，直接显示xx月xx日
        if (currDay - msgDay > 7) {
            return new SimpleDateFormat("MM-dd", Locale.getDefault()).format(date);
        }
        //不是当天
        if (currDay - msgDay > 0) {
            if (currDay - msgDay == 1) {
                return "昨天";
            } else {
                return currDay - msgDay + "天前";
            }
        }
        int msgHour = calendar.get(Calendar.HOUR_OF_DAY);
        int msgMinute = calendar.get(Calendar.MINUTE);
        //不是当前小时内
        if (currHour - msgHour > 0) {
            //如果当前分钟小，说明最后一个不满一小时
            if (currMinute < msgMinute) {
                if (currHour - msgHour == 1) {//当前只大一个小时值，说明不够一小时
                    return 60 - msgMinute + currMinute + "分钟前";
                } else {
                    return currHour - msgHour - 1 + "小时前";
                }
            }
            //如果当前分钟数大，够了一个周期
            return currHour - msgHour + "小时前";
        }
        int msgSecond = calendar.get(Calendar.SECOND);
        //不是当前分钟内
        if (currMinute - msgMinute > 0) {
            //如果当前秒数小，说明最后一个不满一分钟
            if (currSecond < msgSecond) {
                if (currMinute - msgMinute == 1) {//当前只大一个分钟值，说明不够一分钟
                    return "刚刚";
                } else {
                    return currMinute - msgMinute - 1 + "分钟前";
                }
            }
            //如果当前秒数大，够了一个周期
            return currMinute - msgMinute + "分钟前";
        }
        //x秒前
        return "刚刚";
    }

}
