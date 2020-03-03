package com.app.mdc.serviceImpl.mdc;

import com.app.mdc.mapper.mdc.InComeMapper;
import com.app.mdc.model.mdc.InCome;
import com.app.mdc.model.mdc.Transaction;
import com.app.mdc.service.mdc.InComeService;
import com.app.mdc.service.mdc.TransactionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 收益servcieImpl
 */
@Service
public class InComeServiceImpl extends ServiceImpl<InComeMapper, InCome> implements InComeService {

    @Autowired
    private TransactionService transactionService;

    @Override
    public Map<Integer, Map<String, Object>> selectStaticIncomeGroupByLevel(Map<Integer, Map<String, Object>> levelIds, Date selDate, BigDecimal burnValue) {
        return this.baseMapper.selectStaticIncomeGroupByLevel(levelIds, selDate, burnValue.doubleValue());
    }

    @Override
    public Map<String, Object> getAdvanceShareSalary(Date selDate, Integer userId) {
        return this.baseMapper.getAdvanceShareSalary(selDate, userId);
    }

    @Override
    public BigDecimal getTotalSum(Integer userId, Date selDate, double burnValue) {
        return this.baseMapper.getTotalSum(userId, selDate, burnValue);
    }

    @Override
    public List<InCome.IncomeNode> list(Integer userId, Integer pageSize, Integer pageNumber) {
        List<InCome> list = this.baseMapper.list(userId);
        List<InCome.IncomeNode> result = new ArrayList<>();
        for (InCome inCome : list) {
            if (inCome.getContractSalary().compareTo(new BigDecimal(0)) > 0) {
                InCome.IncomeNode incomeNode1 = new InCome.IncomeNode();
                incomeNode1.setType(1); //静态收益
                incomeNode1.setSalary(inCome.getContractSalary());
                incomeNode1.setSelDate(inCome.getSelDate());
                result.add(incomeNode1);
            }

            if (inCome.getShareSalary().compareTo(new BigDecimal(0)) > 0) {
                InCome.IncomeNode incomeNode2 = new InCome.IncomeNode();
                incomeNode2.setType(2); // 分享收益
                incomeNode2.setSalary(inCome.getShareSalary());
                incomeNode2.setSelDate(inCome.getSelDate());
                result.add(incomeNode2);
            }

            if (inCome.getManageSalary().compareTo(new BigDecimal(0)) > 0) {
                InCome.IncomeNode incomeNode3 = new InCome.IncomeNode();
                incomeNode3.setType(3); //管理收益
                incomeNode3.setSalary(inCome.getManageSalary());
                incomeNode3.setSelDate(inCome.getSelDate());
                result.add(incomeNode3);
            }
            if (inCome.getSameLevelSalary().compareTo(new BigDecimal(0)) > 0) {
                InCome.IncomeNode incomeNode4 = new InCome.IncomeNode();
                incomeNode4.setType(4); //平级收益
                incomeNode4.setSalary(inCome.getSameLevelSalary());
                incomeNode4.setSelDate(inCome.getSelDate());
                result.add(incomeNode4);
            }
        }
        Integer fromIndex = (pageNumber - 1) * pageSize;
        Integer toIndex = Math.min(pageNumber * pageSize,result.size());
        if(fromIndex > result.size()){
            return new ArrayList<>();
        }
        return result.subList(fromIndex,toIndex);
    }

    @Override
    public List<Transaction> history(Integer userId) {
//        EntityWrapper<Transaction> transactionEntityWrapper = new EntityWrapper<>();
//        transactionEntityWrapper.eq("transaction_type", 4).and().eq("from_user_id",userId);
//        transactionEntityWrapper.eq("transaction_type", 6).and().eq("to_user_id",userId);
        return transactionService.incomeHistory(userId);
    }
}
