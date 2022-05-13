package com.hongnx.cloud.weixin.admin.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 卡券删除
 */
@Slf4j
@Component
@AllArgsConstructor
public class UserDelCardHandler extends AbstractHandler {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		// TODO 组装回复消息
		String content = "卡券【"+wxMessage.getUserCardCode()+"】删除成功";
		return null;
	}

}
