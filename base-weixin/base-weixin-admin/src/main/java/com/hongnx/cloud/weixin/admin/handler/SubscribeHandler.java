package com.hongnx.cloud.weixin.admin.handler;

import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.weixin.common.constant.ConfigConstant;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import com.hongnx.cloud.weixin.admin.service.WxAppService;
import com.hongnx.cloud.weixin.admin.service.WxAutoReplyService;
import com.hongnx.cloud.weixin.admin.service.WxMsgService;
import com.hongnx.cloud.weixin.common.entity.WxAutoReply;
import com.hongnx.cloud.weixin.common.entity.WxUser;
import com.hongnx.cloud.weixin.admin.service.WxUserService;
import com.hongnx.cloud.common.core.util.LocalDateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 用户关注
 */
@Slf4j
@Component
@AllArgsConstructor
public class SubscribeHandler extends AbstractHandler {

	private final WxAutoReplyService wxAutoReplyService;
	private final WxAppService wxAppService;
	private final WxUserService wxUserService;
	private final WxMsgService wxMsgService;
	private final SimpMessagingTemplate simpMessagingTemplate;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        log.info("新关注用户 OPENID: " + wxMessage.getFromUser());
        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService()
                .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // TODO 添加关注用户到本地数据库
				WxApp wxApp = wxAppService.findByWeixinSign(wxMessage.getToUser());
				TenantContextHolder.setTenantId(wxApp.getTenantId());//加入租户ID
				if(wxApp!=null){
					WxUser wxUser = wxUserService.getByOpenId(wxApp.getId(), userWxInfo.getOpenId());
					if(wxUser==null){//第一次关注
						wxUser = new WxUser();
						wxUser.setSubscribeNum(1);
						this.setWxUserValue(wxApp,wxUser,userWxInfo);
//						wxUser.setTenantId(wxApp.getTenantId());
						wxUserService.save(wxUser);
					}else{//曾经关注过
						wxUser.setSubscribeNum(wxUser.getSubscribeNum()+1);
						this.setWxUserValue(wxApp,wxUser,userWxInfo);
//						wxUser.setTenantId(wxApp.getTenantId());
						wxUserService.updateById(wxUser);
					}
					//发送关注消息
					List<WxAutoReply> listWxAutoReply = wxAutoReplyService.list(Wrappers.<WxAutoReply>query()
							.lambda().eq(WxAutoReply::getAppId, wxApp.getId()).eq(WxAutoReply::getType,ConfigConstant.WX_AUTO_REPLY_TYPE_1));
					WxMpXmlOutMessage wxMpXmlOutMessage = MsgHandler.getWxMpXmlOutMessage(wxMessage,listWxAutoReply,wxApp,wxUser,wxMsgService,simpMessagingTemplate);
					return wxMpXmlOutMessage;
				}
            }
        } catch (Exception e) {
        	log.error("用户关注出错："+e.getMessage());
        }
        return null;
    }

    public static void setWxUserValue(WxApp wxApp,WxUser wxUser,WxMpUser userWxInfo){
		wxUser.setAppType(ConfigConstant.WX_APP_TYPE_2);
		wxUser.setAppId(wxApp.getId());
		wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_YES);
		wxUser.setSubscribeScene(userWxInfo.getSubscribeScene());
		if(null != userWxInfo.getSubscribeTime()){
			wxUser.setSubscribeTime(LocalDateTimeUtils.timestamToDatetime(userWxInfo.getSubscribeTime()*1000));
		}
		wxUser.setOpenId(userWxInfo.getOpenId());
		wxUser.setNickName(userWxInfo.getNickname());
		wxUser.setLanguage(userWxInfo.getLanguage());
		wxUser.setRemark(userWxInfo.getRemark());
		wxUser.setHeadimgUrl(userWxInfo.getHeadImgUrl());
		wxUser.setUnionId(userWxInfo.getUnionId());
		wxUser.setGroupId(JSONUtil.toJsonStr(userWxInfo.getGroupId()));
		wxUser.setTagidList(userWxInfo.getTagIds());
		wxUser.setQrSceneStr(userWxInfo.getQrSceneStr());
	}
}
