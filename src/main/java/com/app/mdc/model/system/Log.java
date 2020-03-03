package com.app.mdc.model.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author
 * @since 2019-08-01
 */
@TableName("sys_log")
@Data
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String brower;
    @TableField(value = "create_time")
    private Date createtime;
    private String ip;
    private String module;
    private String operation;
    private String remarks;
    private String status;
    @TableField("update_time")
    private Date updatetime;
    @TableField("user_name")
    private String username;
    private Integer deleted;
    @TableField("request_tx")
    private String requestTx;
    @TableField("response_tx")
    private String responseTx;

    public String getId() {
        return id;
    }

    public Log setId(String id) {
        this.id = id;
        return this;
    }

    public String getBrower() {
        return brower;
    }

    public Log setBrower(String brower) {
        this.brower = brower;
        return this;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public Log setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Log setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getModule() {
        return module;
    }

    public Log setModule(String module) {
        this.module = module;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public Log setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public Log setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Log setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public Log setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Log setUsername(String username) {
        this.username = username;
        return this;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public Log setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getRequestTx() {
        return requestTx;
    }

    public Log setRequestTx(String requestTx) {
        this.requestTx = requestTx;
        return this;
    }

    public String getResponseTx() {
        return responseTx;
    }

    public Log setResponseTx(String responseTx) {
        this.responseTx = responseTx;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Log{" +
        ", id=" + id +
        ", brower=" + brower +
        ", createtime=" + createtime +
        ", ip=" + ip +
        ", module=" + module +
        ", operation=" + operation +
        ", remarks=" + remarks +
        ", status=" + status +
        ", updatetime=" + updatetime +
        ", username=" + username +
        ", deleted=" + deleted +
        ", requestTx=" + requestTx +
        ", responseTx=" + responseTx +
        "}";
    }
}
