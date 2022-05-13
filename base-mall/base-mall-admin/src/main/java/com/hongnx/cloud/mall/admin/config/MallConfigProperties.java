package com.hongnx.cloud.mall.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 商城相关配置
 *
 * @author
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "base.mall")
public class MallConfigProperties {

	private String notifyHost = "notify-host";

	private String logisticsKey = "logistics-key";
}
