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
 * @since 2019-06-19
 */
@TableName("sys_question")
public class Question extends Model<Question> {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 修改时间
     */
    private Date updatetime;
    /**
     * 状态
     */
    private String status;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     * 问题标题
     */
    private String questionTitle;
    /**
     * 解决方案
     */
    private String questionSolution;
    /**
     * 问题设备类型
     */
    private String questionType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionSolution() {
        return questionSolution;
    }

    public void setQuestionSolution(String questionSolution) {
        this.questionSolution = questionSolution;
    }

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Question{" +
        ", id=" + id +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", status=" + status +
        ", deleted=" + deleted +
        ", questionTitle=" + questionTitle +
        ", questionSolution=" + questionSolution +
        ", questionType=" + questionType +
        "}";
    }
    
    public void fromMap(Map<String,Object> dict){
		if(dict.get("id")!=null){
			this.id = (String) dict.get("id");
		}
		if(dict.get("status")!=null){
			this.status = (String) dict.get("status");
		}
		if(dict.get("questionTitle")!=null){
			this.questionTitle = (String) dict.get("questionTitle");
		}
		if(dict.get("questionSolution")!=null){
			this.questionSolution = (String) dict.get("questionSolution");
		}
		if(dict.get("questionType")!=null){
			this.questionType = (String) dict.get("questionType");
		}
   } 
}
