package com.hongnx.cloud.weixin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.weixin.admin.service.WxAutoReplyService;
import com.hongnx.cloud.weixin.common.constant.ConfigConstant;
import com.hongnx.cloud.weixin.common.entity.WxAutoReply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * 消息自动回复
 *
 * @date 2019-04-18 15:40:39
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wxautoreply")
@Api(value = "wxautoreply", tags = "消息自动回复")
public class WxAutoReplyController {

    private final WxAutoReplyService wxAutoReplyService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param wxAutoReply 消息自动回复
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxautoreply:index')")
    public R getWxAutoReplyPage(Page page, WxAutoReply wxAutoReply) {
    	return R.ok(wxAutoReplyService.page(page,Wrappers.query(wxAutoReply)));
    }


    /**
    * 通过id查询消息自动回复
    * @param id id
    * @return R
    */
	@ApiOperation(value = "通过id查询消息自动回复")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxautoreply:get')")
    public R getById(@PathVariable("id") String id){
    return R.ok(wxAutoReplyService.getById(id));
    }

    /**
    * 新增消息自动回复
    * @param wxAutoReply 消息自动回复
    * @return R
    */
	@ApiOperation(value = "新增消息自动回复")
    @SysLog("新增消息自动回复")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('wxmp:wxautoreply:add')")
    public R save(@RequestBody WxAutoReply wxAutoReply){
		this.jude(wxAutoReply);
    	return R.ok(wxAutoReplyService.save(wxAutoReply));
    }

    /**
    * 修改消息自动回复
    * @param wxAutoReply 消息自动回复
    * @return R
    */
	@ApiOperation(value = "修改消息自动回复")
    @SysLog("修改消息自动回复")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('wxmp:wxautoreply:edit')")
    public R updateById(@RequestBody WxAutoReply wxAutoReply){
		this.jude(wxAutoReply);
    	return R.ok(wxAutoReplyService.updateById(wxAutoReply));
    }

    /**
    * 通过id删除消息自动回复
    * @param id id
    * @return R
    */
	@ApiOperation(value = "通过id删除消息自动回复")
    @SysLog("删除消息自动回复")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxautoreply:del')")
    public R removeById(@PathVariable String id){
    return R.ok(wxAutoReplyService.removeById(id));
    }

	/**
	 * //校验参数
	 * @param wxAutoReply
	 */
	public void jude(WxAutoReply wxAutoReply){
		if(ConfigConstant.WX_AUTO_REPLY_TYPE_2.equals(wxAutoReply.getType())){
			Wrapper<WxAutoReply> queryWrapper = Wrappers.<WxAutoReply>lambdaQuery()
					.eq(WxAutoReply::getReqType,wxAutoReply.getReqType())
					.eq(WxAutoReply::getAppId,wxAutoReply.getAppId());
			List<WxAutoReply> list = wxAutoReplyService.list(queryWrapper);
			if(StringUtils.isNotBlank(wxAutoReply.getId())){
				if(list != null && list.size() == 1){
					if(!list.get(0).getId().equals(wxAutoReply.getId())){
						throw new RuntimeException("请求消息类型重复");
					}
				}
				if(list != null && list.size()>1){
					throw new RuntimeException("请求消息类型重复");
				}
			}
		}
		if(ConfigConstant.WX_AUTO_REPLY_TYPE_3.equals(wxAutoReply.getType())){
			Wrapper<WxAutoReply> queryWrapper = Wrappers.<WxAutoReply>lambdaQuery()
					.eq(WxAutoReply::getReqKey,wxAutoReply.getReqKey())
					.eq(WxAutoReply::getRepType,wxAutoReply.getRepMate())
					.eq(WxAutoReply::getAppId,wxAutoReply.getAppId());
			List<WxAutoReply> list = wxAutoReplyService.list(queryWrapper);
			if(list != null && list.size() == 1){
				if(!list.get(0).getId().equals(wxAutoReply.getId())){
					throw new RuntimeException("关键词重复");
				}
			}
			if(list != null && list.size()>1){
				throw new RuntimeException("关键词重复");
			}
		}
	}
}
