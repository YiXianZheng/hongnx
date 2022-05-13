package com.hongnx.cloud.weixin.admin.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.weixin.common.constant.ConfigConstant;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import com.hongnx.cloud.weixin.common.entity.WxMassMsg;
import com.hongnx.cloud.weixin.admin.service.WxAppService;
import com.hongnx.cloud.weixin.admin.service.WxMassMsgService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 群发结果回调
 */
@Slf4j
@Component
@AllArgsConstructor
public class MassMsgHandler extends AbstractHandler {

	private final WxAppService wxAppService;
	private final WxMassMsgService wxMassMsgService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		// TODO 组装回复消息
		WxApp wxApp = wxAppService.findByWeixinSign(wxMessage.getToUser());
		TenantContextHolder.setTenantId(wxApp.getTenantId());//加入租户ID
		String msgId = String.valueOf(wxMessage.getMsgId());
		WxMassMsg wxMassMsg = wxMassMsgService.getOne(Wrappers.<WxMassMsg>query().lambda()
				.eq(WxMassMsg::getAppId,wxApp.getId())
				.eq(WxMassMsg::getMsgId,msgId));
		String errCode = wxMessage.getStatus();
		wxMassMsg.setMsgStatus(WxConsts.MassMsgStatus.SEND_SUCCESS.equals(errCode) ? ConfigConstant.WX_MASS_STATUS_SEND_SUCCESS : ConfigConstant.WX_MASS_STATUS_SEND_FAIL);
		wxMassMsg.setErrorCode(errCode);
		wxMassMsg.setErrorMsg(WxConsts.MassMsgStatus.STATUS_DESC.get(errCode));
		wxMassMsg.setTotalCount(wxMessage.getTotalCount());
		wxMassMsg.setSentCount(wxMessage.getSentCount());
		wxMassMsg.setErrorCount(wxMessage.getErrorCount());
		wxMassMsg.setFilterCount(wxMessage.getFilterCount());
		wxMassMsgService.updateById(wxMassMsg);
		return null;
	}

}
