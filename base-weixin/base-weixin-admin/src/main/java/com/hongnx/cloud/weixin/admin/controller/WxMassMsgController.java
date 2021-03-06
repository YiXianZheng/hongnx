package com.hongnx.cloud.weixin.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.weixin.admin.service.WxMassMsgService;
import com.hongnx.cloud.weixin.common.constant.ConfigConstant;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import com.hongnx.cloud.weixin.common.entity.WxMassMsg;
import com.hongnx.cloud.weixin.admin.config.mp.WxMpConfiguration;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 微信消息群发
 *
 * @date 2019-07-02 14:17:58
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmassmsg")
@Api(value = "wxmassmsg", tags = "微信消息群发管理")
public class WxMassMsgController {

    private final WxMassMsgService wxMassMsgService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param wxMassMsg 微信消息群发
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmassmsg:index')")
    public R getWxMassMsgPage(Page page, WxMassMsg wxMassMsg) {
        return R.ok(wxMassMsgService.page(page,Wrappers.query(wxMassMsg)));
    }


    /**
    * 通过id查询微信消息群发
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询微信消息群发")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmassmsg:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(wxMassMsgService.getById(id));
    }

    /**
    * 新增微信消息群发
    * @param wxMassMsg 微信消息群发
    * @return R
    */
	@ApiOperation(value = "新增微信消息群发")
    @SysLog("新增微信消息群发")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmassmsg:add')")
    public R save(@RequestBody WxMassMsg wxMassMsg){
		try {
			if(!CommonConstants.YES.equals(wxMassMsg.getIsToAll())){
				if(StringUtils.isBlank(wxMassMsg.getSendType())){
					return R.failed("请选择发送类型");
				}else{
					if(ConfigConstant.WX_MASS_SEND_TYPE_1.equals(wxMassMsg.getSendType()) && wxMassMsg.getTagId()==null){
						return R.failed("请选择用户标签");
					}
					if(ConfigConstant.WX_MASS_SEND_TYPE_2.equals(wxMassMsg.getSendType()) && wxMassMsg.getOpenIds().size()<2){
						return R.failed("请选择至少两个用户");
					}
				}
			}
			return R.ok(wxMassMsgService.massMessageSend(wxMassMsg));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("新增微信消息群发失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
    }

    /**
    * 修改微信消息群发
    * @param wxMassMsg 微信消息群发
    * @return R
    */
	@ApiOperation(value = "修改微信消息群发")
    @SysLog("修改微信消息群发")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmassmsg:edit')")
    public R updateById(@RequestBody WxMassMsg wxMassMsg){
        return R.ok(wxMassMsgService.updateById(wxMassMsg));
    }

    /**
    * 通过id删除微信消息群发
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除微信消息群发")
    @SysLog("删除微信消息群发")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmassmsg:del')")
    public R removeById(@PathVariable String id){
		try {
			WxMassMsg wxMassMsg = wxMassMsgService.getById(id);
			WxMpConfiguration.getMpService(wxMassMsg.getAppId()).getMassMessageService().delete(Long.valueOf(wxMassMsg.getMsgId()),0);
			wxMassMsg.setMsgStatus(ConfigConstant.WX_MASS_STATUS_DELETE);
			wxMassMsgService.updateById(wxMassMsg);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除微信消息群发出错:" + id + ":" + e.getMessage());
		}
        return R.ok();
    }

}
