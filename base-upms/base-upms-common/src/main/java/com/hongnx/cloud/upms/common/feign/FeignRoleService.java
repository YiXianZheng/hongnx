package com.hongnx.cloud.upms.common.feign;

import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.constant.ServiceNameConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.upms.common.entity.SysRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author
 */
@FeignClient(contextId = "feignRoleService", value = ServiceNameConstants.UMPS_ADMIN_SERVICE)
public interface FeignRoleService {
	/**
	 * 通过角色ID查询权限类型最小的角色
	 *
	 * @param roleIdList
	 * @param from     调用标志
	 * @return R
	 */
	@PostMapping("/role/dstype/min")
    R<SysRole> getDsTypeMinRole(@RequestBody List<String> roleIdList
			, @RequestHeader(SecurityConstants.FROM) String from);

}
