package com.app.mdc.serviceImpl.mdc;

import com.app.mdc.mapper.mdc.ContractMapper;
import com.app.mdc.model.mdc.Contract;
import com.app.mdc.service.mdc.ContractService;
import com.app.mdc.service.system.ConfigService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {

    @Autowired
    private ConfigService configService;

    @Override
    public Map<Integer, Contract> selectAllContract() {
        return this.baseMapper.selectAllContract();
    }

    @Override
    public List<Contract> list() {
        List<Contract> contracts = this.baseMapper.selectList(new EntityWrapper<>());
        for(Contract c:contracts){
            if(c.getType() == 1 && c.getLevel() == 1){
                //屌丝
                c.setAmount(new BigDecimal(configService.getByKey("diaosi_amount").getConfigValue()));
                c.setIncomeRate(new BigDecimal(configService.getByKey("diaosi_rate").getConfigValue()));
            }else if(c.getType() == 1 && c.getLevel() == 2){
                //网红
                c.setAmount(new BigDecimal(configService.getByKey("wanghong_amount").getConfigValue()));
                c.setIncomeRate(new BigDecimal(configService.getByKey("wanghong_rate").getConfigValue()));
            }else if(c.getType() == 1 && c.getLevel() == 3){
                //明星
                c.setAmount(new BigDecimal(configService.getByKey("mingxing_amount").getConfigValue()));
                c.setIncomeRate(new BigDecimal(configService.getByKey("mingxing_rate").getConfigValue()));
            }else if(c.getType() == 1 && c.getLevel() == 4){
                //大咖
                c.setAmount(new BigDecimal(configService.getByKey("daka_amount").getConfigValue()));
                c.setIncomeRate(new BigDecimal(configService.getByKey("daka_rate").getConfigValue()));
            }else if(c.getType() == 2){
                //进阶卡
                c.setAmount(new BigDecimal(configService.getByKey("advance_card_price").getConfigValue()));
                c.setIncomeRate(new BigDecimal(configService.getByKey("advance_card_income_rate").getConfigValue()));
            }
        }
        return contracts;
    }

    @Override
    public List<Contract> getHigherContract(String contractId) {
        return this.baseMapper.getHigherContract(contractId);
    }
}
