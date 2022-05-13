package com.hongnx.cloud.upms.common.feign;

import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.constant.ServiceNameConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.upms.common.entity.SysTenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author
 */
@FeignClient(contextId = "feignTenantService", value = ServiceNameConstants.UMPS_ADMIN_SERVICE)
public interface FeignTenantService {

	/**
	 * 查询租户列表
	 *
	 * @return R
	 */
	@GetMapping("/tenant/inside/list")
    R<List<SysTenant>> list(@RequestHeader(SecurityConstants.FROM) String from);

}
