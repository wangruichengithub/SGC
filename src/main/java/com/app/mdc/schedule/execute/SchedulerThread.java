package com.app.mdc.schedule.execute;


import com.app.mdc.service.system.QuartzService;
import com.app.mdc.utils.date.QuartzCronDateUtils;
import com.app.mdc.schedule.ScheduleJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SchedulerThread implements Runnable{
    private final ScheduleJob quartz;
    private final ApplicationContext applicationContext;
    private final QuartzService quartzService;
    SchedulerThread(ScheduleJob quartz, ApplicationContext applicationContext,QuartzService quartzService) {
        this.quartz = quartz;
        this.applicationContext = applicationContext;
        this.quartzService = quartzService;
    }

    public void run() {
        try {
            //根据配置的bean获取类
            Object target = applicationContext.getBean(quartz.getBeanClass());
            //判断quartz中params参数（有参，无参调用），以及methodname获取调用的方法
            Method method;
            //result为该方法返回值，如果为void则为null；
            Object result;
            Map<String,Object> params = new HashMap<>();
            if(!StringUtils.isEmpty(quartz.getParams())){
                String param = quartz.getParams();
                String[] paras = param.split(",");
                for (String para1 : paras) {
                    String[] para = para1.split("=");
                    params.put(para[0], para[1]);
                }
            }
            method = target.getClass().getDeclaredMethod(quartz.getMethodName(), Map.class);
            ReflectionUtils.makeAccessible(method);
            result = method.invoke(target, params);
            System.out.println(result);
            quartz.setPrevTime(new Date());
            quartz.setNextTime(QuartzCronDateUtils.getNextTime(quartz.getCronExpression()));
            quartzService.updateQuartz(quartz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String printException(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

}
