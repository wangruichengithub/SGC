package com.app.mdc.model.mdc;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;


@TableName("sys_notice")
@Data
public class Notice {

    /** 公告ID */
    @TableField("notice_id")
    private Long noticeId;

    /** 公告标题 */
    @TableField("notice_title")
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    @TableField("notice_type")
    private String noticeType;

    /** 公告内容 */
    @TableField("notice_content")
    private String noticeContent;

    /** 公告状态（0正常 1关闭） */
    @TableField("status")
    private String status;

    /** 创建时间 */
    @TableField("create_time")
    private String createTime;

}
