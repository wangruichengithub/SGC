package com.app.mdc.model.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author
 * @since 2019-06-17
 */
@TableName("sys_district")
public class District extends Model<District> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    private String status;
    private Integer deleted;
    private Date createtime;
    private Date updatetime;
    /**
     * 行政区编码
     */
    private String code;
    /**
     * 行政区名字
     */
    private String name;
    /**
     * 行政区划等级
     */
    private String level;
    /**
     * 父级行政区划id
     */
    private String parentId;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 区号
     */
    private String areaCode;
    /**
     * 是否有下级
     */
    private boolean isChildren;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    

    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public boolean isChildren() {
        return isChildren;
    }

    public void setChildren(boolean children) {
        isChildren = children;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "District{" +
        ", id=" + id +
        ", status=" + status +
        ", deleted=" + deleted +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", code=" + code +
        ", name=" + name +
        ", level=" + level +
        ", parentId=" + parentId +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", shortName=" + shortName +
        ", areaCode=" + areaCode +
        ", isChildren=" + isChildren +
        "}";
    }
    
    public void fromMap(Map<String,Object> dict){
    	 if(dict.get("id")!=null){
             this.id = (String) dict.get("id");
         } 
         if(dict.get("status")!=null){
             this.status = (String) dict.get("status");
         } 
         if(dict.get("name")!=null){
             this.name = (String) dict.get("name");
         } 
         if(dict.get("code")!=null){
             this.code = (String) dict.get("code");
         } 
         if(dict.get("name")!=null){
             this.name = (String) dict.get("name");
         } 
         if(dict.get("level")!=null){
             this.level = (String) dict.get("level") ;
         } 
         if(dict.get("parentId")!=null){
             this.parentId = (String) dict.get("parentId") ;
         }
         if(dict.get("longitude")!=null){
            this.longitude = (String) dict.get("longitude") ;
         }
         if(dict.get("latitude")!=null){
            this.latitude = (String) dict.get("latitude") ;
         }
         if(dict.get("shortName")!=null){
            this.shortName = (String) dict.get("shortName") ;
         }
         if(dict.get("areaCode")!=null){
            this.areaCode = (String) dict.get("areaCode") ;
         }
         if(dict.get("children")!=null){
            this.isChildren =  Boolean.valueOf((String) dict.get("children"));
         }
    }

    @TableField(exist = false)
    private List<District> districts;

    public List<District> getDistricts() {
        return districts;
    }

    public District setDistricts(List<District> districts) {
        this.districts = districts;
        return this;
    }

}
