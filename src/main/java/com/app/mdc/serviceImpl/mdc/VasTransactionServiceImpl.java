package com.app.mdc.serviceImpl.mdc;

import com.alibaba.fastjson.JSONObject;
import com.app.mdc.enums.ApiErrEnum;
import com.app.mdc.exception.BusinessException;
import com.app.mdc.mapper.mdc.TransactionMapper;
import com.app.mdc.mapper.mdc.VasWalletMapper;
import com.app.mdc.mapper.mdc.WalletMapper;
import com.app.mdc.mapper.system.UserMapper;
import com.app.mdc.model.mdc.Transaction;
import com.app.mdc.model.mdc.VasWallet;
import com.app.mdc.model.mdc.Wallet;
import com.app.mdc.model.system.Config;
import com.app.mdc.model.system.User;
import com.app.mdc.service.mdc.VasTransactionService;
import com.app.mdc.service.system.ConfigService;
import com.app.mdc.service.system.VerificationCodeService;
import com.app.mdc.utils.Md5Utils;
import com.app.mdc.utils.httpclient.vas.CoinUtils;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Service("vasTransactionService")
public class VasTransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements VasTransactionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VasWalletMapper vasWalletMapper;
    @Autowired
    private CoinUtils coinUtils;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private  ConfigService configService;
    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ResponseResult tranfer(String userId, String payPassword, String toAddress, String transferNumber, String verCode, String verId) throws Throwable {
        Double amount  =Double.parseDouble(transferNumber);
        User user = userMapper.selectById(userId);
        if (StringUtils.isNotEmpty(user.getPayPassword()) && !Md5Utils.hash(user.getLoginName(), payPassword).equals(user.getPayPassword())) {
            throw new BusinessException(ApiErrEnum.ERR202);
        }
      /*  //验证校验码
      boolean flag = verificationCodeService.validateVerCode(verCode,verId);
        if(!flag){
            return ResponseResult.fail(ApiErrEnum.ERR203);
        }*/
        //判断转出地址
        if(toAddress.length() != 34){
            throw new BusinessException(ApiErrEnum.ERR208);
        }
        VasWallet vasWallet = vasWalletMapper.getWalletByUserId(userId);
        if (vasWallet!=null){
            Double banlance = vasWallet.getBalance();
            if(Double.parseDouble(transferNumber) > banlance){
                return ResponseResult.fail(ApiErrEnum.ERR205);
            }
            Double fee = Double.parseDouble(configService.getByKey("VAS_CASH_OUT_FEE").getConfigValue());
            if(amount < fee){
                return ResponseResult.fail("ERR209","转账金额必须大于手续费");
            }
            String fromAddress = vasWallet.getAddress();
            coinUtils.transfer(fromAddress,toAddress,amount-fee);
            //更新余额
            vasWallet.setBalance(vasWallet.getBalance()-amount);
            vasWalletMapper.updateById(vasWallet);
            Transaction transaction = new Transaction();
            transaction.setFeeAmount(BigDecimal.valueOf(fee));
            transaction.setCreateTime(new Date());
            transaction.setFromAmount(BigDecimal.valueOf(amount));
            transaction.setFromUserId(Integer.parseInt(userId));
            transaction.setFromWalletAddress(toAddress);
            //0-usdt
            transaction.setFromWalletType("2");
            transaction.setToAmount(BigDecimal.valueOf(amount).subtract(transaction.getFeeAmount()));
            transaction.setToWalletAddress(toAddress);
            //0-usdt
            transaction.setToWalletType("2");
            //0-交易进行中
            transaction.setTransactionStatus("1");
            //1-提现
            transaction.setTransactionType("1");
            transactionMapper.insert(transaction);
        }else {
            return ResponseResult.fail(ApiErrEnum.ERR204);
        }

        return ResponseResult.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult convertUsdt(String userId, String convertMoney,String payPassword) {
        User u = userMapper.selectById(userId);

        //验证支付密码
        if (StringUtils.isNotEmpty(u.getPayPassword()) && !Md5Utils.hash(u.getLoginName(), payPassword).equals(u.getPayPassword())) {
            return ResponseResult.fail(ApiErrEnum.ERR202);
        }
        Config fee = configService.getByKey("VAS_CONVERT_FEE");
        EntityWrapper <Wallet> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id",userId);
        List <Wallet> wallets = walletMapper.selectList(entityWrapper);
        VasWallet vasWallet = vasWalletMapper.getWalletByUserId(userId);
        if(wallets.size() > 0){
            Wallet wallet = wallets.get(0);
            //获得兑换价格
            JSONObject jsonObject = restTemplate.getForObject("https://api.boboo.com/openapi/quote/v1/ticker/price?symbol=VASUSDT", JSONObject.class);
            //转换类型
            BigDecimal convert = new BigDecimal(convertMoney);
            //算出所需能兑换的 USTD数量
            BigDecimal convertUSTD = convert.multiply(new BigDecimal(jsonObject.getString("price")));
            BigDecimal convertFee = convert.multiply(new BigDecimal(fee.getConfigValue()));
            Double vasbalance = vasWallet.getBalance();
            //算出兑总 vas
            Double actualConvert = Double.valueOf(convert.add(convertFee).toString());
            //如果余额小于 vas 数量
            if(vasbalance< actualConvert.doubleValue()){
                return ResponseResult.fail(ApiErrEnum.ERR207);
            }else{
                BigDecimal usdtBalance = wallet.getUstdBlance();
                //设置 usdt 余额
                wallet.setUstdBlance(usdtBalance.add(convertUSTD));
                //设置 vas 余额
                vasWallet.setBalance(vasWallet.getBalance()-actualConvert);
                walletMapper.updateById(wallet);
                vasWalletMapper.updateById(vasWallet);
                Transaction transaction = new Transaction();
                transaction.setFeeAmount(convertFee);
                transaction.setCreateTime(new Date());
                transaction.setFromAmount(convert);
                transaction.setFromUserId(Integer.parseInt(userId));
                transaction.setFromWalletAddress(vasWallet.getAddress());
                //2-vas
                transaction.setFromWalletType("2");
                transaction.setToAmount(convertUSTD);
                transaction.setToWalletAddress(wallet.getAddress());
                transaction.setToUserId(Integer.parseInt(userId));
                //0-usdt
                transaction.setToWalletType("0");
                //0-交易进行中
                transaction.setTransactionStatus("1");
                //7-兑换
                transaction.setTransactionType("7");
                transactionMapper.insert(transaction);

            }
        }else{
            return ResponseResult.fail();
        }

        return ResponseResult.success();
    }

}
