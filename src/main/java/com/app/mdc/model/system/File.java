package com.app.mdc.model.system;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("sys_files")
public class File {

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    private Date createtime;//创建时间
    private Date updatetime;//更新时间
    private String status;//状态
    private String filename;//文件名
    private String filepath;//文件路径
    private String filetype;//文件类型
    private String userid;//上传用户
    private String moudle;//模块
    private String moudleType; //业务类别
    private String moudleId;
    private int deleted;//删除逻辑键

    public int getDeleted() {
        return deleted;
    }

    public File setDeleted(int deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getMoudle() {
        return moudle;
    }

    public File setMoudle(String moudle) {
        this.moudle = moudle;
        return this;
    }

    public String getId() {
        return id;
    }

    public File setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public File setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public File setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public File setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public File setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getFilepath() {
        return filepath;
    }

    public File setFilepath(String filepath) {
        this.filepath = filepath;
        return this;
    }

    public String getFiletype() {
        return filetype;
    }

    public File setFiletype(String filetype) {
        this.filetype = filetype;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public File setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getMoudleType() {
        return moudleType;
    }

    public File setMoudleType(String moudleType) {
        this.moudleType = moudleType;
        return this;
    }

    public String getMoudleId() {
        return moudleId;
    }

    public File setMoudleId(String moudleId) {
        this.moudleId = moudleId;
        return this;
    }
}
