package com.app.mdc.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.Log;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-08-01
 */
public interface LogService extends IService<Log> {

    /**
     * 保存更新
     * @param log log实体类
     */
    void saveOrUpdateLog(Log log);

    /**
     * 分页获取log日志
     * @param page 分页插件
     * @param map 查询参数
     * @return ResponseResult
     */
    ResponseResult findLogsPage(Page page, Map<String, Object> map);


	/**
	 * 获取单个日志详细信息
	 * @param id	日志id
	 * @return	日志实体类
	 */
    Log getOneLog(String id);
}
