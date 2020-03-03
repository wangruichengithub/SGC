package com.app.mdc.model.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("sys_user_tokens")
public class UserToken {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField(value = "user_id")
    private String userId;//用户id
    private String token;//用户token
    @TableField(value = "end_time")
    private Date endtime;//token失效时间

    public String getId() {
        return id;
    }

    public UserToken setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public UserToken setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserToken setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getEndtime() {
        return endtime;
    }

    public UserToken setEndtime(Date endtime) {
        this.endtime = endtime;
        return this;
    }
}
