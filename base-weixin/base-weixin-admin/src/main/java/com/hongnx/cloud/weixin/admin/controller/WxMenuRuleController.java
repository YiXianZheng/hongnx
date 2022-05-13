package com.hongnx.cloud.weixin.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.weixin.admin.service.WxMenuRuleService;
import com.hongnx.cloud.weixin.common.entity.WxMenuRule;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 微信自定义菜单分组
 *
 * @date 2020-02-22 19:34:22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmenurule")
@Api(value = "wxmenurule", tags = "微信自定义菜单分组管理")
public class WxMenuRuleController {

    private final WxMenuRuleService wxMenuRuleService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param wxMenuRule 微信自定义菜单分组
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmenurule:index')")
    public R getPage(Page page, WxMenuRule wxMenuRule) {
        return R.ok(wxMenuRuleService.page(page, Wrappers.query(wxMenuRule)));
    }

    /**
     * 微信自定义菜单分组查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "微信自定义菜单分组查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmenurule:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(wxMenuRuleService.getById(id));
    }

    /**
     * 微信自定义菜单分组新增
     * @param wxMenuRule 微信自定义菜单分组
     * @return R
     */
	@ApiOperation(value = "微信自定义菜单分组新增")
    @SysLog("新增微信自定义菜单分组")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmenurule:add')")
    public R save(@RequestBody WxMenuRule wxMenuRule) {
        return R.ok(wxMenuRuleService.save(wxMenuRule));
    }

    /**
     * 微信自定义菜单分组修改
     * @param wxMenuRule 微信自定义菜单分组
     * @return R
     */
	@ApiOperation(value = "微信自定义菜单分组修改")
    @SysLog("修改微信自定义菜单分组")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmenurule:edit')")
    public R updateById(@RequestBody WxMenuRule wxMenuRule) {
        return R.ok(wxMenuRuleService.updateById(wxMenuRule));
    }

    /**
     * 微信自定义菜单分组删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "微信自定义菜单分组删除")
    @SysLog("删除微信自定义菜单分组")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp:wxmenurule:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(wxMenuRuleService.removeById(id));
    }

}
