package com.hongnx.cloud.weixin.admin.controller;

import cn.binarywang.wx.miniapp.api.WxMaLiveService;
import cn.binarywang.wx.miniapp.bean.live.WxMaCreateRoomResult;
import cn.binarywang.wx.miniapp.bean.live.WxMaLiveResult;
import cn.binarywang.wx.miniapp.bean.live.WxMaLiveRoomInfo;
import cn.binarywang.wx.miniapp.constant.WxMaApiUrlConstants;
import cn.binarywang.wx.miniapp.json.WxMaGsonBuilder;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.weixin.admin.config.ma.WxMaConfiguration;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import com.hongnx.cloud.weixin.common.entity.WxMaLive;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.enums.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小程序直播
 *
 * @date 2019-03-23 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmalive")
@Api(value = "wxmalive", tags = "小程序直播管理")
public class WxMaLiveController {

	/**
	 * 分页查询
	 *
	 * @param page  分页对象
	 * @return
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalive:index')")
	public R getPage(Page page, HttpServletRequest request) {
		try {
			String appId = request.getParameter("appId");
			int limit = (int)page.getSize();
			int start = (int)page.getCurrent()*limit-limit;
			Map<String, Object>  map = new HashMap<>(2);
			map.put("start", start);
			map.put("limit", limit);
			String responseContent = WxMaConfiguration.getMaService(appId).post(WxMaApiUrlConstants.Broadcast.GET_LIVE_INFO, WxMaGsonBuilder.create().toJson(map));
			JSONObject jsonObject = JSONUtil.parseObj(responseContent);
			if (jsonObject.getInt("errcode") != 0) {
				throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
			}
			return R.ok(WxMaLiveResult.fromJson(jsonObject.toString()));
		} catch (WxErrorException e) {
			if(e.getError().getErrorCode() == 9410000){
				cn.binarywang.wx.miniapp.bean.live.WxMaLiveResult wxMaLiveResult = new cn.binarywang.wx.miniapp.bean.live.WxMaLiveResult();
				wxMaLiveResult.setRoomInfos(new ArrayList<>());
				wxMaLiveResult.setTotal(0);
				return R.ok(wxMaLiveResult);
			}
			e.printStackTrace();
			log.error("分页查询小程序直播失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 小程序直播新增
	 * @param roomInfo 小程序直播
	 * @return R
	 */
	@ApiOperation(value = "小程序直播新增")
	@SysLog("新增小程序直播")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalive:add')")
	public R save(@RequestBody WxMaLive roomInfo) {
		WxMaLiveService wxMaLiveService = WxMaConfiguration.getMaService(roomInfo.getAppId()).getLiveService();
		WxMaLiveRoomInfo wxMaLiveRoomInfo = new WxMaLiveRoomInfo();
		BeanUtil.copyProperties(roomInfo, wxMaLiveRoomInfo);
		try {
			WxMaCreateRoomResult wxMaCreateRoomResult = wxMaLiveService.createRoom(wxMaLiveRoomInfo);
			if(wxMaCreateRoomResult.getRoomId() == null){
				return R.failed(WxReturnCode.ERR_300036.getCode(), WxReturnCode.ERR_300036.getMsg());
			}
			return R.ok(wxMaCreateRoomResult);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("新增小程序直播失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 小程序直播修改
	 * @param roomInfo 小程序直播
	 * @return R
	 */
	@ApiOperation(value = "小程序直播修改")
	@SysLog("修改小程序直播")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalive:edit')")
	public R updateById(@RequestBody WxMaLive roomInfo) {
		WxMaLiveService wxMaLiveService = WxMaConfiguration.getMaService(roomInfo.getAppId()).getLiveService();
		WxMaLiveRoomInfo wxMaLiveRoomInfo = new WxMaLiveRoomInfo();
		BeanUtil.copyProperties(roomInfo, wxMaLiveRoomInfo);
		wxMaLiveRoomInfo.setId(roomInfo.getRoomId());
		try {
			return R.ok(wxMaLiveService.editRoom(wxMaLiveRoomInfo));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("修改小程序直播失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 小程序直播删除
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "小程序直播删除")
	@SysLog("删除小程序直播")
	@DeleteMapping("/{appId}/{id}")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalive:del')")
	public R removeById(@PathVariable String appId,@PathVariable Integer id) {
		WxMaLiveService wxMaLiveService = WxMaConfiguration.getMaService(appId).getLiveService();
		try {
			return R.ok(wxMaLiveService.deleteRoom(id));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除小程序直播失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 获取直播间分享二维码
	 *
	 * @return
	 */
	@ApiOperation(value = "获取直播间分享二维码")
	@GetMapping("/sharedcode/{appId}/{id}")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalive:index')")
	public R getSharedCode(@PathVariable String appId, @PathVariable String id) {
		try {
			Map<String, Object> map = new HashMap<>(2);
			map.put("roomId", id);
			String responseContent = WxMaConfiguration.getMaService(appId).get(WxMaApiUrlConstants.Broadcast.Room.GET_SHARED_CODE, Joiner.on("&").withKeyValueSeparator("=").join(map));
			JSONObject jsonObject = JSONUtil.parseObj(responseContent);
			if (jsonObject.getInt("errcode") != 0) {
				throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
			}
			return R.ok(jsonObject);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("获取直播间分享二维码失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 获取直播间推流地址
	 *
	 * @return
	 */
	@ApiOperation(value = "获取直播间推流地址")
	@GetMapping("/pushurl/{appId}/{id}")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalive:index')")
	public R getPushUrl(@PathVariable String appId, @PathVariable String id) {
		WxMaLiveService wxMaLiveService = WxMaConfiguration.getMaService(appId).getLiveService();
		try {
			return R.ok(wxMaLiveService.getPushUrl(Integer.parseInt(id)));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("获取直播间推流地址失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 直播间导入商品
	 * @param goodsIds 商品 ID
	 * @return R
	 */
	@ApiOperation(value = "直播间导入商品")
	@SysLog("直播间导入商品")
	@PostMapping("/{appId}/{roomId}")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalive:edit')")
	public R addGoods(@PathVariable String appId, @PathVariable Integer roomId, @RequestBody List<Integer> goodsIds) {
		WxMaLiveService wxMaLiveService = WxMaConfiguration.getMaService(appId).getLiveService();
		try {
			return R.ok(wxMaLiveService.addGoodsToRoom(roomId, goodsIds));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("直播间导入商品失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 删除直播间商品
	 * @param goodsId
	 * @return R
	 */
	@ApiOperation(value = "删除直播间商品")
	@SysLog("删除直播间商品")
	@DeleteMapping("/{appId}/{roomId}/{goodsId}")
	@PreAuthorize("@ato.hasAuthority('wxma:wxmalive:del')")
	public R deleteGoods(@PathVariable String appId,@PathVariable Integer roomId, @PathVariable Integer goodsId) {
		Map<String, Object> map = new HashMap<>(2);
		map.put("roomId", roomId);
		map.put("goodsId", goodsId);
		try {
			String responseContent = WxMaConfiguration.getMaService(appId).post("https://api.weixin.qq.com/wxaapi/broadcast/goods/deleteInRoom", WxMaGsonBuilder.create().toJson(map));
			JSONObject jsonObject = JSONUtil.parseObj(responseContent);
			if (jsonObject.getInt("errcode") != 0) {
				throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
			}
			return R.ok();
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除小程序直播失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}
}
