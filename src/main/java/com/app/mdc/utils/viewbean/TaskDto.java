package com.app.mdc.utils.viewbean;

/**
 * @ Author     ：zl
 * @ Date       ：2019/6/14 10:27
 * @ Description
 */
public class TaskDto {

    private String id;

    /**
     * 地址
     */
    private String taskAddress;
    /**
     * 任务内容
     */
    private String taskContent;
    /**
     * 起始时间
     */
    private String startTimeStr;
    /**
     * 预计结束时间
     */
    private String finishTimeStr;

    /**
     * 负责人
     */
    private String leader;
    /**
     * 执行人
     */
    private String executor;

    /**
     * 运维频率
     */
    private String devopsFrequency;


    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 公司id
     */
    //private String companyId;

    /**
     * 任务类型（1：任务计划 2：临时计划）
     */
    private String taskType;

    /**
     * 任务类别（1:巡检 2：校验 3：故障 4：校准）
     */
    private Integer category;

    private Integer devopsSize;

    private String operator;
    /**
     * 频率类型（:日，周，月，第一季度，第二季度，第三季度，第四季度）
     */
    private String frequencyType;

    private String companyId;

    private String companyName;

    /**
     * 频率
     */
    private Integer frequency;

    /**
     * 选择周的月份
     */
    private String weekMonth;

    private String taskTime;

    private String exceptionId;

    private String taskNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTaskAddress() {
        return taskAddress;
    }

    public void setTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getFinishTimeStr() {
        return finishTimeStr;
    }

    public void setFinishTimeStr(String finishTimeStr) {
        this.finishTimeStr = finishTimeStr;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getDevopsFrequency() {
        return devopsFrequency;
    }

    public void setDevopsFrequency(String devopsFrequency) {
        this.devopsFrequency = devopsFrequency;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }



    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public Integer getDevopsSize() {
        return devopsSize;
    }

    public void setDevopsSize(Integer devopsSize) {
        this.devopsSize = devopsSize;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getWeekMonth() {
        return weekMonth;
    }

    public void setWeekMonth(String weekMonth) {
        this.weekMonth = weekMonth;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "id='" + id + '\'' +
                ", taskAddress='" + taskAddress + '\'' +
                ", taskContent='" + taskContent + '\'' +
                ", startTimeStr='" + startTimeStr + '\'' +
                ", finishTimeStr='" + finishTimeStr + '\'' +
                ", leader='" + leader + '\'' +
                ", executor='" + executor + '\'' +
                ", devopsFrequency='" + devopsFrequency + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskType='" + taskType + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
