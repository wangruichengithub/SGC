package com.app.mdc.model.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@TableName("sys_user")
@Data
public class User {

	@TableId(value = "user_id", type = IdType.AUTO)
	private String id;

	@TableField("dept_id")
	private String deptId;

	@TableField("login_name")
	private String loginName;

	@TableField("user_name")
	private String userName;

	@TableField("user_type")
	private String userType;

	@TableField("email")
	private String email;

	@TableField("phonenumber")
	private String phoneNumber;

	@TableField("sex")
	private String sex;

	@TableField("avatar")
	private String avatar;

	@TableField("password")
	private String password;

	@TableField("salt")
	private String salt;

	@TableField("status")
	private String status;

	@TableField("del_flag")
	private Integer delFlag;

	@TableField("login_ip")
	private String loginIp;

	@TableField("login_date")
	private String loginDate;

	@TableField("create_by")
	private String createBy;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_by")
	private String updateBy;

	@TableField("update_time")
	private Date updateTime;

	@TableField("remark")
	private String remark;

	//直属推荐人id
	@TableField("up_user_id")
	private String upUserId;

	//所有推荐人id
	@TableField("up_user_ids")
	private String upUserIds;

	@TableField("pay_password")
	private String payPassword;

	@TableField("level")
	private Integer level;

	@TableField("send_code")
	private Integer sendCode;

	@TableField("sign_contract_sum")
	private BigDecimal signContractSum;

	@TableField("advance_contract_sum")
	private BigDecimal advanceContractSum;

	@TableField("union_sign_total_money")
	private BigDecimal unionSignTotalMoney;

	@TableField("union_advance_total_money")
	private BigDecimal unionAdvanceTotalMoney;

	@TableField("gesture_switch")
	private Integer gestureSwitch;


//	@TableField("self_Sign_total_money")
//	private BigDecimal selfSignTotalMoney;
//
//	@TableField("self_advance_total_money")
//	private BigDecimal selfAdvanceTotalMoney;

	@TableField("member_size")
	private Integer memberSize;

	@TableField("register_type")
	private Integer registerType;

	public void fromMap(Map<String, Object> map) {
		if (map.get("id") != null) {
			this.id = (String) map.get("id");
		}
		if (map.get("loginName") != null) {
			this.loginName = (String) map.get("loginName");
		}

		if (map.get("userName") != null) {
			this.userName = (String) map.get("userName");
		}

		if (map.get("password") != null) {
			this.password = (String) map.get("password");
		}
//		if (map.get("name") != null) {
//			this.name = (String) map.get("name");
//		}
//		if (map.get("telephone") != null) {
//			this.telephone = (String) map.get("telephone");
//		}
//		if (map.get("position") != null) {
//			this.position = (String) map.get("position");
//		}
		if (map.get("remark") != null) {
			this.remark = (String) map.get("remark");
		}
//		if (map.get("companyid") != null) {
//			this.companyid = (String) map.get("companyid");
//		}
//		if (map.get("rank") != null) {
//			this.rank = Integer.parseInt((String) map.get("rank"));
//		}
//		if (map.get("code") != null) {
//			this.code = (String) map.get("code");
//		}
//		if (map.get("districtId") != null) {
//			this.districtId = (String) map.get("districtId");
//		}
//		if (map.get("cid") != null) {
//			this.cid = (String) map.get("cid");
//		}
	}

}
