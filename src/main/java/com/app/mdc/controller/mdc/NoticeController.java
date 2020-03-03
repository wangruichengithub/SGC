package com.app.mdc.controller.mdc;



import com.app.mdc.service.mdc.FeedBackService;
import com.app.mdc.service.mdc.NoticeService;
import com.app.mdc.utils.viewbean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2019-06-12
 */
@RestController
@RequestMapping("/notice")
@Api("公告")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@RequestMapping("/list")
	@ApiOperation("查询公告")
	public ResponseResult list(){
		return ResponseResult.success().setData(this.noticeService.list());
	}

}

