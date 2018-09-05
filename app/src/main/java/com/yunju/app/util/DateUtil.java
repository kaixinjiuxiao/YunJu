package com.yunju.app.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date工具类
 *
 * @author sm
 */
public class DateUtil {

    public static final String PATTERN_STANDARD08W = "yyyyMMdd";
    public static final String PATTERN_STANDARD12W = "yyyyMMddHHmm";
    public static final String PATTERN_STANDARD14W = "yyyyMMddHHmmss";
    public static final String PATTERN_STANDARD17W = "yyyyMMddHHmmssSSS";

    public static final String PATTERN_STANDARD10H = "yyyy-MM-dd";
    public static final String PATTERN_STANDARD16H = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_STANDARD10X = "yyyy/MM/dd";
    public static final String PATTERN_STANDARD16X = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_STANDARD19X = "yyyy/MM/dd HH:mm:ss";

    /**
     * 格式yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 秒 换算成时分秒
     *
     * @param second
     * @return
     */
    public static String secondToHour(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
            return d + "分" + s + "秒";
        }
        return h + "时" + d + "分" + s + "秒";

    }

    /**
     * 计算剩余时间
     *
     * @param timeStr "2006-05-26 12:00:00"
     * @return
     * @throws ParseException
     */
    public static long getLeftTime(String timeStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long days = 0, hours = 0, minutes = 0, totalSec = 0;
        try {
            Date d1 = df.parse(timeStr);
            Date d2 = new Date();

            totalSec = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
            // days = totalSec / (1000 * 60 * 60 * 24);
            // hours = (totalSec - days * (1000 * 60 * 60 * 24))
            // / (1000 * 60 * 60);
            // minutes = (totalSec - days * (1000 * 60 * 60 * 24) - hours
            // * (1000 * 60 * 60))
            // / (1000 * 60);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return totalSec;
    }

    /**
     * long转日期格式
     *
     * @param dateFormat
     * @param millSec
     * @return
     */
    public static String longToDateStr(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.CHINESE);
        // sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");// 获取中国的时区
        sdf.setTimeZone(timeZoneChina);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 两个日期之间相差天数
     *
     * @param smalldate
     * @param bigdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smalldate, String bigdate)
            throws ParseException {
        if (StringUtil.isNullString(smalldate)
                || StringUtil.isNullString(bigdate)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smalldate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bigdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smalldate 较小的时间
     * @param bigdate   较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smalldate, Date bigdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = sdf.format(smalldate);
        String bDate = sdf.format(bigdate);

        return daysBetween(sDate, bDate);
    }

    public static int daysOfTwo(String smalldate, String bigdate)
            throws ParseException {

        if (StringUtil.isNullString(smalldate)
                || StringUtil.isNullString(bigdate)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smalldate));
        int year1=cal.get(Calendar.YEAR);
        int day1 = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(sdf.parse(bigdate));
        int year2=cal.get(Calendar.YEAR);
        int day2 = cal.get(Calendar.DAY_OF_YEAR);

        if(year2>=year1) {
            return day2 - day1;
        }else{
            return -1*(day2 - day1);
        }

    }

    public static String getWantDate(String dateStr, String wantFormat) {
        if (StringUtil.isNotNullString(dateStr.trim())) {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch (len) {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 10:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD10H
                            : PATTERN_STANDARD10X;
                    break;
                case 16:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD16H
                            : PATTERN_STANDARD16X;
                    break;
                case 19:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD19H
                            : PATTERN_STANDARD19X;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(wantFormat);
            try {
                SimpleDateFormat sdfStr = new SimpleDateFormat(pattern);
                Date date = sdfStr.parse(dateStr);
                dateStr = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateStr;
    }

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 计算发布时间
     *
     * @param timeStr
     * @return
     */
    public static String getStandardDate(String timeStr) {
        String temp = "刚刚";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(timeStr);

            Log.i("getStandardDate", "d1=" + date);
            Log.i("getStandardDate", "d2=" + sdf.format(date));
            long publishTime = date.getTime();

            long now = new Date().getTime();
            long diff = now - publishTime;

            if (diff > year || diff > month) {
                temp = getWantDate(timeStr, "yyyy-MM-dd HH:mm");
                return temp;
            }
            if (diff > day) {
                if (diff / day == 1) {
                    temp = "昨天" + getWantDate(timeStr, "HH:mm");
                    return temp;
                } else {
                    temp = getWantDate(timeStr, "yyyy-MM-dd HH:mm");
                    return temp;
                }
            }
            if (diff > hour) {
                temp = diff / hour + "小时前";
                return temp;
            }
            if (diff > minute) {
                temp = diff / minute + "分钟前";
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     */
    public static String getWeek(String pTime) {
        String week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            week += "六";
        }
        return week;
    }

    /**
     * 当前日期增加几天
     * @param cDate 当前日期
     * @param dNum  增加天数
     * @return "yyyy-MM-dd"
     */
    public static String getDate(String cDate, int dNum) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sf.parse(cDate));
            c.add(Calendar.DAY_OF_MONTH, dNum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sf.format(c.getTime());
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     * @Description: (这里用一句话描述这个方法的作用)
     * @Author: gyz
     * @Since: 2017-1-9下午2:29:38
     * @param year
     * @param monthOfYear
     * @return
     */
    public static Date getEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return lastDate;
    }



    public static int getDaysByYearMonth(int year, int month) {

             Calendar a = Calendar.getInstance();
                a.set(Calendar.YEAR, year);
               a.set(Calendar.MONTH, month - 1);
                a.set(Calendar.DATE, 1);
                a.roll(Calendar.DATE, -1);
              int maxDate = a.get(Calendar.DATE);
                return maxDate;
            }
}
