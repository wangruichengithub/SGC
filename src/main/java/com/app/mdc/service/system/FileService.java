package com.app.mdc.service.system;

import com.app.mdc.model.system.File;
import com.app.mdc.model.system.User;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {

    /**
     * 文件上传方法
     * @param files MultipartFile文件
     * @param moudle 所属模块
     * @param moudleType 所属业务类型（可不填）
     * @param moudleId 所属业务id
     * @param User 上传用户
     * @return Filemodel
     */
    List<File> uploadFile(MultipartFile[] files, String moudle,String moudleType,String moudleId, User User) throws IOException;

    /**
     * 根据文件id获取文件model
     * @param id 文件id
     * @return 文件model
     */
    File findFileById(String id);

    /**
     * 根据ids获取文件列表
     * @param ids 文件id集合，根据逗号隔开
     * @return list
     */
    List<File> findFilesByIds(String ids);

    /**
     * 根据分页获取文件列表
     * @param map 文件id，文件名称
     * @return ResponseResult
     */
    ResponseResult getFilesByPage(Page page, Map<String,Object> map);

    /**
     * 根据id删除文件
     * @param id 文件id
     * @return ResponseResult
     */
    ResponseResult deleteById(String id);

    /**
     * 根据ids批量删除文件
     * @param ids 文件ids
     * @return ResponseResult
     */
    ResponseResult batchDeleteByIds(String ids);

    /**
     * 根据id判断文件是否存在
     * @param id 文件id
     * @return ResponseResult
     */
    ResponseResult checkFileIsExist(String id);
}
