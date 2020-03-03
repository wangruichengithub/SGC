package com.app.mdc.controller.mdc;



import com.app.mdc.service.mdc.ContractService;
import com.app.mdc.service.mdc.NoticeService;
import com.app.mdc.utils.viewbean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/contract")
@Api("合约控制器")
public class ContractController {

	@Autowired
	private ContractService contractService;

	@RequestMapping("/list")
	@ApiOperation("合约列表")
	public ResponseResult list(){
		return ResponseResult.success().setData(this.contractService.list());
	}
}

