package com.app.mdc.mapper.mdc;

import com.app.mdc.model.mdc.Notice;
import com.baomidou.mybatisplus.mapper.BaseMapper;

public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 查询最新公告
     * @return
     */
    Notice selectNewest();
}
