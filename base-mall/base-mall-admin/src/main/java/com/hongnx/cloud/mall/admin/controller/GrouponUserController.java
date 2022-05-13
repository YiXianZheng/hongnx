package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.GrouponUserService;
import com.hongnx.cloud.mall.common.entity.GrouponUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 拼团记录
 *
 * @date 2020-03-17 12:01:53
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/grouponuser")
@Api(value = "grouponuser", tags = "拼团记录管理")
public class GrouponUserController {

    private final GrouponUserService grouponUserService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param grouponUser 拼团记录
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:grouponuser:index')")
    public R getPage(Page page, GrouponUser grouponUser) {
        return R.ok(grouponUserService.page1(page, grouponUser));
    }

    /**
     * 拼团记录查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "拼团记录查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:grouponuser:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(grouponUserService.getById(id));
    }

    /**
     * 拼团记录新增
     * @param grouponUser 拼团记录
     * @return R
     */
    @ApiOperation(value = "拼团记录新增")
    @SysLog("新增拼团记录")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:grouponuser:add')")
    public R save(@RequestBody GrouponUser grouponUser) {
        return R.ok(grouponUserService.save(grouponUser));
    }

    /**
     * 拼团记录修改
     * @param grouponUser 拼团记录
     * @return R
     */
    @ApiOperation(value = "拼团记录修改")
    @SysLog("修改拼团记录")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:grouponuser:edit')")
    public R updateById(@RequestBody GrouponUser grouponUser) {
        return R.ok(grouponUserService.updateById(grouponUser));
    }

    /**
     * 拼团记录删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "拼团记录删除")
    @SysLog("删除拼团记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:grouponuser:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(grouponUserService.removeById(id));
    }

}
