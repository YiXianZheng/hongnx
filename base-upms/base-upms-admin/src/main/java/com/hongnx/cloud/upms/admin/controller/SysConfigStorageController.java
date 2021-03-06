package com.hongnx.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.common.security.annotation.Inside;
import com.hongnx.cloud.upms.admin.service.SysConfigStorageService;
import com.hongnx.cloud.upms.common.entity.SysConfigStorage;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 存储配置
 *
 * @date 2020-02-03 20:07:38
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/configstorage")
@Api(value = "configstorage", tags = "存储配置管理")
public class SysConfigStorageController {

    private final SysConfigStorageService sysConfigStorageService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param sysConfigStorage 存储配置
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:index')")
    public R getPage(Page page, SysConfigStorage sysConfigStorage) {
        return R.ok(sysConfigStorageService.page(page, Wrappers.query(sysConfigStorage)));
    }

    /**
     * 存储配置查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "存储配置查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(sysConfigStorageService.getById(id));
    }

    /**
     * 存储配置新增
     * @param sysConfigStorage 存储配置
     * @return R
     */
	@ApiOperation(value = "存储配置新增")
    @SysLog("新增存储配置")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:add')")
    public R save(@RequestBody SysConfigStorage sysConfigStorage) {
        return R.ok(sysConfigStorageService.save(sysConfigStorage));
    }

    /**
     * 存储配置修改
     * @param sysConfigStorage 存储配置
     * @return R
     */
	@ApiOperation(value = "存储配置修改")
    @SysLog("修改存储配置")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:edit')")
    public R updateById(@RequestBody SysConfigStorage sysConfigStorage) {
		if(StrUtil.isNotBlank(sysConfigStorage.getId())){
			sysConfigStorageService.updateById(sysConfigStorage);
			return R.ok(sysConfigStorage);
		}else{
			sysConfigStorageService.save(sysConfigStorage);
			return R.ok(sysConfigStorage);
		}
    }

    /**
     * 存储配置删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "存储配置删除")
    @SysLog("删除存储配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(sysConfigStorageService.removeById(id));
    }

	/**
	 * 查询存储配置
	 * @return R
	 */
	@ApiOperation(value = "查询存储配置")
	@GetMapping()
	@PreAuthorize("@ato.hasAuthority('sys:configstorage:get')")
	public R get() {
		return R.ok(sysConfigStorageService.getOne(Wrappers.emptyWrapper()));
	}

	/**
	 * 查询存储配置（服务间调用）
	 * @return R
	 */
	@Inside
	@ApiOperation(value = "存储配置查询")
	@GetMapping("/inside")
	public R getInside() {
		return R.ok(sysConfigStorageService.getOne(Wrappers.emptyWrapper()));
	}
}
