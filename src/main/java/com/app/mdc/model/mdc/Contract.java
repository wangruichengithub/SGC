package com.app.mdc.model.mdc;

import com.app.mdc.service.system.ConfigService;
import com.app.mdc.utils.SpringContextHolder;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("sys_contract")
@Data
public class Contract {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("type")
    private Integer type;

    @TableField("level")
    private Integer level;

    @TableField("name")
    private String name;

    @TableField("price")
    private BigDecimal price;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("unit")
    private String unit;

    @TableField("introduce")
    private String introduce;

    @TableField("audio_path")
    private String audioPath;

    @TableField("income_rate")
    private BigDecimal incomeRate;

    @TableField("out_rate")
    private BigDecimal outRate;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_by")
    private String updateBy;

    public BigDecimal getAmount() {
        ConfigService configService = SpringContextHolder.applicationContext.getBean(ConfigService.class);
        if(this.type == 1 && this.level == 1){
            //屌丝
            return new BigDecimal(configService.getByKey("diaosi_amount").getConfigValue());
        }else if(this.type == 1 && this.level == 2){
            //网红
            return new BigDecimal(configService.getByKey("wanghong_amount").getConfigValue());
        }else if(this.type == 1 && this.level == 3){
            //明星
          return new BigDecimal(configService.getByKey("mingxing_amount").getConfigValue());
        }else if(this.type == 1 && this.level == 4){
            //大咖
            return  new BigDecimal(configService.getByKey("daka_amount").getConfigValue());
        }else if(this.type == 2){
            //进阶卡
            return new BigDecimal(configService.getByKey("advance_card_price").getConfigValue());
        }
        return new BigDecimal(0);
    }

    public BigDecimal getIncomeRate() {
        ConfigService configService = SpringContextHolder.applicationContext.getBean(ConfigService.class);
        if(this.type == 1 && this.level == 1){
            //屌丝
            return new BigDecimal(configService.getByKey("diaosi_rate").getConfigValue());
        }else if(this.type == 1 && this.level == 2){
            //网红
            return new BigDecimal(configService.getByKey("wanghong_rate").getConfigValue());
        }else if(this.type == 1 && this.level == 3){
            //明星
            return new BigDecimal(configService.getByKey("mingxing_rate").getConfigValue());
        }else if(this.type == 1 && this.level == 4){
            //大咖
            return  new BigDecimal(configService.getByKey("daka_rate").getConfigValue());
        }else if(this.type == 2){
            //进阶卡
            return new BigDecimal(configService.getByKey("advance_card_income_rate").getConfigValue());
        }
        return new BigDecimal(0);
    }
}
