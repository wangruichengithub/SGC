package com.app.mdc.serviceImpl.vas;

import com.app.mdc.utils.httpclient.vas.CoinUtils;

/*
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MdcApplication.class)//这里的Application是springboot的启动类名*/
/*@WebAppConfiguration*/
public class Test{


    @org.junit.Test
      public  void create() throws Throwable {
        new CoinUtils().transfer("VW1RpNvpZiQHcavVPDZngnwHWCLkPM1pA7","VQjK2Z48dx4Kht4PWBHMmKeH7oMebHddRR",20.00);
        new CoinUtils().createaddress(1);
    }



}
