package com.hongnx.cloud.common.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 短信模块配置
 *
 * @author
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "sms.templates")
public class SmsTemplateProperties {

	/**
	 * #登录模块
	 */
	private String signName1 = "signName1";
	private String templateCode1 = "templateCode1";
	/**
	 * #绑定模块
	 */
	private String signName2 = "signName2";
	private String templateCode2 = "templateCode2";
	/**
	 * #解绑模块
	 */
	private String signName3 = "signName3";
	private String templateCode3 = "templateCode3";

}
