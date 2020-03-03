package com.app.mdc.model.mdc;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@TableName("mdc_user_contract")
@Data
public class UserContract {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("contract_id")
    private Integer contractId;

    @TableField("number")
    private Integer number;

    @TableLogic
    private Integer delFlag;

    @TableField("upgrade_history")
    private String upgradeHistory;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;

    public UserContract() {
    }

    public UserContract(Integer userId, Integer contractId, Integer number, Date date, String userName) {
        this.userId = userId;
        this.contractId = contractId;
        this.number = number;
        this.createTime = date;
        this.createBy = userName;
        this.updateTime = date;
        this.updateBy = userName;
    }
}
