package com.hongnx.cloud.weixin.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.weixin.admin.config.mp.WxMpConfiguration;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpFreePublishService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 微信发布
 *
 * @date 2022-03-10 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/freepublish")
@Api(value = "freepublish", tags = "微信发布")
public class WxFreePublishController {

	/**
	 * 删除发布
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "删除发布")
	@SysLog("删除发布")
	@DeleteMapping
	@PreAuthorize("@ato.hasAuthority('wxmp:wxfreepublish:del')")
	public R del(String id, String appId) {
		WxMpFreePublishService wxMpFreePublishService = WxMpConfiguration.getMpService(appId).getFreePublishService();
		try {
			return R.ok(wxMpFreePublishService.deletePushAllArticle(id));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除发布失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 获取成功发布列表
	 *
	 * @param page 获取成功发布列表
	 * @param
	 * @return
	 */
	@ApiOperation(value = "获取成功发布列表")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxfreepublish:index')")
	public R getPage(Page page, String appId) {
		try {
			WxMpFreePublishService wxMpFreePublishService = WxMpConfiguration.getMpService(appId).getFreePublishService();
			int count = (int) page.getSize();
			int offset = (int) page.getCurrent() * count - count;
			return R.ok(wxMpFreePublishService.getPublicationRecords(offset, count));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("获取成功发布列表失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

}
