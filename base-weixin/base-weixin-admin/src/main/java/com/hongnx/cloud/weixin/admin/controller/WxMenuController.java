package com.hongnx.cloud.weixin.admin.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.weixin.admin.service.WxMenuService;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 自定义菜单
 *
 * @date 2019-03-27 16:52:10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmenu")
@Api(value = "wxmenu", tags = "微信自定义菜单管理")
public class WxMenuController {

	private final WxMenuService wxMenuService;

	/**
	 * 通过appId查询自定义菜单
	 *
	 * @param appId
	 * @param menuRuleId
	 * @return R
	 */
	@ApiOperation(value = "通过appId查询自定义菜单")
	@GetMapping("/list")
	public R getWxMenuButton(String appId, String menuRuleId) {
		return R.ok(wxMenuService.getWxMenuButton(appId, menuRuleId));
	}

	/**
	 * 保存并发布菜单
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "保存并发布菜单")
	@SysLog("保存并发布菜单")
	@PostMapping("/release")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxmenu:add')")
	public R saveAndRelease(@RequestBody String data) {
		JSONObject jSONObject = JSONUtil.parseObj(data);
		String strWxMenu = jSONObject.getStr("strWxMenu");
		String appId = jSONObject.getStr("appId");
		try {
			return R.ok(wxMenuService.saveAndRelease(appId, strWxMenu));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("发布自定义菜单失败appID:" + appId + ":" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 删除菜单
	 *
	 * @param ruleId
	 * @return R
	 */
	@ApiOperation(value = "删除菜单")
	@SysLog("删除微信菜单")
	@DeleteMapping("/{ruleId}")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxmenu:add')")
	public R removeByRuleId(@PathVariable String ruleId) {
		try {
			wxMenuService.removeByRuleId(ruleId);
			return R.ok();
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除微信菜单失败ruleId:" + ruleId + ":" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}
}
