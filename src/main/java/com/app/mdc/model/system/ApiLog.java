package com.app.mdc.model.system;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author
 * @since 2019-08-05
 */
@TableName("sys_api_log")
public class ApiLog extends Model<ApiLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    private Date createtime;
    /**
     * ip
     */
    private String ip;
    /**
     * 请求链接
     */
    private String requestUrl;
    /**
     * 请求类型
     */
    private String requestType;
    /**
     * 请求说明
     */
    private String remarks;
    /**
     * 状态
     */
    private Integer status;
    private Date updatetime;
    /**
     * 用户名
     */
    private String username;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     * request请求参数
     */
    private String requestTx;
    private String responseTx;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getRequestTx() {
        return requestTx;
    }

    public void setRequestTx(String requestTx) {
        this.requestTx = requestTx;
    }

    public String getResponseTx() {
        return responseTx;
    }

    public void setResponseTx(String responseTx) {
        this.responseTx = responseTx;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApiLog{" +
        ", id=" + id +
        ", createtime=" + createtime +
        ", ip=" + ip +
        ", requestUrl=" + requestUrl +
        ", requestType=" + requestType +
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
