package com.hongnx.cloud.weixin.admin.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.weixin.admin.service.WxTemplateMsgService;
import com.hongnx.cloud.weixin.admin.config.ma.WxMaConfiguration;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import com.hongnx.cloud.weixin.common.dto.WxTemplateMsgSendDTO;
import com.hongnx.cloud.weixin.common.entity.WxTemplateMsg;
import com.hongnx.cloud.weixin.admin.mapper.WxTemplateMsgMapper;
import com.hongnx.cloud.weixin.common.entity.WxUser;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信模板/订阅消息
 *
 * @date 2020-04-16 17:30:03
 */
@Service
public class WxTemplateMsgServiceImpl extends ServiceImpl<WxTemplateMsgMapper, WxTemplateMsg> implements WxTemplateMsgService {

	/**
	 * 发送微信订阅消息
	 * @param wxTemplateMsgSendDTO wxTemplateMsgSendDTO.useType、wxTemplateMsgSendDTO.page、wxTemplateMsgSendDTO.data、
	 * @param wxUser wxUser.appId、wxUser.openId、
	 */
	@Override
	public void sendWxTemplateMsg(WxTemplateMsgSendDTO wxTemplateMsgSendDTO, WxUser wxUser) {
		//查询订阅消息配置
		WxTemplateMsg wxTemplateMsg = baseMapper.selectOne(Wrappers.<WxTemplateMsg>lambdaQuery()
				.eq(WxTemplateMsg::getAppId,wxUser.getAppId())
				.eq(WxTemplateMsg::getEnable, CommonConstants.YES)
				.eq(WxTemplateMsg::getUseType,wxTemplateMsgSendDTO.getUseType()));
		if(wxTemplateMsg !=null){
			WxMaMsgService wxMaMsgService = WxMaConfiguration.getMaService(wxTemplateMsg.getAppId()).getMsgService();
			WxMaSubscribeMessage subscribeMessage = new WxMaSubscribeMessage();
			subscribeMessage.setToUser(wxUser.getOpenId());
			subscribeMessage.setTemplateId(wxTemplateMsg.getPriTmplId());
			subscribeMessage.setPage(wxTemplateMsgSendDTO.getPage());
			String content = wxTemplateMsg.getContent();
			//提取模板中的参数
			List<String> paramlist=new ArrayList<>();
			Pattern p = Pattern.compile("(?<=\\{)[^\\}]+");
			Matcher m = p.matcher(content);
			while(m.find()){
				paramlist.add(m.group().substring(1, m.group().length()));
			}
			List<WxMaSubscribeMessage.MsgData> listData = new ArrayList<>();
			HashMap<String, HashMap<String, String>> dataMap = wxTemplateMsgSendDTO.getData();
			paramlist.forEach(param -> {
				param = param.replace(".DATA","");
				if(dataMap.containsKey(param)){
					WxMaSubscribeMessage.MsgData data = new WxMaSubscribeMessage.MsgData();
					data.setName(param);
					data.setValue(dataMap.get(param).get("value"));
					listData.add(data);
				}
			});
			subscribeMessage.setData(listData);
			try {
				wxMaMsgService.sendSubscribeMsg(subscribeMessage);
			} catch (WxErrorException e) {
				if(!WxReturnCode.ERR_43101.getCode().equals(e.getError().getErrorCode()+"")){
					log.error(e.getMessage(),e);
				}
			}
		}
	}
}
