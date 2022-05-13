package com.hongnx.cloud.mall.admin.api.ma;

import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.UserInfoService;
import com.hongnx.cloud.mall.common.feign.FeignWxUserService;
import com.hongnx.cloud.mall.common.dto.WxOpenDataDTO;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
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
@Api(value = "userinfo", tags = "微信用户API")
public class WxUserApi {

	private final FeignWxUserService feignWxUserService;
	private final UserInfoService userInfoService;

	/**
	 * 获取用户信息
	 * @param
	 * @return
	 */
	@ApiOperation(value = "获取用户信息")
	@GetMapping
	public R get(){
		return feignWxUserService.getById(ThirdSessionHolder.getThirdSession().getWxUserId(), SecurityConstants.FROM_IN);
	}

	/**
	 * 保存用户信息
	 * @param wxOpenDataDTO
	 * @return
	 */
	@ApiOperation(value = "保存用户信息")
	@PostMapping
	public R save(HttpServletRequest request, @RequestBody WxOpenDataDTO wxOpenDataDTO){
		wxOpenDataDTO.setAppId(ThirdSessionHolder.getThirdSession().getAppId());
		wxOpenDataDTO.setUserId(ThirdSessionHolder.getThirdSession().getWxUserId());
		wxOpenDataDTO.setSessionKey(ThirdSessionHolder.getThirdSession().getSessionKey());
		return userInfoService.saveByWxUser(wxOpenDataDTO);
	}
}
