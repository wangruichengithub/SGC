package com.app.mdc.controller.system;

import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.service.system.FileService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/admin/file")
public class FileController {

    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }
    /**
     * 分页查询文件记录
     * @param page 分页插件
     * @param map 查询条件
     * @return response
     */
    @PostMapping(value = "getFilesByPage")
    @ResponseBody
    public ResponseResult getFilesByPage(Page page,
                                         @RequestParam Map<String,Object> map){
        return fileService.getFilesByPage(page,map);
    }

    /**
     * 根据id删除文件
     * @param id 文件id
     * @return ResponseResult
     */
    @PostMapping(value = "deleteFileById")
    @SystemLogAnno(module = "文件管理", operation = "根据id删除文件记录")
    @ResponseBody
    public ResponseResult deleteFileById(@RequestParam String id){
        return fileService.deleteById(id);
    }

    /**
     * 批量删除文件
     * @param ids 文件ids
     * @return ResponseResult
     */
    @PostMapping(value = "deleteFileByIds")
    @SystemLogAnno(module = "文件管理", operation = "根据ids批量删除文件记录")
    @ResponseBody
    public ResponseResult deleteFileByIds(@RequestParam String ids){
        return fileService.batchDeleteByIds(ids);
    }

    /**
     * 查询文件是否存在
     * @param id 文件id
     * @return ResponseResult
     */
    @PostMapping(value = "checkFileIsExist")
    @ResponseBody
    public ResponseResult checkFileIsExist(@RequestParam String id){
        return fileService.checkFileIsExist(id);
    }
}
