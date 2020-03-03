package com.app.mdc.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.app.mdc.model.system.File;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface FileMapper extends BaseMapper<File> {
    Map<String,Object> getFilesByMoudleId(@Param("moudleId") String moudleId);
    int deleteFileByMoudleId(@Param("moudleId")String moudleId);
}
