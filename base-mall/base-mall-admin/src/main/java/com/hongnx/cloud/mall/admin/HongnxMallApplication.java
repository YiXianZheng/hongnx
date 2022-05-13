package com.hongnx.cloud.mall.admin;

import com.hongnx.cloud.common.security.annotation.EnableBaseFeignClients;
import com.hongnx.cloud.common.security.annotation.EnableBaseResourceServer;
import com.hongnx.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @date 2019/07/29
 * 商城模块
 */
@BaseEnableSwagger
@SpringBootApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class HongnxMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(HongnxMallApplication.class, args);
	}

}
