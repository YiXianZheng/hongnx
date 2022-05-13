package com.hongnx.cloud.weixin.admin.config.open;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方平台账号配置
 *
 * @author
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "wx.component")
public class WxComponentConfigProperties {

	private String appId = "appId";

	private String appSecret = "appSecret";

	private String token = "token";

	private String aesKey = "aesKey";

}
