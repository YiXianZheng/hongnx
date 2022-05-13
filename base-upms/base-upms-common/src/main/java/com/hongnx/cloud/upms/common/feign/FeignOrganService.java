package com.hongnx.cloud.upms.common.feign;

import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author
 */
@FeignClient(contextId = "feignOrganService", value = ServiceNameConstants.UMPS_ADMIN_SERVICE)
public interface FeignOrganService {
	/**
	 * 通过organId查询本级及其下级
	 *
	 * @param organId
	 * @param from     调用标志
	 * @return R
	 */
	@GetMapping("/organ/inside/organids/{organId}")
	List<String> getDorganIds(@PathVariable("organId") String organId
			, @RequestHeader(SecurityConstants.FROM) String from);

}
