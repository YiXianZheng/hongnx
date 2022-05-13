package com.hongnx.cloud.weixin.admin.controller;

import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import com.hongnx.cloud.weixin.admin.config.open.WxOpenConfiguration;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开发平台API
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/open/api")
@Api(value = "openapi", tags = "开发平台api")
public class WxOpenApiController {

	/**
	 * getAuthorizerInfo
	 * @param appId
	 * @return
	 */
    @GetMapping("/authorizer-info/{appId}")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxapp:index')")
    public R getAuthorizerInfo(@PathVariable("appId") String appId){
        try {
            WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = WxOpenConfiguration.getOpenService().getWxOpenComponentService().getAuthorizerInfo(appId);
//			WxOpenAuthorizerInfo wxOpenAuthorizerInfo = wxOpenAuthorizerInfoResult.getAuthorizerInfo();
			return R.ok(wxOpenAuthorizerInfoResult);
        } catch (WxErrorException e) {
			e.printStackTrace();
			log.error("getAuthorizerInfo：", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
        }
    }
}
