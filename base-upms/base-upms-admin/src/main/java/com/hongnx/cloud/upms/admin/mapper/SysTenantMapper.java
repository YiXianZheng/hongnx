package com.hongnx.cloud.upms.admin.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.upms.common.entity.SysTenant;
import java.io.Serializable;

/**
 * <p>
 * 租户管理 Mapper 接口
 * </p>
 *
 * @since 2019-01-20
 */
@InterceptorIgnore(tenantLine="true")
public interface SysTenantMapper extends BaseMapper<SysTenant> {

	/**
	 * 通过租户ID删除租户
	 * 包括base_upms库所有表的当前租户数据
	 */
	void deleteSysTenantById(Serializable id);
}
