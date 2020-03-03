package com.app.mdc.model.system;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author
 * @since 2019-06-11
 */
@TableName("sys_user_role")
public class RoleUser extends Model<RoleUser> {

    private static final long serialVersionUID = 1L;
//
//    @TableId(value = "id", type = IdType.UUID)
//    private String id;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 人员id
     */
    private String userId;


//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId + this.roleId;
    }

    @Override
    public String toString() {
        return "RoleUser{" +
//        ", id=" + id +
        ", roleId=" + roleId +
        ", userId=" + userId +
        "}";
    }
}
