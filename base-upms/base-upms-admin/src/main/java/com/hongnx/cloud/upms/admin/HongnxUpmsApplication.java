package com.hongnx.cloud.upms.admin;

import com.hongnx.cloud.common.security.annotation.EnableBaseFeignClients;
import com.hongnx.cloud.common.security.annotation.EnableBaseResourceServer;
import com.hongnx.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author
 * 用户管理系统
 */
@BaseEnableSwagger
@SpringBootApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class HongnxUpmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(HongnxUpmsApplication.class, args);
	}

}
