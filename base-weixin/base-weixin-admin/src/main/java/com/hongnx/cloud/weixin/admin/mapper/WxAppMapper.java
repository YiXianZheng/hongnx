package com.hongnx.cloud.weixin.admin.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.weixin.common.entity.WxApp;

import java.util.List;

/**
 * 微信应用
 *
 * @date 2019-03-15 10:26:44
 */
public interface WxAppMapper extends BaseMapper<WxApp> {

	/**
	 * 通过weixinSign获取WxApp，无租户条件
	 * @param weixinSign
	 * @return
	 */
	@InterceptorIgnore(tenantLine="true")
	List<WxApp> findByWeixinSign(String weixinSign);

	/**
	 * 通过appId获取WxApp，无租户条件
	 * @param appId
	 * @return
	 */
	@InterceptorIgnore(tenantLine="true")
	List<WxApp> findByAppId(String appId);
}
