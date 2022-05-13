package com.hongnx.cloud.common.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 短信配置
 *
 * @author
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfigProperties {

	private String regionId = "regionId";

	private String accessKeyId = "accessKeyId";

	private String accessKeySecret = "accessKeySecret";

}
