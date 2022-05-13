package com.hongnx.cloud.weixin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.weixin.common.entity.WxMenu;
import com.hongnx.cloud.weixin.common.entity.WxMenuRule;
import me.chanjar.weixin.common.error.WxErrorException;

import java.io.Serializable;

/**
 * 自定义菜单
 *
 * @date 2019-03-27 16:52:10
 */
public interface WxMenuService extends IService<WxMenu> {

	/***
	 * 获取WxApp下的菜单
	 * @param appId
	 * @param menuRuleId
	 * @return
	 */
	String getWxMenuButton(String appId, String menuRuleId);

	/**
	 * 保存并发布菜单
	 * @param
	 */
	WxMenuRule saveAndRelease(String appId , String strWxMenu) throws WxErrorException;

	/**
	 * 删除菜单
	 * @param ruleId
	 * @return
	 */
	void removeByRuleId(Serializable ruleId) throws WxErrorException;
}
