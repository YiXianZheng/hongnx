package com.hongnx.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.upms.admin.service.SysConfigEditorService;
import com.hongnx.cloud.upms.common.entity.SysConfigEditor;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 编辑器配置
 *
 * @date 2020-02-10 14:23:30
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/configeditor")
@Api(value = "configeditor", tags = "编辑器配置管理")
public class SysConfigEditorController {

    private final SysConfigEditorService sysConfigEditorService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param sysConfigEditor 编辑器配置
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:index')")
    public R getPage(Page page, SysConfigEditor sysConfigEditor) {
        return R.ok(sysConfigEditorService.page(page, Wrappers.query(sysConfigEditor)));
    }

    /**
     * 编辑器配置查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "编辑器配置查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(sysConfigEditorService.getById(id));
    }

    /**
     * 编辑器配置新增
     * @param sysConfigEditor 编辑器配置
     * @return R
     */
	@ApiOperation(value = "编辑器配置新增")
    @SysLog("新增编辑器配置")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:add')")
    public R save(@RequestBody SysConfigEditor sysConfigEditor) {
        return R.ok(sysConfigEditorService.save(sysConfigEditor));
    }

    /**
     * 编辑器配置修改
     * @param sysConfigEditor 编辑器配置
     * @return R
     */
	@ApiOperation(value = "编辑器配置修改")
    @SysLog("修改编辑器配置")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:edit')")
    public R updateById(@RequestBody SysConfigEditor sysConfigEditor) {
		if(StrUtil.isNotBlank(sysConfigEditor.getId())){
			sysConfigEditorService.updateById(sysConfigEditor);
			return R.ok(sysConfigEditor);
		}else{
			sysConfigEditorService.save(sysConfigEditor);
			return R.ok(sysConfigEditor);
		}
    }

    /**
     * 编辑器配置删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "编辑器配置删除")
    @SysLog("删除编辑器配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(sysConfigEditorService.removeById(id));
    }

	/**
	 * 查询编辑器配置
	 * @return R
	 */
	@ApiOperation(value = "查询编辑器配置")
	@GetMapping()
	public R get() {
		return R.ok(sysConfigEditorService.getOne(Wrappers.emptyWrapper()));
	}
}
