package com.app.mdc.mapper.mdc;

import com.app.mdc.model.mdc.Transaction;
import com.app.mdc.model.system.User;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Component
public interface TransactionMapper extends BaseMapper<Transaction> {

    List<Transaction> incomeHistory(Integer userId);

    List<Transaction> getTransaction(Map<String,Object> params);

}
