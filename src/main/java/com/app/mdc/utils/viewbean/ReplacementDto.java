package com.app.mdc.utils.viewbean;

/**
 * @ Author     ：zl
 * @ Date       ：2019/6/6 15:17
 * @ Description    备件dto
 */
public class ReplacementDto {

    private String id;
    /**
     * 备件名称
     */
    private String name;
    /**
     * 备件编号
     */
    private String code;
    /**
     * 规格型号
     */
    private String model;
    /**
     * 类别
     */
    private String type;
    /**
     * 生产厂家
     */
    private String factory;
    /**
     * 当前库存
     */
    private Integer stock;
    /**
     * 生产日期
     */
    private String producetimeStr;
    /**
     * 保修时间
     */
    private String warrantyStr;
    /**
     * 设备说明书
     */
    private String instructionBook;
    /**
     * 设备图片
     */
    private String devicePicture;

    private String storageId;//仓库id

    private String unitId;

    private String unitName;

    private String categoryId;

    private String categoryName;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getProducetimeStr() {
        return producetimeStr;
    }

    public void setProducetimeStr(String producetimeStr) {
        this.producetimeStr = producetimeStr;
    }

    public String getWarrantyStr() {
        return warrantyStr;
    }

    public void setWarrantyStr(String warrantyStr) {
        this.warrantyStr = warrantyStr;
    }

    public String getInstructionBook() {
        return instructionBook;
    }

    public void setInstructionBook(String instructionBook) {
        this.instructionBook = instructionBook;
    }

    public String getDevicePicture() {
        return devicePicture;
    }

    public void setDevicePicture(String devicePicture) {
        this.devicePicture = devicePicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
