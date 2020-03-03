package com.app.mdc.utils.viewbean.apply;

/**
 * @ Author     ：zl
 * @ Date       ：2019/7/11 10:26
 * @ Description
 */
public class CarApplyDto {
    private String carId;
    private String userId;
    private String usetimeStr;
    private String backtimeStr;
    private String companyIds;
    private String companyNames;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsetimeStr() {
        return usetimeStr;
    }

    public void setUsetimeStr(String usetimeStr) {
        this.usetimeStr = usetimeStr;
    }

    public String getBacktimeStr() {
        return backtimeStr;
    }

    public void setBacktimeStr(String backtimeStr) {
        this.backtimeStr = backtimeStr;
    }

    public String getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
    }

    public String getCompanyNames() {
        return companyNames;
    }

    public void setCompanyNames(String companyNames) {
        this.companyNames = companyNames;
    }

    public CarApplyDto(String carId, String userId, String usetimeStr, String backtimeStr, String companyIds, String companyNames) {
        this.carId = carId;
        this.userId = userId;
        this.usetimeStr = usetimeStr;
        this.backtimeStr = backtimeStr;
        this.companyIds = companyIds;
        this.companyNames = companyNames;
    }

    public CarApplyDto() {
    }

    @Override
    public String toString() {
        return "CarApplyDto{" +
                "carId='" + carId + '\'' +
                ", userId='" + userId + '\'' +
                ", usetimeStr='" + usetimeStr + '\'' +
                ", backtimeStr='" + backtimeStr + '\'' +
                ", companyIds='" + companyIds + '\'' +
                ", companyNames='" + companyNames + '\'' +
                '}';
    }
}
