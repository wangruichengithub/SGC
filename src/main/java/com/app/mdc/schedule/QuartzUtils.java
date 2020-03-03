package com.app.mdc.schedule;


import com.app.mdc.schedule.execute.ExecuteScheduler;
import com.app.mdc.utils.date.DateUtil;
import com.app.mdc.utils.date.QuartzCronDateUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import java.util.*;

public class QuartzUtils {

    public static List<Map<String,Object>> getAllJob(Scheduler scheduler) throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<Map<String,Object>> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                Map<String,Object> job = new HashMap<>();
                job.put("job", jobKey.getName());
                job.put("jobGroupName", jobKey.getGroup());
                job.put("description", "触发器:" + trigger.getKey());
                job.put("prevTime",trigger.getPreviousFireTime());
                job.put("nextTime",trigger.getNextFireTime());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.put("jobStatus",triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.put("jobTime", cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public static List<ScheduleJob> getRunningJob(Scheduler scheduler) throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 添加任务
     *
     * @throws SchedulerException
     */
    public static void addJob(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(ExecuteScheduler.class)
                .withIdentity(getJobKeyById(scheduleJob.getJobId())).build();

        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                .withMisfireHandlingInstructionDoNothing();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobId());

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        //将配置的调度任务参数防止在jobDataMap中
        jobDetail.getJobDataMap().put("schedule", scheduleJob);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ObjectAlreadyExistsException e) {
            //如果存在该定时任务则唤醒
            resumeJob(scheduler, scheduleJob);
        }
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob model
     * @throws SchedulerException
     */
    public static void pauseJob(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {
        scheduler.pauseJob(getJobKeyById(scheduleJob.getJobId()));
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob model
     * @throws SchedulerException
     */
    public static void resumeJob(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {
        scheduler.resumeJob(getJobKeyById(scheduleJob.getJobId()));
    }

    /**
     * 删除一个job
     *
     * @param jobId model
     * @throws SchedulerException
     */
    public static void deleteJob(Scheduler scheduler, String jobId) throws SchedulerException {
        String[] jobIds = jobId.split(",");
        for(String job : jobIds){
            scheduler.deleteJob(getJobKeyById(job));
        }
    }

    /**
     * 立即执行job
     *
     * @param scheduleJob model
     * @throws SchedulerException
     */
    public static void runJobNow(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {
        scheduler.triggerJob(getJobKeyById(scheduleJob.getJobId()));
    }


    private static JobKey getJobKeyById(String id){
        return JobKey.jobKey(id);
    }

    public static ScheduleJob getScheduleJob(String service,String method,String type,String cron,String data){
        ScheduleJob schedulerJob = new ScheduleJob();
        schedulerJob.setBeanClass(service);
        schedulerJob.setMethodName(method);
        schedulerJob.setIsConcurrent("0");
        if("1".equals(type)){
            cron = QuartzCronDateUtils.getCron(DateUtil.getDate("yyyy-MM-dd HH:mm:ss",cron));
            schedulerJob.setIsConcurrent("1");
        }
        schedulerJob.setJobStatus("1");
        schedulerJob.setCronExpression(cron);
        schedulerJob.setJobId(UUID.randomUUID().toString());
        schedulerJob.setParams(data);
        schedulerJob.setNextTime(QuartzCronDateUtils.getNextTime(cron));
        return schedulerJob;
    }
}
