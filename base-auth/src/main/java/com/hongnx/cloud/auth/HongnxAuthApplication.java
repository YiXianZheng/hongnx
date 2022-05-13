package com.hongnx.cloud.auth;

import com.hongnx.cloud.common.security.annotation.EnableBaseFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author
 * 认证授权中心
 */
@SpringBootApplication
@EnableBaseFeignClients
public class HongnxAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(HongnxAuthApplication.class, args);
	}
}
