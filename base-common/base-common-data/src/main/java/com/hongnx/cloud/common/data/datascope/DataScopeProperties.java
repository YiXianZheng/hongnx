package com.hongnx.cloud.common.data.datascope;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限配置
 *
 * @author
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "base.datascope")
public class DataScopeProperties {

	private List<String> mapperIds = new ArrayList<>();

}
