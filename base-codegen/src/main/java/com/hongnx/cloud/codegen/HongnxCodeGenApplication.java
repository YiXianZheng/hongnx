package com.hongnx.cloud.codegen;

import com.hongnx.cloud.common.datasource.annotation.EnableDynamicDataSource;
import com.hongnx.cloud.common.security.annotation.EnableBaseResourceServer;
import com.hongnx.cloud.common.security.annotation.EnableBaseFeignClients;
import com.hongnx.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @date 2018/07/29
 * 代码生成模块
 */
@EnableDynamicDataSource
@BaseEnableSwagger
@SpringBootApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class HongnxCodeGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(HongnxCodeGenApplication.class, args);
	}
}
