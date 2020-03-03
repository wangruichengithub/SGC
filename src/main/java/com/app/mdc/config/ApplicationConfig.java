package com.app.mdc.config;

import com.app.mdc.service.system.QuartzService;
import com.app.mdc.schedule.QuartzUtils;
import com.app.mdc.schedule.ScheduleJob;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Order(value=1)
public class ApplicationConfig implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    private final QuartzService quartzService;

    private final SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    ApplicationConfig(QuartzService quartzService, SchedulerFactoryBean schedulerFactoryBean){
        this.quartzService = quartzService;
        this.schedulerFactoryBean = schedulerFactoryBean;
    }
    /**
     * 启动加载
     * @param args ApplicationArguments
     * @throws Exception exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        quartzRun();
    }

    private void quartzRun() throws Exception{
        logger.info("【系统参数】定时任务启动中...");
        /*List<ScheduleJob> scheduleJobList = quartzService.getScheduleJobs();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for(ScheduleJob scheduleJob : scheduleJobList){
            //添加定时
            QuartzUtils.addJob(scheduler, scheduleJob);

            //添加定时任务的时候，这边就已经把定时任务启动了。
//            //恢复该定时
//            QuartzUtils.resumeJob(scheduler,scheduleJob);
        }*/
        logger.info("【系统参数】定时任务启动完毕!");
    }

}
