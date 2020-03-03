package com.app.mdc.model.mdc;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("mdc_income")
@Data
public class InCome {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField("user_id")
    private Integer userId;

    @TableField("salary")
    private BigDecimal salary;

    @TableField("contract_salary")
    private BigDecimal contractSalary;

    @TableField("share_salary")
    private BigDecimal shareSalary;

    @TableField("type")
    private Integer type;

    @TableField("unit")
    private String unit;

    @TableField("number")
    private Integer number;

    @TableField("contract_id")
    private Integer contractId;

    @TableField("remark")
    private String remark;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("rate")
    private BigDecimal rate;

    @TableField("sel_date")
    private Date selDate;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("is_cal_msalary")
    private Integer isCalMsalary;

    @TableField("manage_salary")
    private BigDecimal manageSalary;

    @TableField("same_level_salary")
    private BigDecimal sameLevelSalary;

    public InCome() {
    }

    public InCome(Integer userId, String unit, Integer contractId, int type, String remark, BigDecimal amount, BigDecimal incomeRate,Date selDate, Date createTime) {
        this.userId = userId;
        this.unit = unit;
        this.contractId = contractId;
        this.type = type;
        this.remark = remark;
        this.amount = amount;
        this.rate = incomeRate;
        this.selDate = selDate;
        this.createTime = createTime;
    }

    @Data
    public static class IncomeNode{
        private Date selDate;
        private Integer type;
        private BigDecimal salary;
    }
}
