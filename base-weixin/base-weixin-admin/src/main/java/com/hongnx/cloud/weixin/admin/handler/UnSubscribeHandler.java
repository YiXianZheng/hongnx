package com.hongnx.cloud.weixin.admin.handler;

import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.weixin.common.constant.ConfigConstant;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import com.hongnx.cloud.weixin.common.entity.WxUser;
import com.hongnx.cloud.weixin.admin.service.WxAppService;
import com.hongnx.cloud.weixin.admin.service.WxMsgService;
import com.hongnx.cloud.weixin.admin.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户取消关注
 */
@Slf4j
@Component
@AllArgsConstructor
public class UnSubscribeHandler extends AbstractHandler {

	private final WxAppService wxAppService;
	private final WxUserService wxUserService;
	private final WxMsgService wxMsgService;
	private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        log.info("取消关注用户 OPENID: " + openId);
        // TODO 更新本地数据库为取消关注状态
		WxApp wxApp = wxAppService.findByWeixinSign(wxMessage.getToUser());
		TenantContextHolder.setTenantId(wxApp.getTenantId());//加入租户ID
		WxUser wxUser = wxUserService.getByOpenId(wxApp.getId(), openId);
		if(wxUser!=null){
			wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_NO);
			wxUser.setCancelSubscribeTime(LocalDateTime.now());
			wxUserService.updateById(wxUser);
			//消息记录
			MsgHandler.getWxMpXmlOutMessage(wxMessage,null,wxApp,wxUser,wxMsgService,simpMessagingTemplate);
		}
        return null;
    }

}
