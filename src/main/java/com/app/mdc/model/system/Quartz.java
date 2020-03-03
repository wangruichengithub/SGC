package com.app.mdc.model.system;

import java.sql.Blob;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 存储每一个已配置的 Job 的详细信息
 * </p>
 *
 * @author
 * @since 2019-08-02
 */
@TableName("qrtz_job_details")
public class Quartz extends Model<Quartz> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "SCHED_NAME")
    private String schedName;
    private String jobName;
    private String jobGroup;
    private String description;
    private String jobClassName;
    private String isDurable;
    private String isNonconcurrent;
    private String isUpdateData;
    private String requestsRecovery;
    private Blob jobData;


    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getIsDurable() {
        return isDurable;
    }

    public void setIsDurable(String isDurable) {
        this.isDurable = isDurable;
    }

    public String getIsNonconcurrent() {
        return isNonconcurrent;
    }

    public void setIsNonconcurrent(String isNonconcurrent) {
        this.isNonconcurrent = isNonconcurrent;
    }

    public String getIsUpdateData() {
        return isUpdateData;
    }

    public void setIsUpdateData(String isUpdateData) {
        this.isUpdateData = isUpdateData;
    }

    public String getRequestsRecovery() {
        return requestsRecovery;
    }

    public void setRequestsRecovery(String requestsRecovery) {
        this.requestsRecovery = requestsRecovery;
    }

    public Blob getJobData() {
        return jobData;
    }

    public void setJobData(Blob jobData) {
        this.jobData = jobData;
    }

    @Override
    protected Serializable pkVal() {
        return this.schedName;
    }

    @Override
    public String toString() {
        return "JobDetails{" +
        ", schedName=" + schedName +
        ", jobName=" + jobName +
        ", jobGroup=" + jobGroup +
        ", description=" + description +
        ", jobClassName=" + jobClassName +
        ", isDurable=" + isDurable +
        ", isNonconcurrent=" + isNonconcurrent +
        ", isUpdateData=" + isUpdateData +
        ", requestsRecovery=" + requestsRecovery +
        ", jobData=" + jobData +
        "}";
    }
}
