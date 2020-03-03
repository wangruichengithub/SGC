package com.app.mdc.model.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user_level")
public class UserLevel {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //推荐人id
    @TableField("recId")
    private String recId;

    //被推荐人id
    @TableField("recedId")
    private String recedId;

    //层级
    @TableField("level")
    private Integer level;

    //创建人
    @TableField("createBy")
    private String createBy;

    //创建时间
    @TableField("createTime")
    private Date createTime;


    public UserLevel() {
    }

    public UserLevel(String recId, String recedId, Integer level, Date createTime) {
        this.recId = recId;
        this.recedId = recedId;
        this.createTime = createTime;
        this.level = level;
    }
}
