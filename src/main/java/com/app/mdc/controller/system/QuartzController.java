package com.app.mdc.controller.system;



import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.schedule.QuartzUtils;
import com.app.mdc.service.system.QuartzService;
import com.app.mdc.utils.date.QuartzCronDateUtils;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.app.mdc.schedule.ScheduleJob;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin/quarz")
public class QuartzController {
    private final QuartzService quartzService;

    @Autowired
    public QuartzController(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    /**
     * 创建定时任务
     */
    @PostMapping("/addjob")
    @SystemLogAnno(module = "定时任务", operation = "创建定时任务")
    @ResponseBody
    public ResponseResult startJob(@RequestParam Map<String,Object> map) {
        try {
            ScheduleJob schedulerJob = QuartzUtils.getScheduleJob((String)map.get("service"),(String)map.get("method"),(String)map.get("type"),(String)map.get("cron"),(String)map.get("data"));
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            QuartzUtils.addJob(scheduler, schedulerJob);
            quartzService.addQuartz(schedulerJob);
            //quartzService.addJob(DevopsJob.class, (String)map.get("job"), (String)map.get("jobGroupName"),cron ,map);
            return ResponseResult.success();
        }catch (Exception e){
            return ResponseResult.fail("ERR900","添加定时任务失败:"+e.getMessage());
        }

    }
    /**
     * 更新定时任务
     */
    /*@PostMapping("/updatejob")
    @SystemLogAnno(module = "定时任务", operation = "更新定时任务")
    @ResponseBody
    public ResponseResult updatejob(@RequestParam Map map) {
        try {
            String cron = (String)map.get("cron");
            if("1".equals(map.get("type"))){
                cron = QuartzCronDateUtils.getCron(DateUtil.getDate("yyyy-MM-dd HH:mm:ss",cron));
            }
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            ScheduleJob schedulerJob = QuartzUtils.getScheduleJob((String)map.get("service"),(String)map.get("method"),cron);
            QuartzUtils.
            //quartzService.updateJob((String)map.get("job"), (String)map.get("jobGroupName"), cron);
            return ResponseResult.success();
        }catch (Exception e){
            return ResponseResult.fail("ERR904","更新定时任务失败:"+e.getMessage());
        }
    }*/
    /**
     * 删除定时任务
     */
    @PostMapping("/deletejob")
    @SystemLogAnno(module = "定时任务", operation = "删除定时任务")
    @ResponseBody
    public ResponseResult deletejob(@RequestParam String jobId) {
        try {
            QuartzUtils.deleteJob(schedulerFactoryBean.getScheduler(),jobId);
            quartzService.deleteQuartz(jobId);
            return ResponseResult.success();
        } catch (Exception e) {
            return ResponseResult.fail("ERR901",e.getMessage());
        }
    }

    /**
     * 暂停定时任务
     */
    @PostMapping("/pauseJob")
    @SystemLogAnno(module = "定时任务", operation = "暂停定时任务")
    @ResponseBody
    public ResponseResult pauseJob(@RequestParam String jobId) {
        try {
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobId(jobId);
            QuartzUtils.pauseJob(schedulerFactoryBean.getScheduler(),scheduleJob);
            //quartzService.pauseJob((String)map.get("job"), (String)map.get("jobGroupName"));
            scheduleJob.setJobStatus("0");
            quartzService.updateQuartz(scheduleJob);
            return ResponseResult.success();
        }catch (Exception e) {
            return ResponseResult.fail("ERR902",e.getMessage());
        }
    }

    /**
     * 恢复定时任务
     */
    @PostMapping("/resumeJob")
    @SystemLogAnno(module = "定时任务", operation = "恢复定时任务")
    @ResponseBody
    public ResponseResult resumeJob(@RequestParam String jobId,String cron) {
        try {
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobId(jobId);
            QuartzUtils.resumeJob(schedulerFactoryBean.getScheduler(),scheduleJob);
            //quartzService.resumeJob((String)map.get("job"), (String)map.get("jobGroupName"));
            scheduleJob.setJobStatus("1");
            scheduleJob.setNextTime(QuartzCronDateUtils.getNextTime(cron));
            quartzService.updateQuartz(scheduleJob);
            return ResponseResult.success();
        }catch (Exception e) {
            return ResponseResult.fail("ERR903",e.getMessage());
        }

    }
    /*
    @PostMapping("/getJob")
    @ResponseBody
    public Object getJob() {
        return quartzService.getJob("job", "test");
    }*/
    /**
     * 查询所有定时任务
     * @return
     */
    @PostMapping("/queryAllJob")
    @ResponseBody
    public ResponseResult queryAllJob(Page page, Map<String,Object> params) {
        return quartzService.queryQuartz(page,params);
    }

    /**
     * 查询运行的定时任务
     * @return
     */
    @PostMapping("/queryRunJob")
    @ResponseBody
    public ResponseResult queryRunJob() {
        try {
            return ResponseResult.success().add(QuartzUtils.getRunningJob(schedulerFactoryBean.getScheduler()));
        } catch (SchedulerException e) {
            return ResponseResult.fail();
        }
    }

    @PostMapping("/runJobNow")
    @ResponseBody
    public ResponseResult runJobNow(@RequestParam String jobId){
        try{
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobId(jobId);
            QuartzUtils.runJobNow(schedulerFactoryBean.getScheduler(),scheduleJob);
            //quartzService.runAJobNow((String)map.get("job"), (String)map.get("jobGroupName"));
            return ResponseResult.success();
        }catch (Exception b){
            return ResponseResult.fail("ERR904",b.getMessage());
        }

    }

}
