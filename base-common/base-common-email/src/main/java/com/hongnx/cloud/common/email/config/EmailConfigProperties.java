package com.hongnx.cloud.common.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 邮箱配置
 *
 * @author
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfigProperties {

	private String mailSmtpHost = "mailSmtpHost";

	private String mailSmtpUsername = "mailSmtpUsername";

	private String mailSmtpPassword = "mailSmtpPassword";

	private String siteName = "siteName";

}
