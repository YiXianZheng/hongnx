package com.hongnx.cloud.upms.common.feign;

import com.hongnx.cloud.upms.common.entity.SysLog;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.constant.ServiceNameConstants;
import com.hongnx.cloud.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author
 */
@FeignClient(contextId = "feignLogService", value = ServiceNameConstants.UMPS_ADMIN_SERVICE)
public interface FeignLogService {
	/**
	 * 保存日志
	 *
	 * @param sysLog 日志实体
	 * @param from   是否内部调用
	 * @return succes、false
	 */
	@PostMapping("/log/save")
	R<Boolean> saveLog(@RequestBody SysLog sysLog, @RequestHeader(SecurityConstants.FROM) String from);
}
