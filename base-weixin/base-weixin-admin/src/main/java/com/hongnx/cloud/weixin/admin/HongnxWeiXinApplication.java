package com.hongnx.cloud.weixin.admin;

import com.hongnx.cloud.common.security.annotation.EnableBaseFeignClients;
import com.hongnx.cloud.common.security.annotation.EnableBaseResourceServer;
import com.hongnx.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @date 2019/02/29
 * 微信公众号模块
 */
@BaseEnableSwagger
@SpringBootApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class HongnxWeiXinApplication {

	public static void main(String[] args) {
		SpringApplication.run(HongnxWeiXinApplication.class, args);
	}

}
