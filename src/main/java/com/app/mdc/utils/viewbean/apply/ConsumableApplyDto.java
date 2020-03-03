package com.app.mdc.utils.viewbean.apply;

/**
 * @ Author     ：zl
 * @ Date       ：2019/7/11 10:34
 * @ Description
 */
public class ConsumableApplyDto {
    private String consumableId;

    private String usetimeStr;

    private Integer num;

    private String userId;

    public String getConsumableId() {
        return consumableId;
    }

    public void setConsumableId(String consumableId) {
        this.consumableId = consumableId;
    }

    public String getUsetimeStr() {
        return usetimeStr;
    }

    public void setUsetimeStr(String usetimeStr) {
        this.usetimeStr = usetimeStr;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ConsumableApplyDto(String consumableId, String usetimeStr, Integer num, String userId) {
        this.consumableId = consumableId;
        this.usetimeStr = usetimeStr;
        this.num = num;
        this.userId = userId;
    }

    public ConsumableApplyDto() {
    }
}
