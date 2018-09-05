package com.yunju.app.widget.calendar;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by sm on 2018/3/27 0027.
 */

public class FestivalUtil {

    private static int lunarYear; // 农历的年份
    private static int lunarMonth;
    private static int lunarDay;
    private static int leapMonth = 0; // 闰的是哪个月

    final static long[] lunarInfo = new long[]{ //
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, //
            0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, //
            0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, //
            0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, //
            0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, //
            0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, //
            0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, //
            0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, //
            0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, //
            0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, //
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, //
            0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, //
            0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, //
            0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, //
            0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, //
            0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, //
            0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};

    // 农历部分假日
    final static String[] lunarHoliday = new String[]{"01-01 春节", "01-15 元宵节", "05-05 端午节", "07-07 七夕节", "07-15 中元节", "08-15 中秋节", "09-09 重阳节", "12-08 腊八节", "12-24 小年", "12-30 除夕"};

    // 公历部分节假日
    final static String[] solarHoliday = new String[]{
            "01-01 元旦", "02-14 情人节", "03-08 妇女节", "03-12 植树节", "04-01 愚人节", "05-01 劳动节", "05-04 青年节",
            "06-01 儿童节", "07-01 建党节", "08-01 建军节", "09-10 教师节", "10-01 国庆节", "12-25 圣诞节"};

    /**
     * 是否为节日
     *
     * @param dateStr 格式 yyyy-MM-dd
     * @return
     */
    public static String isFestival(String dateStr) {

        String[] s = dateStr.split("-");
        int year = Integer.parseInt(s[0]);
        int month = Integer.parseInt(s[1]);
        int day = Integer.parseInt(s[2]);

        for (int i = 0; i < solarHoliday.length; i++) {
            // 返回公历节假日名称
            String date = solarHoliday[i].split(" ")[0]; // 节假日的日期
            String name = solarHoliday[i].split(" ")[1]; // 节假日的名称
            String dt = year + "-" + date;
            if (dateStr.equals(dt)) {
                return name;
            }
        }
        //母亲节
        if(month==5){
            if(getMothersDay(year)==day){
                return "母亲节";
            }
        }
        //父亲节
        if(month==6){
            if(getFathersDay(year)==day){
                return "父亲节";
            }
        }

        //清明节
        if (month == 4) {
            if (getQingmingDay(year) == day) {
                return "清明节";
            }
        }

        String lunarDate = getLunarDate(dateStr);
        for (int i = 0; i < lunarHoliday.length; i++) {
            // 返回农历节假日名称
            String date = lunarHoliday[i].split(" ")[0]; // 节假日的日期
            String name = lunarHoliday[i].split(" ")[1]; // 节假日的名称
            String dt = year + "-" + date;
            if (lunarDate.equals(dt)) {
                return name;
            }
        }
        return "";
    }

    /**
     * 计算清明节的日期(可计算范围: 1700-3100)
     *
     * @param year 需要计算的年份
     * @return 清明节在公历中的日期
     */
    public static int getQingmingDay(int year) {
        if (year == 2232) {
            return 4;
        }
        if (year < 1700) {
            Log.e("FestivalUtil", "1700年以前暂时不支持");
            return -1;
        }
        if (year >= 3100) {
            Log.e("FestivalUtil", "3100年以后暂时不支持");
            return -1;
        }
        double[] coefficient = {5.15, 5.37, 5.59, 4.82, 5.02, 5.26, 5.48, 4.70, 4.92, 5.135, 5.36, 4.60, 4.81, 5.04,
                5.26};
        int mod = year % 100;
        return (int) (mod * 0.2422 + coefficient[year / 100 - 17] - mod / 4);
    }

    /**
     * 计算母亲节：每年的5月份，第二个周日
     * @param year
     * @return dd
     */
    private static int getMothersDay(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 5-1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        int sundays = 0;
        for(int i = 1; i <= maxDate; i ++) {
            cal.set(Calendar.DATE, i);
            //周日
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sundays ++;
                //第二个周日，返回
                if(sundays == 2) {
                    break;
                }
            }
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//        Log.i("FestivalUtil",year+"年的母亲节是："+date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算父亲节：每年的6月份，第三个周日
     * @param year
     * @return dd
     */
    private static int getFathersDay(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 6-1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        int sundays = 0;
        for(int i = 1; i <= maxDate; i ++) {
            cal.set(Calendar.DATE, i);
            //周日
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sundays ++;
                //第三个周日，返回
                if(sundays == 3) {
                    break;
                }
            }
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//        Log.i("FestivalUtil",year+"年的父亲节是："+date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 传出yyyy-MM-dd对应的农历. yearCyl3:农历年与1864的相差数 ? monCyl4:从1900年1月31日以来,闰月数
     * dayCyl5:与1900年1月31日相差的天数,再加40
     *
     * @return
     */
    public static String getLunarDate(String dStr) {
        // @SuppressWarnings("unused")
        int yearCyl, monCyl, dayCyl;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        // int leapMonth = 0;
        Date baseDate = null;
        Date nowaday = null;
        try {
            baseDate = dateFormat.parse("1900-01-31");
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use
            // Options | File Templates.
        }

        try {
            nowaday = dateFormat.parse(dStr);
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use
            // Options | File Templates.
        }

        // 求出和1900年1月31日相差的天数
        int offset = (int) ((nowaday.getTime() - baseDate.getTime()) / 86400000L);
        dayCyl = offset + 40;
        monCyl = 14;

        // 用offset减去每农历年的天数
        // 计算当天是农历第几天
        // i最终结果是农历的年份
        // offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 10000 && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
            monCyl += 12;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
            monCyl -= 12;
        }
        // 农历年份
        lunarYear = iYear;
//        setYear(year); // 设置公历对应的农历年份

        yearCyl = iYear - 1864;
        leapMonth = leapMonth(iYear); // 闰哪个月,1-12
        boolean leap = false;

        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // 闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                --iMonth;
                leap = true;
                daysOfMonth = leapDays(lunarYear);
            } else
                daysOfMonth = monthDays(lunarYear, iMonth);

            offset -= daysOfMonth;
            // 解除闰月
            if (leap && iMonth == (leapMonth + 1))
                leap = false;
            if (!leap)
                monCyl++;
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --iMonth;
                --monCyl;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
            --monCyl;
        }
        lunarMonth = iMonth;
        lunarDay = offset + 1;

        if (lunarMonth < 10) {
            return lunarDay < 10 ? lunarYear + "-0" + lunarMonth + "-0" + lunarDay : lunarYear + "-0" + lunarMonth + "-" + lunarDay;
        } else {
            return lunarDay < 10 ? lunarYear + "-" + lunarMonth + "-0" + lunarDay : lunarYear + "-" + lunarMonth + "-" + lunarDay;
        }

    }

    /**
     * 传回农历 y年的总天数
     *
     * @param y 年份
     * @return 该年的总天数
     */
    private static int yearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(y));
    }

    /**
     * 传回农历 y年闰月的天数
     *
     * @param y 年份
     * @return 改年闰月天数
     */
    private static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    /**
     * 传回农历 y年闰哪个月 1-12 , 没闰传回 0
     *
     * @param y 年份
     * @return 改年闰月的月数
     */
    private static int leapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 0b1111);
    }

    /**
     * 传回农历 y年m月的总天数
     *
     * @param y 年份
     * @param m 月份
     * @return 该月份的总天数
     */
    private static int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }
}
