package com.app.mdc.model.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2019-08-23
 */
@TableName("sys_app_version_log")
public class AppVersionLog extends Model<AppVersionLog> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.UUID)
	private String id;
	/**
	 * 版本号
	 */
	private String versionNumber;
	/**
	 * 版本信息
	 */
	private String versionInfo;
	/**
	 * 更新时间
	 */
	private Date updatetime;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 是否删除
	 */
	private Integer deleted;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AppVersionLog{" +
				", id=" + id +
				", versionNumber=" + versionNumber +
				", versionInfo=" + versionInfo +
				", updatetime=" + updatetime +
				", createtime=" + createtime +
				", status=" + status +
				", deleted=" + deleted +
				"}";
	}

	public void fromMap(Map<String,Object> map){
		if(map.get("id")!=null){
			this.id = (String) map.get("id");
		}
		if(map.get("versionNumber")!=null){
			this.versionNumber = (String) map.get("versionNumber");
		}
		if(map.get("versionInfo")!=null){
			this.versionInfo = (String) map.get("versionInfo");
		}
	}
}
