package com.hongnx.cloud.weixin.admin.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
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
import me.chanjar.weixin.mp.api.WxMpDraftService;
import me.chanjar.weixin.mp.api.WxMpFreePublishService;
import me.chanjar.weixin.mp.bean.draft.WxMpAddDraft;
import me.chanjar.weixin.mp.bean.draft.WxMpDraftArticles;
import me.chanjar.weixin.mp.bean.draft.WxMpUpdateDraft;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信草稿箱
 *
 * @date 2022-03-10 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxdraft")
@Api(value = "wxdraft", tags = "微信草稿箱")
public class WxDraftController {

	/**
	 * 新增图文消息
	 *
	 * @param data
	 * @return
	 */
	@ApiOperation(value = "新增草稿箱")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('wxmp:wxdraft:add')")
	public R add(@RequestBody JSONObject data) {
		try {
			String appId = data.getStr("appId");
			JSONArray jSONArray = data.getJSONArray("articles");
			List<WxMpDraftArticles> articles = jSONArray.toList(WxMpDraftArticles.class);
			WxMpAddDraft wxMpAddDraft = new WxMpAddDraft();
			wxMpAddDraft.setArticles(articles);
			WxMpDraftService wxMpDraftService = WxMpConfiguration.getMpService(appId).getDraftService();
			String rs = wxMpDraftService.addDraft(wxMpAddDraft);
			return R.ok(rs);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("新增微信草稿箱失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("新增微信草稿箱失败", e);
			return R.failed(e.getLocalizedMessage());
		}
	}

	/**
	 * 修改微信草稿箱
	 *
	 * @param data
	 * @return
	 */
	@ApiOperation(value = "修改微信草稿箱")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('wxmp:wxdraft:edit')")
	public R edit(@RequestBody JSONObject data) {
		try {
			String appId = data.getStr("appId");
			String mediaId = data.getStr("mediaId");
			JSONArray jSONArray = data.getJSONArray("articles");
			List<WxMpDraftArticles> articles = jSONArray.toList(WxMpDraftArticles.class);
			WxMpDraftService wxMpDraftService = WxMpConfiguration.getMpService(appId).getDraftService();
			WxMpUpdateDraft wxMpUpdateDraft = new WxMpUpdateDraft();
			wxMpUpdateDraft.setMediaId(mediaId);
			int index = 0;
			for (WxMpDraftArticles article : articles) {
				wxMpUpdateDraft.setIndex(index);
				wxMpUpdateDraft.setArticles(article);
				wxMpDraftService.updateDraft(wxMpUpdateDraft);
				index++;
			}
			return new R<>();
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("修改微信草稿箱失败" + e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改微信草稿箱失败", e);
			return R.failed(e.getLocalizedMessage());
		}
	}

	/**
	 * 通过id删除微信草稿箱
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "通过id删除微信草稿箱")
	@SysLog("删除微信草稿箱")
	@DeleteMapping
	@PreAuthorize("@ato.hasAuthority('wxmp:wxdraft:del')")
	public R del(String id, String appId) {
		WxMpDraftService wxMpDraftService = WxMpConfiguration.getMpService(appId).getDraftService();
		try {
			return R.ok(wxMpDraftService.delDraft(id));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除微信草稿箱失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 分页查询
	 *
	 * @param page 分页对象
	 * @param
	 * @return
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxdraft:index')")
	public R getPage(Page page, String appId) {
		try {
			WxMpDraftService wxMpDraftService = WxMpConfiguration.getMpService(appId).getDraftService();
			int count = (int) page.getSize();
			int offset = (int) page.getCurrent() * count - count;
			return R.ok(wxMpDraftService.listDraft(offset, count));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("查询微信草稿箱失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 发布草稿箱
	 * @param id
	 * @param appId
	 * @return
	 */
	@ApiOperation(value = "发布草稿箱")
	@PostMapping("/publish/{appId}/{id}")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxdraft:publish')")
	public R publish(@PathVariable String id, @PathVariable String appId) {
		try {
			WxMpFreePublishService wxMpFreePublishService = WxMpConfiguration.getMpService(appId).getFreePublishService();
			wxMpFreePublishService.submit(id);
			return R.ok();
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("发布草稿箱失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}
}
