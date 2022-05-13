package com.hongnx.cloud.weixin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.weixin.common.entity.WxMassMsg;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信消息群发
 *
 * @date 2019-07-02 14:17:58
 */
public interface WxMassMsgService extends IService<WxMassMsg> {

	/**
	 * 群发微信消息
	 * @param entity
	 * @return
	 * @throws WxErrorException
	 */
	boolean massMessageSend(WxMassMsg entity) throws WxErrorException;
}
