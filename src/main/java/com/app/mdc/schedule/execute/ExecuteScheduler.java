package com.app.mdc.schedule.execute;

import com.app.mdc.controller.socket.MessageSocket;
import com.app.mdc.service.system.QuartzService;
import com.app.mdc.schedule.ScheduleJob;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ExecuteScheduler extends QuartzJobBean {

    @Autowired
    private QuartzService quartzService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        ApplicationContext applicationContext = MessageSocket.getApplicationContext();
        ScheduleJob quartz = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("schedule");
        SchedulerThread scheduleThread = new SchedulerThread(quartz, applicationContext,quartzService);
        Thread thread = new Thread(scheduleThread);
        thread.start();
    }
}
