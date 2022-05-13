package com.hongnx.cloud.weixin.admin.controller;

import cn.binarywang.wx.miniapp.api.WxMaLiveGoodsService;
import cn.binarywang.wx.miniapp.bean.live.WxMaLiveGoodInfo;
import cn.binarywang.wx.miniapp.bean.live.WxMaLiveResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.weixin.admin.config.ma.WxMaConfiguration;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 小程序直播商品
 *
 * @date 2021-03-10 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmalivegoods")
@Api(value = "wxmalivegoods", tags = "小程序直播商品")
public class WxMaLiveGoodsController {

	/**
	 * 分页查询
	 *
	 * @param page  分页对象
	 * @return
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalivegoods:index')")
	public R getPage(Page page, HttpServletRequest request) {
		try {
			String appId = request.getParameter("appId");
			String status = request.getParameter("status");
			int limit = (int)page.getSize();
			int offset = (int)page.getCurrent()*limit-limit;
			WxMaLiveResult wxMaLiveResult = WxMaConfiguration.getMaService(appId).getLiveGoodsService().getApprovedGoods(offset,limit,Integer.valueOf(status));
			return R.ok(wxMaLiveResult);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("小程序直播商品分页查询失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 小程序直播商品新增
	 * @param wxMaLiveGoodInfo 小程序直播商品
	 * @return R
	 */
	@ApiOperation(value = "小程序直播商品新增")
	@SysLog("新增小程序直播商品")
	@PostMapping("/{appId}")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalivegoods:add')")
	public R save(@PathVariable String appId, @RequestBody WxMaLiveGoodInfo wxMaLiveGoodInfo) {
		WxMaLiveGoodsService wxMaLiveGoodsService = WxMaConfiguration.getMaService(appId).getLiveGoodsService();
		try {
			return R.ok(wxMaLiveGoodsService.addGoods(wxMaLiveGoodInfo));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("新增小程序直播商品失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 小程序直播商品修改
	 * @param wxMaLiveGoodInfo 小程序直播商品
	 * @return R
	 */
	@ApiOperation(value = "小程序直播商品修改")
	@SysLog("修改小程序直播商品")
	@PutMapping("/{appId}")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalivegoods:edit')")
	public R updateById(@PathVariable String appId, @RequestBody WxMaLiveGoodInfo wxMaLiveGoodInfo) {
		WxMaLiveGoodsService wxMaLiveGoodsService = WxMaConfiguration.getMaService(appId).getLiveGoodsService();
		try {
			return R.ok(wxMaLiveGoodsService.updateGoods(wxMaLiveGoodInfo));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("修改小程序直播商品失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 小程序直播商品删除
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "小程序直播商品删除")
	@SysLog("删除小程序直播商品")
	@DeleteMapping("/{appId}/{id}")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalivegoods:del')")
	public R removeById(@PathVariable String appId, @PathVariable Integer id) {
		WxMaLiveGoodsService wxMaLiveGoodsService = WxMaConfiguration.getMaService(appId).getLiveGoodsService();
		try {
			return R.ok(wxMaLiveGoodsService.deleteGoods(id));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除小程序直播商品失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 小程序直播商品提交审核
	 * @param wxMaLiveGoodInfo 小程序直播商品
	 * @return R
	 */
	@ApiOperation(value = "小程序直播商品提交审核")
	@SysLog("小程序直播商品提交审核")
	@PostMapping("/{appId}/audit")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalivegoods:edit')")
	public R audit(@PathVariable String appId, @RequestBody WxMaLiveGoodInfo wxMaLiveGoodInfo) {
		WxMaLiveGoodsService wxMaLiveGoodsService = WxMaConfiguration.getMaService(appId).getLiveGoodsService();
		try {
			return R.ok(wxMaLiveGoodsService.auditGoods(wxMaLiveGoodInfo.getGoodsId()));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("修改小程序直播商品失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}
}
