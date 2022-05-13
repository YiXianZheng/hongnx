package com.hongnx.cloud.common.data.datascope;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.exception.CheckedException;
import com.hongnx.cloud.common.data.enums.DataScopeTypeEnum;
import com.hongnx.cloud.common.security.entity.BaseUser;
import com.hongnx.cloud.common.security.util.SecurityUtils;
import com.hongnx.cloud.upms.common.entity.SysRole;
import com.hongnx.cloud.upms.common.feign.FeignOrganService;
import com.hongnx.cloud.upms.common.feign.FeignRoleService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.GrantedAuthority;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author
 * <p>
 * mybatis 数据权限拦截器
 */
@Slf4j
@AllArgsConstructor
public class DataScopeInterceptor implements InnerInterceptor {
	private final DataScopeProperties dataScopeProperties;
	private final FeignRoleService feignRoleService;
	private final FeignOrganService feignOrganService;

	@Override
	@SneakyThrows
	public void beforeQuery(Executor executor, MappedStatement mappedStatement, Object parameter, RowBounds rowBounds,
							ResultHandler resultHandler, BoundSql boundSql) {
		PluginUtils.MPBoundSql mPBoundSql = PluginUtils.mpBoundSql(boundSql);
		//查询数据权限配置
		List<String> mapperIds = dataScopeProperties.getMapperIds();
		//未配置数据权限，直接放行
		if (mapperIds==null || mapperIds.size()<=0) {
			return;
		}else{
			String mappedStatementId = mappedStatement.getId();
			if(!CollUtil.contains(mapperIds,mappedStatementId)){
				return;
			}
		}
		String originalSql = boundSql.getSql();

		String scopeName = CommonConstants.SCOPENAME;
		List<String> organIds = new ArrayList<>();
		// 优先获取赋值数据
		BaseUser user = SecurityUtils.getUser();
		if (user == null) {
			throw new CheckedException("auto datascope, set up security details true");
		}

		List<String> roleIdList = user.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith(SecurityConstants.ROLE))
				.map(authority -> authority.split("_")[1])
				.collect(Collectors.toList());
		SysRole sysRole = feignRoleService.getDsTypeMinRole(roleIdList, SecurityConstants.FROM_IN).getData();
		Integer dsType = sysRole.getDsType();
		// 查询全部
		if (DataScopeTypeEnum.ALL.getType() == dsType) {
			return;
		}
		// 自定义
		if (DataScopeTypeEnum.CUSTOM.getType() == dsType) {
			String dsScope = sysRole.getDsScope();
			organIds.addAll(Arrays.stream(dsScope.split(","))
					.map(String::toString).collect(Collectors.toList()));
		}
		// 查询本级及其下级
		if (DataScopeTypeEnum.OWN_CHILD_LEVEL.getType() == dsType) {
			List<String> organIdList = feignOrganService.getDorganIds(user.getOrganId(), SecurityConstants.FROM_IN);
			organIds.addAll(organIdList);
		}
		// 只查询本级
		if (DataScopeTypeEnum.OWN_LEVEL.getType() == dsType) {
			organIds.add(user.getOrganId());
		}
		String join = CollectionUtil.join(organIds, "','");
		originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope." + scopeName + " in ('" + join + "')";
		mPBoundSql.sql(originalSql);
		return;
	}

}
