package com.hongnx.cloud.upms.admin.controller;

import com.aliyuncs.exceptions.ClientException;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.common.sms.util.SmsUtils;
import com.hongnx.cloud.upms.common.entity.SysSms;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2019/07/14
 * 短信管理
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sms")
@Api(value = "sms", tags = "短信管理")
public class SmsController {
	private final SmsUtils smsUtils;

	/**
	 * 发送短信
	 * @param sysSms
	 * @return
	 */
	@ApiOperation(value = "发送短信")
	@SysLog("发送短信")
	@PostMapping("/send")
	@PreAuthorize("@ato.hasAuthority('sys:sms:add')")
	public R sendEmail(@RequestBody SysSms sysSms) throws ClientException {
		smsUtils.sendSms(sysSms.getSignName(), sysSms.getPhoneNumbers(), sysSms.getTemplateCode(), sysSms.getTemplateParam());
		return R.ok();
	}
}
