package com.app.mdc.serviceImpl.vas;
import com.app.mdc.service.vas.VasWalletService;
import com.app.mdc.utils.httpclient.vas.CoinUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class Test{
    @Autowired
    VasWalletService vasWalletService;

    @org.junit.Test
      public  void create() throws Throwable {
       new CoinUtils().createaddress(222);
    }
}
