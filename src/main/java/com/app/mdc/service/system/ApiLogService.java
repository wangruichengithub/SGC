package com.app.mdc.service.system;

import com.app.mdc.model.system.ApiLog;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2019-08-05
 */
public interface ApiLogService extends IService<ApiLog> {

    /**
     * 保存接口请求信息
     * @param apiLog log实体类
     */
    void saveRequestMessage(ApiLog apiLog);

    /**
     * get请求
     * @param url 请求链接
     * @param param 请求参数
     * @return 返回请求结果
     */
    String getRequest(String url, Map<String,Object> param);

    /**
     * post请求
     * @param url 请求链接
     * @param param 请求参数
     * @return 返回请求结果
     */
    String postRequest(String url,Map<String,Object> param);

    /**
     * 定时请求
     * @param url 请求链接
     * @param param 请求参数
     * @param header 请求头
     * @param headerValue 请求头参数
     * @return 返回请求结果
     */
    String quartzRequest(String url,Map<String,Object> param,String header,String headerValue);

}
