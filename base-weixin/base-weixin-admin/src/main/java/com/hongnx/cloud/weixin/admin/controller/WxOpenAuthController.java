package com.hongnx.cloud.weixin.admin.controller;

import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.weixin.admin.service.WxAppService;
import com.hongnx.cloud.weixin.common.constant.ConfigConstant;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import com.hongnx.cloud.weixin.admin.config.open.WxOpenConfiguration;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 开发平台授权控制
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/open/auth")
@Api(value = "openauth", tags = "开发平台授权控制")
public class WxOpenAuthController {

	private final WxAppService wxAppService;

	@GetMapping("/goto_auth_url")
	public void gotoPreAuthUrl(HttpServletRequest request, HttpServletResponse response,
							   @RequestParam("tenantId") String tenantId,
							   @RequestParam("organId") String organId,
							   @RequestParam("host") String host,
							   @RequestParam("authType") String authType){
		String url = host+"weixin/open/auth/jump?tenantId="+tenantId+"&organId="+organId;
		try {
			url = WxOpenConfiguration.getOpenService().getWxOpenComponentService().getPreAuthUrl(url,authType,null);
			// 添加来源，解决302跳转来源丢失的问题
			response.addHeader("Referer", host);
			response.sendRedirect(url);
		} catch (WxErrorException | IOException e) {
			log.error("gotoPreAuthUrl", e);
			throw new RuntimeException(e);
		}
	}
	@GetMapping("/jump")
	@ResponseBody
	public String jump(@RequestParam("auth_code") String authorizationCode,
					   @RequestParam("tenantId") String tenantId,
					   @RequestParam("organId") String organId){
		try {
			WxOpenQueryAuthResult queryAuthResult = WxOpenConfiguration.getOpenService().getWxOpenComponentService().getQueryAuth(authorizationCode);
			log.info("getQueryAuth", queryAuthResult);
			String appId = queryAuthResult.getAuthorizationInfo().getAuthorizerAppid();
			WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = WxOpenConfiguration.getOpenService().getWxOpenComponentService().getAuthorizerInfo(appId);
			WxOpenAuthorizerInfo wxOpenAuthorizerInfo = wxOpenAuthorizerInfoResult.getAuthorizerInfo();
			WxApp wxApp = new WxApp();
			wxApp.setDelFlag(CommonConstants.STATUS_NORMAL);
			wxApp.setId(appId);
			TenantContextHolder.setTenantId(tenantId);//加入租户ID
			wxAppService.removeById(wxApp);
			wxApp.setOrganId(organId);
			wxApp.setIsComponent(CommonConstants.YES);
			wxApp.setName(wxOpenAuthorizerInfo.getNickName());
			wxApp.setWeixinHao(wxOpenAuthorizerInfo.getAlias());
			wxApp.setWeixinSign(wxOpenAuthorizerInfo.getUserName());
			wxApp.setWeixinType(String.valueOf(wxOpenAuthorizerInfo.getServiceTypeInfo()));
			wxApp.setVerifyType(String.valueOf(wxOpenAuthorizerInfo.getVerifyTypeInfo()));
			wxApp.setLogo(wxOpenAuthorizerInfo.getHeadImg());
			wxApp.setQrCode(wxOpenAuthorizerInfo.getQrcodeUrl());
			wxApp.setPrincipalName(wxOpenAuthorizerInfo.getPrincipalName());
			WxOpenAuthorizerInfo.MiniProgramInfo miniProgramInfo = wxOpenAuthorizerInfo.getMiniProgramInfo();
			if(miniProgramInfo == null){
				wxApp.setAppType(ConfigConstant.WX_APP_TYPE_2);
			}else{
				wxApp.setAppType(ConfigConstant.WX_APP_TYPE_1);
			}
			wxAppService.saveOrUpdate(wxApp);
			return "<p style='text-align: center;font-size: 18px;color:green'>恭喜您！授权成功；<a href='/#/weixin/wxmp/wxapp'>返回系统</a></p>";
		} catch (WxErrorException e) {
			log.error("gotoPreAuthUrl", e);
			return "<p style='text-align: center;font-size: 18px;color:red'>抱歉！授权失败："+e.getMessage()+"；<a href='/#/weixin/wxmp/wxapp'>返回系统</a></p>";
		} catch (Exception e) {
			log.error("gotoPreAuthUrl", e);
			if(e.getMessage().contains("PRIMARY")){
				return "<p style='text-align: center;font-size: 18px;color:red'>授权失败：该公众号或小程序已经存在；<a href='/#/weixin/wxmp/wxapp'>返回系统</a></p>";
			}else{
				return "<p style='text-align: center;font-size: 18px;color:red'>抱歉！授权失败："+e.getMessage()+"；<a href='/#/weixin/wxmp/wxapp'>返回系统</a></p>";
			}
		}
	}
}
