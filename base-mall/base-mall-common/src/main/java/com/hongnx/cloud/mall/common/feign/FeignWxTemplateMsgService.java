package com.hongnx.cloud.mall.common.feign;

import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.constant.ServiceNameConstants;
import com.hongnx.cloud.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import com.hongnx.cloud.weixin.common.dto.WxTemplateMsgSendDTO;

/**
 */
@FeignClient(contextId = "feignWxTemplateMsgService", value = ServiceNameConstants.WX_ADMIN_SERVICE)
public interface FeignWxTemplateMsgService {

	@PostMapping("/wxtemplatemsg/send")
	R sendTemplateMsg(@RequestBody WxTemplateMsgSendDTO wxTemplateMsgSendDTO, @RequestHeader(SecurityConstants.FROM) String from);

}
