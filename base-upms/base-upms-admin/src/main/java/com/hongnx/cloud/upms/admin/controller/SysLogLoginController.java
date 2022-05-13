package com.hongnx.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.common.security.annotation.Inside;
import com.hongnx.cloud.upms.admin.service.SysLogLoginService;
import com.hongnx.cloud.upms.common.entity.SysLogLogin;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 登录日志
 *
 * @date 2020-03-28 16:39:04
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/loglogin")
@Api(value = "loglogin", tags = "登录日志管理")
public class SysLogLoginController {

    private final SysLogLoginService sysLogLoginService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param sysLogLogin 登录日志
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
//    @PreAuthorize("@ato.hasAuthority('sys:loglogin:index')")
    public R getPage(Page page, SysLogLogin sysLogLogin) {
        return R.ok(sysLogLoginService.page(page, Wrappers.query(sysLogLogin)));
    }

    /**
     * 登录日志查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "登录日志查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:loglogin:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(sysLogLoginService.getById(id));
    }

    /**
     * 登录日志新增
     * @param sysLogLogin 登录日志
     * @return R
     */
    @ApiOperation(value = "登录日志新增")
	@Inside
    @PostMapping("/save")
    public R save(@RequestBody SysLogLogin sysLogLogin) {
        return R.ok(sysLogLoginService.save(sysLogLogin));
    }

    /**
     * 登录日志修改
     * @param sysLogLogin 登录日志
     * @return R
     */
    @ApiOperation(value = "登录日志修改")
    @SysLog("修改登录日志")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('sys:loglogin:edit')")
    public R updateById(@RequestBody SysLogLogin sysLogLogin) {
        return R.ok(sysLogLoginService.updateById(sysLogLogin));
    }

    /**
     * 登录日志删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "登录日志删除")
    @SysLog("删除登录日志")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:loglogin:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(sysLogLoginService.removeById(id));
    }

}
