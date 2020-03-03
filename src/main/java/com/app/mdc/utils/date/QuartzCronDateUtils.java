package com.app.mdc.utils.date;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuartzCronDateUtils {
    /***
     *  功能描述：日期转换cron表达式时间格式
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return cron字符产
     */
    public static String formatDateByPattern(Date date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
    /***
     * convert Date to cron ,eg.  "14 01 17 22 07 ? 2017"
     * @param date:时间点
     * @return 返回cron字符串
     */
    public static String getCron(Date  date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date,dateFormat);
    }

    public static Date getNextTime(String cron){
        CronTriggerImpl cronTriggerImpl  = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(cron);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 1);
        if(dates.size() > 0){
            return dates.get(0);
        }else{
            return null;
        }
    }
}
