package com.hongnx.cloud.common.data.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.hongnx.cloud.common.data.datascope.DataScopeInterceptor;
import com.hongnx.cloud.common.data.datascope.DataScopeProperties;
import com.hongnx.cloud.common.data.tenant.BaseTenantHandler;
import com.hongnx.cloud.upms.common.feign.FeignOrganService;
import com.hongnx.cloud.upms.common.feign.FeignRoleService;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * MybatisPlus配置
 * https://baomidou.com/guide/page.html
 *
 * @author
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@AllArgsConstructor
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@MapperScan("com.hongnx.cloud.**.mapper")
public class MybatisPlusConfig {

	private DataScopeProperties dataScopeProperties;
	private FeignRoleService feignRoleService;
	private FeignOrganService feignOrganService;

	/**
	 * 创建租户维护处理器对象
	 *
	 * @return 处理后的租户维护处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	public BaseTenantHandler baseTenantHandler() {
		return new BaseTenantHandler();
	}

	/**
	 * mybatis plus 拦截器各组件配置
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		// 多租户配置
		TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor();
		tenantLineInnerInterceptor.setTenantLineHandler(baseTenantHandler());
		mybatisPlusInterceptor.addInnerInterceptor(tenantLineInnerInterceptor);
		// 乐观锁配置
		mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		// 数据权限
		DataScopeInterceptor dataScopeInterceptor = new DataScopeInterceptor(dataScopeProperties, feignRoleService, feignOrganService);
		mybatisPlusInterceptor.addInnerInterceptor(dataScopeInterceptor);
		// 分页配置
		mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return mybatisPlusInterceptor;
	}

}
