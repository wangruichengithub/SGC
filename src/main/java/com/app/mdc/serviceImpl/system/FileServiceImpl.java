package com.app.mdc.serviceImpl.system;

import com.app.mdc.config.PicConfig;
import com.app.mdc.enums.ApiErrEnum;
import com.app.mdc.mapper.system.DictMapper;
import com.app.mdc.mapper.system.FileMapper;
import com.app.mdc.model.system.File;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.FileService;
import com.app.mdc.utils.file.FileUtil;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    private final FileMapper fileMapper;
    private final PicConfig picConfig;
    private final DictMapper dictMapper;

    @Autowired
    public FileServiceImpl(FileMapper fileMapper, PicConfig picConfig,DictMapper dictMapper) {
        this.fileMapper = fileMapper;
        this.picConfig = picConfig;
        this.dictMapper = dictMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<File> uploadFile(MultipartFile[] files, String moudle,String moudleType,String moudleId, User tbUser) throws IOException {
        List<File> tbfiles = new ArrayList<>();
        for (MultipartFile file: files){
            String fileName = FileUtil.save(file, picConfig.getSavePath() + moudle + "/");
            File tbFile = new File();
            tbFile.setCreatetime(new Date()).setUpdatetime(new Date())
                    .setStatus("A").setDeleted(0)
                    .setFilename(file.getOriginalFilename())
                    .setFilepath(moudle + "/" + fileName)
                    .setFiletype(file.getContentType())
                    .setMoudle(moudle)
                    .setUserid(tbUser.getId())
                    .setMoudleType(moudleType)
                    .setMoudleId(moudleId);
            fileMapper.insert(tbFile);
            tbfiles.add(tbFile);

        }
        return tbfiles;
    }

    @Override
    public File findFileById(String id) {
        return fileMapper.selectById(id);
    }

    @Override
    public List<File> findFilesByIds(String ids) {
        return fileMapper.selectBatchIds(Arrays.asList(ids.split(",")));
    }

    @Override
    public ResponseResult getFilesByPage(Page page, Map<String, Object> map) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        map.remove("pageNum");
        map.remove("pageSize");
        map.put("deleted",0);
        List<File> files = fileMapper.selectByMap(map);
        return ResponseResult.success().add(new PageInfo<>(files));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult deleteById(String id) {
        return batchDeleteByIds(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult batchDeleteByIds(String ids) {
        Date date = new Date();
        File file = new File();
        file.setUpdatetime(date);
        file.setDeleted(1);
        String[] fileIds = ids.split(",");
        EntityWrapper<File> entityWrapper=new EntityWrapper<>();
        entityWrapper.in("id",fileIds);
        int flag = fileMapper.update(file,entityWrapper);
        return flag == fileIds.length ? ResponseResult.success("删除成功") : ResponseResult.fail();
    }

    @Override
    public ResponseResult checkFileIsExist(String id) {
        File file = fileMapper.selectById(id);
        int deleted = file.getDeleted();
        if(deleted == 1){
            return ResponseResult.fail(ApiErrEnum.ERR301);
        }else{
            String path = file.getFilepath();
            String realPath = picConfig.getSavePath()+path;
            boolean flag = FileUtil.isExist(realPath);
            if(!flag){
                return ResponseResult.fail(ApiErrEnum.ERR302);
            }
        }
        return ResponseResult.success("该文件存在");
    }

}
