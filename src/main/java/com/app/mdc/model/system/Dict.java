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
 * @since 2019-06-12
 */
@TableName("sys_dict")
public class Dict extends Model<Dict> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    private String status;
    private Integer deleted;
    private Date createtime;
    private Date updatetime;
    /**
     * 字典名称
     */
    private String name;
    /**
     * 父级字典id
     */
    private String pid;
    /**
     * 字典类型
     */
    private String type;
    /**
     * 字典编号
     */
    private String number;
    /**
     * 字典描述
     */
    private String desc;
    /**
     * 排序
     */
    private Integer rank;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Dict{" +
        ", id=" + id +
        ", status=" + status +
        ", deleted=" + deleted +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", name=" + name +
        ", pid=" + pid +
        ", type=" + type +
        ", number=" + number +
        ", desc=" + desc +
        ", rank=" + rank +
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
        if(dict.get("pid")!=null){
            this.pid = (String) dict.get("pid");
        } 
        if(dict.get("type")!=null){
            this.type = (String) dict.get("type");
        } 
        if(dict.get("number")!=null){
            this.number = (String) dict.get("number") ;
        } 
        if(dict.get("desc")!=null){
            this.desc = (String) dict.get("desc");
        } 
        if(dict.get("rank")!=null){
            this.rank = Integer.parseInt((String) dict.get("rank")) ;
        } 
    }

    public void fromMapWithoutNumber(Map<String,Object> dict){
        if(dict.get("id")!=null){
            this.id = (String) dict.get("id");
        }
        if(dict.get("status")!=null){
            this.status = (String) dict.get("status");
        }
        if(dict.get("name")!=null){
            this.name = (String) dict.get("name");
        }
        if(dict.get("pid")!=null){
            this.pid = (String) dict.get("pid");
        }
        if(dict.get("type")!=null){
            this.type = (String) dict.get("type");
        }
        if(dict.get("desc")!=null){
            this.desc = (String) dict.get("desc");
        }
        if(dict.get("rank")!=null){
            this.rank = Integer.parseInt((String) dict.get("rank")) ;
        }
    }
}
