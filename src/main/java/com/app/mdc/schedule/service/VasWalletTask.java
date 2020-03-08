package com.app.mdc.schedule.service;

import com.app.mdc.mapper.mdc.VasWalletMapper;
import com.app.mdc.model.mdc.*;
import com.app.mdc.service.mdc.TransactionService;
import com.app.mdc.utils.httpclient.vas.CoinUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * 用于 vas 钱包监控
 */
@Configuration
@EnableScheduling
public class VasWalletTask {
    private static final Logger log = LoggerFactory.getLogger(VasWalletTask.class);
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private VasWalletMapper vasWalletMapper;
    @Autowired
    private CoinUtils coinUtils;
    private int initHeight = 0;
    private Block bloks;
    @Scheduled(cron = "0 0/30 * * * ?")
    public void coinOut() throws Throwable {
        //获取区块高度,如果还未获取则重新获取
        int height = coinUtils.getsyncinfo().getInteger("blocks");
        log.debug("开始运行数据监控");
        if (initHeight == 0) {
            initHeight = height-1;
        }

        for (int i = initHeight; i < height; i++) {
            try {
                bloks = coinUtils.getblock(i + "");
                List <Tx> txs = bloks.getTx();
                for (int s = 0; s < txs.size(); s++) {
                    List <Vout> vouts = txs.get(s).getVout();
                    for (int m = 0; m < vouts.size(); m++) {
                        Vout vout = vouts.get(m);
                        //判断这笔交易存在吗
                        Double value = Double.parseDouble(vout.getValue());
                        if (value > 0.00) {
                            List <String> addresss = vout.getScriptPubKey().getAddresses();
                            for (int a = 0; a < addresss.size(); a++) {
                                String addr = addresss.get(a);
                                //判断是不是系统地址
                                VasWallet vasWallet = vasWalletMapper.getWalletByAddress(addr);
                                if (vasWallet != null) {
                                    transactionService.investVas(vasWallet.getUserId() + "", addr, value + "");
                                    vasWallet.setBalance(vasWallet.getBalance() + value);
                                    vasWalletMapper.updateById(vasWallet);
                                    initHeight = height;
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                log.error("监控 vas 充值出错,区块高度{},错误原因:{}",height,e.toString());
                continue;
            }

        }
    }

}


