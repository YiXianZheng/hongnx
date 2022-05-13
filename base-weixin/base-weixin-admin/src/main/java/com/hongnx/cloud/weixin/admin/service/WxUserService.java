package com.hongnx.cloud.weixin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.weixin.common.dto.WxOpenDataDTO;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import com.hongnx.cloud.weixin.common.entity.WxUser;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信用户
 *
 * @date 2019-03-25 15:39:39
 */
public interface WxUserService extends IService<WxUser> {

	/**
	 * 根据openId获取用户
	 * @param appId
	 * @param openId
	 * @return
	 */
	WxUser getByOpenId(String appId, String openId);

	/**
	 * 同步微信用户
	 * @param appId
	 */
	void synchroWxUser(String appId) throws WxErrorException;

	/**
	 * 修改用户备注
	 * @param entity
	 * @return
	 */
	boolean updateRemark(WxUser entity) throws WxErrorException;

	/**
	 * 认识标签
	 * @param taggingType
	 * @param appId
	 * @param tagId
	 * @param openIds
	 * @throws WxErrorException
	 */
	void tagging(String taggingType,String appId,Long tagId,String[] openIds) throws WxErrorException;

	/**
	 * 保存微信用户
	 * @param wxOpenDataDTO
	 */
	WxUser saveInside(WxOpenDataDTO wxOpenDataDTO);

	/**
	 * 小程序登录
	 * @param wxApp
	 * @param jsCode
	 * @return
	 */
	WxUser loginMa(WxApp wxApp, String jsCode) throws WxErrorException;
}
