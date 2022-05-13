package com.hongnx.cloud.weixin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.weixin.common.dto.WxTemplateMsgSendDTO;
import com.hongnx.cloud.weixin.common.entity.WxTemplateMsg;
import com.hongnx.cloud.weixin.common.entity.WxUser;

/**
 * 微信模板/订阅消息
 *
 * @date 2020-04-16 17:30:03
 */
public interface WxTemplateMsgService extends IService<WxTemplateMsg> {

	/**
	 * 发送微信订阅消息
	 * @param wxTemplateMsgSendDTO
	 * @param wxUser
	 */
	void sendWxTemplateMsg(WxTemplateMsgSendDTO wxTemplateMsgSendDTO, WxUser wxUser);
}
