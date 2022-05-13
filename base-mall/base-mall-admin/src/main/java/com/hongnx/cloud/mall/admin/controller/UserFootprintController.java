package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.UserFootprintService;
import com.hongnx.cloud.mall.common.entity.UserFootprint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 用户足迹
 *
 * @date 2020-12-24 22:15:45
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/userfootprint")
@Api(value = "userfootprint", tags = "用户足迹管理")
public class UserFootprintController {

    private final UserFootprintService userFootprintService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param userFootprint 用户足迹
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:userfootprint:index')")
    public R getPage(Page page, UserFootprint userFootprint) {
        return R.ok(userFootprintService.page1(page, userFootprint));
    }

    /**
     * 用户足迹查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "用户足迹查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:userfootprint:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(userFootprintService.getById(id));
    }

    /**
     * 用户足迹新增
     * @param userFootprint 用户足迹
     * @return R
     */
    @ApiOperation(value = "用户足迹新增")
    @SysLog("新增用户足迹")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:userfootprint:add')")
    public R save(@RequestBody UserFootprint userFootprint) {
        return R.ok(userFootprintService.save(userFootprint));
    }

    /**
     * 用户足迹修改
     * @param userFootprint 用户足迹
     * @return R
     */
    @ApiOperation(value = "用户足迹修改")
    @SysLog("修改用户足迹")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:userfootprint:edit')")
    public R updateById(@RequestBody UserFootprint userFootprint) {
        return R.ok(userFootprintService.updateById(userFootprint));
    }

    /**
     * 用户足迹删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "用户足迹删除")
    @SysLog("删除用户足迹")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:userfootprint:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(userFootprintService.removeById(id));
    }

}
