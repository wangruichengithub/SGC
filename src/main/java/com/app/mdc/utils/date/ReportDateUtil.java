package com.app.mdc.utils.date;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @ Author     ：zl
 * @ Date       ：2019/3/11 15:46
 * @ Description
 */
public class ReportDateUtil {

    /**
     *获取本月所有日期
     */
    public static List<String> getDayListOfThisMonth() {
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = String.valueOf(year)+"-"+String.format("%02d", month)+"-"+String.format("%02d", i);
            list.add(aDate);
        }
        return list;
    }

    /**
     *获取上个月所有日期
     */
    public static List<String> getDayListOfLastMonth() {
        List<String> list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        //aCalendar.set(aCalendar.MONTH, 1);

        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH);//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = String.valueOf(year)+"-"+String.format("%02d", month)+"-"+String.format("%02d", i);
            list.add(aDate);
        }
        return list;
    }

    /**
     *获取本月所有日期
     */
    public static String getToday() {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.get(Calendar.DATE);
        String aDate = String.valueOf(year)+"-"+String.format("%02d", month)+"-"+String.format("%02d", day);
        return aDate;
    }

    /**
     *获取月份
     */
    public static List<String> getMonth() {
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        for (int i = 1; i <= 12; i++) {
            String aDate = String.valueOf(year)+"-"+String.format("%02d", i);
            list.add(aDate);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(getDayListOfThisMonth());//本月
        System.out.println(getMonth());


    }

}
