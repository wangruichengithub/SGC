package com.app.mdc.model.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

/**
 * 验证码
 */
@Data
@TableName("sys_verification_code")
public class VerificationCode {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableId(value = "code")
    private String code;

    @TableId(value = "create_time")
    private Date createTime;

    @TableId(value = "create_by")
    private String createBy;


    public VerificationCode() {
    }

    public VerificationCode(String code, String createBy, Date createTime) {
        this.code = code;
        this.createBy = createBy;
        this.createTime = createTime;
    }
}
