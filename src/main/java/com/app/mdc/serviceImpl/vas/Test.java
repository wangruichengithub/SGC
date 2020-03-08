package com.app.mdc.serviceImpl.vas;

import com.app.mdc.MdcApplication;
import com.app.mdc.service.mdc.VasTransactionService;
import com.app.mdc.service.system.UserLevelService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MdcApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class Test{
    @Autowired
    VasTransactionService vasWalletService;
    @Autowired
    UserLevelService userLevelService;

    @org.junit.Test
      public  void create() throws Throwable {
        List <Map <String, Object>> list =  userLevelService.getGhztList(592);
    }
}
