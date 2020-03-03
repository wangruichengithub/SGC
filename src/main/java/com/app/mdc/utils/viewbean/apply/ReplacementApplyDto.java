package com.app.mdc.utils.viewbean.apply;

/**
 * @ Author     ：zl
 * @ Date       ：2019/7/11 10:26
 * @ Description
 */
public class ReplacementApplyDto {

    private String replacementId;

    private String usetimeStr;

    private Integer num;

    private String userId;

    private String equipmentId;

    public String getReplacementId() {
        return replacementId;
    }

    public void setReplacementId(String replacementId) {
        this.replacementId = replacementId;
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

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public ReplacementApplyDto(String replacementId, String usetimeStr, Integer num, String userId,String equipmentId) {
        this.replacementId = replacementId;
        this.usetimeStr = usetimeStr;
        this.num = num;
        this.userId = userId;
        this.equipmentId = equipmentId;
    }

    public ReplacementApplyDto() {
    }
}
