package com.app.mdc.model.mdc;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@TableName("mdc_feedback")
@Data
public class FeedBack {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("message")
    private String message;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;
}
