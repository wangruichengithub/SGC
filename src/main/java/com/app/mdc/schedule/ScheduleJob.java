package com.app.mdc.schedule;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("sys_quartz")
public class ScheduleJob {
    @TableId(value = "job_id", type = IdType.UUID)
    private String jobId;

    private Date createTime;

    private Date updateTime;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 是否启动任务
     */
    private String jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 描述
     */
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;
    /**
     * 任务是否有状态
     */
    private String isConcurrent;
    /**
     * spring bean
     */
    private String springId;
    /**
     * 任务调用的方法名
     */
    private String methodName;

    /**
     * 任务需要的参数
     */
    private String params;

    private Date prevTime;

    private Date nextTime;

    public String getParams() {
        return params;
    }

    public ScheduleJob setParams(String params) {
        this.params = params;
        return this;
    }

    public String getJobId() {
        return jobId;
    }

    public ScheduleJob setJobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ScheduleJob setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ScheduleJob setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public ScheduleJob setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public ScheduleJob setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        return this;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public ScheduleJob setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
        return this;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public ScheduleJob setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ScheduleJob setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public ScheduleJob setBeanClass(String beanClass) {
        this.beanClass = beanClass;
        return this;
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public ScheduleJob setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
        return this;
    }

    public String getSpringId() {
        return springId;
    }

    public ScheduleJob setSpringId(String springId) {
        this.springId = springId;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public ScheduleJob setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Date getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(Date prevTime) {
        this.prevTime = prevTime;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }
}
