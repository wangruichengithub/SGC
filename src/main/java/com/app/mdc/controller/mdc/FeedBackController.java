package com.app.mdc.controller.mdc;



import com.app.mdc.service.mdc.FeedBackService;
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
@RequestMapping("/feedback")
@Api("反馈")
public class FeedBackController {

	@Autowired
	private FeedBackService feedBackService;

	@RequestMapping("/add")
	@ApiOperation("新增反馈")
	public ResponseResult add(@RequestParam String userId,@RequestParam String message){
		this.feedBackService.add(userId,message);
		return ResponseResult.success("感谢您的反馈，我们已收到。");
	}

}

