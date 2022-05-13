package com.hongnx.cloud.upms.common.feign;

import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.constant.ServiceNameConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.upms.common.entity.SysConfigStorage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author
 */
@FeignClient(contextId = "feignConfigStorageService", value = ServiceNameConstants.UMPS_ADMIN_SERVICE)
public interface FeignConfigStorageService {
	/**
	 * 查询存储配置
	 *
	 * @param from     调用标志
	 * @return R
	 */
	@GetMapping("/configstorage/inside")
    R<SysConfigStorage> getSysConfigStorage(@RequestHeader(SecurityConstants.FROM) String from);

}
