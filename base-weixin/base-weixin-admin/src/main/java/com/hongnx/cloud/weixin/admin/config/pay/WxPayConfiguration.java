package com.hongnx.cloud.weixin.admin.config.pay;

import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.hongnx.cloud.weixin.admin.service.WxAppService;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付Configuration
 *
 */
@Slf4j
@Configuration
public class WxPayConfiguration {

	private static WxAppService wxAppService;

	@Autowired
	public WxPayConfiguration( WxAppService wxAppService) {
		this.wxAppService = wxAppService;
	}

	/**
	 *  获取WxMpService
	 * @param appId
	 * @return
	 */
	public static WxPayService getPayService(String appId) {
		WxPayService wxPayService = null;
		WxApp wxApp = wxAppService.findByAppId(appId);
		if(wxApp!=null) {
			if(StrUtil.isNotBlank(wxApp.getMchId()) && StrUtil.isNotBlank(wxApp.getMchKey())){
				WxPayConfig payConfig = new WxPayConfig();
				payConfig.setAppId(wxApp.getId());
				payConfig.setMchId(wxApp.getMchId());
				payConfig.setMchKey(wxApp.getMchKey());
				payConfig.setKeyPath(wxApp.getKeyPath());
				// 可以指定是否使用沙箱环境
				payConfig.setUseSandboxEnv(false);
				wxPayService = new WxPayServiceImpl();
				wxPayService.setConfig(payConfig);
			}else{
				log.error("请在后台小程序管理中配置收款账号");
			}
		}else{
			throw new RuntimeException("无此小程序：" + appId);
		}
		return wxPayService;
    }

}
