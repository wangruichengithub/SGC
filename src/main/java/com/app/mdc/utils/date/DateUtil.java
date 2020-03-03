package com.app.mdc.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class DateUtil {

    /**
     * 根据指定日期获取所需日期
     * @param dateFormat 日期类型 例如yyyy-MM-dd
     * @param day 获取指定日期之前或之后日期，负数表示获取之前，0表示当前，正数表示之后
     * @param date 指定日期
     * @return 所需日期
     */
    public static String getDate(String dateFormat,String changeFormat,int day,String date){
        Date d=getDate(dateFormat,date);
        return getDate(changeFormat,day,d);
    }

    /**
     * 根据指定的String日期获取所需格式的日期
     * @param dateFormat 日期格式
     * @param date 指定日期
     * @return Date
     */
    public static Date getDate(String dateFormat,String date){
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date d=new Date();
        try {
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }


    /**
     * 根据指定Date日期获取所需格式日期
     * @param dateFormat 日期格式
     * @param day 获取指定日期之前或之后日期，负数表示获取之前，0表示当前，正数表示之后
     * @param date 指定日期
     * @return String
     */
    public static String getDate(String dateFormat,int day,Date date){
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return sf.format(c.getTime());
    }

    /**
     * 根据指定Date日期获取所需格式日期
     * @param dateFormat 日期格式
     * @param day 获取指定日期之前或之后日期，负数表示获取之前，0表示当前，正数表示之后
     * @param date 指定日期
     * @return String
     */
    public static String getDate(String dateFormat,int type,int day,Date date){
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, day);
        return sf.format(c.getTime());
    }

    /**
     * 根据指定Date日期获取所需格式日期
     * @param dateFormat 日期格式
     * @param day 获取指定日期之前或之后日期，负数表示获取之前，0表示当前，正数表示之后
     * @param date 指定日期
     * @return String
     */
    public static String getDate(String dateFormat,int type,int day,String date){
        Date changeDate = getDate(dateFormat,date);
        return getDate(dateFormat,type,day,changeDate);
    }

    /**
     * 根据指定Date日期获取所需当前格式日期
     * @param day 获取指定日期之前或之后日期，负数表示获取之前，0表示当前，正数表示之后
     * @param date 日期
     * @return Date
     */
    public static Date getDate(int day,Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    /**
     * 根据日期获取不同类型的指定日期
     * @param calendarType 日期类型
     * @param day 获取指定日期之前或之后日期，负数表示获取之前，0表示当前，正数表示之后
     * @param date 指定日期
     * @return Date
     */
    public static Date getDate(int calendarType,int day,Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarType, day);
        return c.getTime();
    }

    /**
     * 获取需要的日期
     * @param calendarType 日期类型
     * @param needDay 获取指定日期之前或之后日期，负数表示获取之前，0表示当前，正数表示之后
     * @param date 日期
     * @return Date
     */
    public static Date getNeedDate(int calendarType,int needDay,Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(calendarType, needDay);
        return c.getTime();
    }

    /**
     * 获取只当日期的年份
     * @param date 日期
     * @return int
     */
    public static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取指定日期的月份
     * @param date 日期
     * @return int
     */
    public static int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH)+1;
    }

    /**
     * 获取指定日期的天份
     * @param date 日期
     * @return int
     */
    public static int getDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个日期之间的差值
     * @param firstDate 第一个日期
     * @param SecondDate 第二个日期
     * @return int
     */
    public static int betweenTwoDays(String firstDate,String SecondDate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //跨年的情况会出现问题哦

        Date fDate= new Date();
        Date oDate = new Date();
        try {
            fDate = sdf.parse(firstDate);
            oDate=sdf.parse(SecondDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return betweenTwoDays(fDate,oDate);
    }

    /**
     * 获取两个日期之间的差值
     * @param firstDate 第一个日期
     * @param SecondDate 第二个日期
     * @return int
     */
    public static int betweenTwoDays(Date firstDate,Date SecondDate){
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(firstDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(SecondDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2-day1;
    }

    //获取本月第一天
    public static String getFirstDay(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//1:本月第一天
		return format.format(c.getTime());
	}

	//获取本月最后一天
	public static String getLastDay(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(ca.getTime());
	}

	// 获取本月是哪一月
	public static String getMonth(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		return format.format(c.getTime());
	}

	//获取当天
    public static String getDay(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        return format.format(c.getTime());
    }
}
