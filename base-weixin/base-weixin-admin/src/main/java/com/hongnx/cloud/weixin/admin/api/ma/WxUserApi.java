package com.hongnx.cloud.weixin.admin.api.ma;

import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.weixin.admin.service.WxUserService;
import com.hongnx.cloud.weixin.admin.config.ma.WxMaConfiguration;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import com.hongnx.cloud.weixin.common.entity.WxUser;
import com.hongnx.cloud.weixin.common.dto.LoginMaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 微信用户
 *
 * @date 2019-08-25 15:39:39
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/wxuser")
@Api(value = "wxuser", tags = "小程序用户API")
public class WxUserApi {

	private final WxUserService wxUserService;
	/**
	 * 小程序用户登录
	 * @param request
	 * @param loginMaDTO
	 * @return
	 */
	@ApiOperation(value = "小程序用户登录")
	@PostMapping("/login")
	public R login(HttpServletRequest request, @RequestBody LoginMaDTO loginMaDTO){
		try {
			WxApp wxApp = WxMaConfiguration.getApp(request);
			WxUser wxUser = wxUserService.loginMa(wxApp,loginMaDTO.getJsCode());
			return R.ok(wxUser);
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed(e.getMessage());
		}
	}
}
