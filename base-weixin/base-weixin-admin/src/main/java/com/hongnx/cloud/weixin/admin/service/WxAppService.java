package com.hongnx.cloud.weixin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.weixin.common.entity.WxApp;

/**
 * 微信应用
 *
 * @date 2019-03-15 10:26:44
 */
public interface WxAppService extends IService<WxApp> {

	/**
	 * 微信原始标识查找
	 * @param weixinSign
	 * @return
	 */
	WxApp findByWeixinSign(String weixinSign);

	/**
	 * 通过appId获取WxApp，无租户条件
	 * @param appId
	 * @return
	 */
	WxApp findByAppId(String appId);
}
