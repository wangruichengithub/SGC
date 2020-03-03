package com.app.mdc;

import com.app.mdc.controller.socket.MessageSocket;
import com.app.mdc.serviceImpl.mdc.reward.AllRewardJob;
import com.app.mdc.utils.jvm.JvmUtils;
import com.app.mdc.utils.jvm.LinceseUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.nio.charset.Charset;

@MapperScan({ "com.app.mdc.mapper" })
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.yml"})
@SpringBootApplication
@EnableScheduling
public class MdcApplication extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(MdcApplication.class);

	private static String linceseKey = "b266593f-21d6-4772-9ef9-795a5faffc78";

	public static void main(String[] args) throws Exception {

//		System.out.println(JvmUtils.getWindowsMac());
//		System.out.println(JvmUtils.getCPUIDWindows());

		/*if (LinceseUtils.getInstance(linceseKey).isCheckLincese()){
			ConfigurableApplicationContext applicationContext = SpringApplication.run(MdcApplication.class, args);
			String encoding = Charset.defaultCharset().name();
			logger.info("======AppApplication===启动 success==encoding=" + encoding);
			MessageSocket.setApplicationContext(applicationContext);

		}else{
			logger.error("系统授权失败！");
		}*/

        ConfigurableApplicationContext applicationContext = SpringApplication.run(MdcApplication.class, args);
        String encoding = Charset.defaultCharset().name();
        logger.info("======AppApplication===启动 success==encoding=" + encoding);
        MessageSocket.setApplicationContext(applicationContext);

//        applicationContext.getBean(AllRewardJob.class).execute();
	}


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(MdcApplication.class);
    }
}
