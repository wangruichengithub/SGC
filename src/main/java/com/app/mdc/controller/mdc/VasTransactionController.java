package com.app.mdc.controller.mdc;


import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.mapper.mdc.VasWalletMapper;
import com.app.mdc.service.mdc.TransactionService;
import com.app.mdc.service.mdc.VasTransactionService;
import com.app.mdc.utils.httpclient.HttpUtil;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2019-06-12
 */
@Api(value = "VasTransactionController", description = "交易接口-充值，提现，转账，闪兑及其记录")
@Controller
@RequestMapping("/vas/transaction")
public class VasTransactionController {

	private final TransactionService transactionService;

	@Autowired
	private VasTransactionService vasTransactionService;

	@Autowired
	private VasWalletMapper vasWalletMapper;


	@Autowired
	public VasTransactionController(TransactionService transactionService) {
		this.transactionService=transactionService;
	}
	
	/**
	 * 获取交易记录
	 * @param map
	 * @return 返回的结果，0正确ERR500错误
	 */
	@ApiOperation(value="交易记录", notes="获取充值，提现，转账，闪兑记录")
	@PostMapping("/getTransaction")
	@ResponseBody
	public ResponseResult getTransaction(@RequestParam Map<String, Object> map, Page page) {
		return transactionService.getETHBlance(page,map);
	}

	/**
	 * 提现
	 */
	@PostMapping("/cashOut")
	@SystemLogAnno(module = "交易管理", operation = "交易提现")
	@ResponseBody
	public ResponseResult cashOut(@RequestParam String userId,@RequestParam String payPassword,@RequestParam String toAddress,@RequestParam String cashOutMoney,@RequestParam String verCode,@RequestParam String verId) {
		try {
			return vasTransactionService.tranfer(userId, payPassword, toAddress, cashOutMoney,verCode,verId);
		}catch (Throwable throwable){
			return ResponseResult.fail("-999",throwable.getMessage());
		}

	}

	@PostMapping("/convertUsdt")
	@SystemLogAnno(module = "交易管理", operation = "vas闪兑usdt")
	@ResponseBody
	public ResponseResult convertUsdt(@RequestParam String userId,@RequestParam String convertMoney,@RequestParam String payPassword) {
		try {
			return vasTransactionService.convertUsdt(userId, convertMoney, payPassword);
		}catch (Exception e){
			return ResponseResult.fail("-999",e.getMessage());
		}

	}

	@PostMapping("/getWallet")
	@SystemLogAnno(module = "交易管理", operation = "获取充值地址")
	@ResponseBody
	public ResponseResult getWallet(@RequestParam String userId) {
		try {
			return ResponseResult.success().setData(vasWalletMapper.getWalletByUserId(userId));
		}catch (Exception e){
			return ResponseResult.fail("-999",e.getMessage());
		}

	}

	@PostMapping("/getPrice")
	@SystemLogAnno(module = "获取 vas 价格", operation = "获取 vas 价格")
	@ResponseBody
	public ResponseResult getPrice(){
		String json = HttpUtil.doGet("https://api.boboo.com/openapi/quote/v1/ticker/price?symbol=VASUSDT",null).toString();
		return  ResponseResult.success().setData(json);
	}

}

