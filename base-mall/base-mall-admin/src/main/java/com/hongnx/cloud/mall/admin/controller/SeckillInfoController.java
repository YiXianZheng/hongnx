package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.SeckillInfoService;
import com.hongnx.cloud.mall.common.entity.SeckillInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀商品
 *
 * @date 2020-08-12 11:03:50
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/seckillinfo")
@Api(value = "seckillinfo", tags = "秒杀商品管理")
public class SeckillInfoController {

    private final SeckillInfoService seckillInfoService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param seckillInfo 秒杀商品
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:seckillinfo:index')")
    public R getPage(Page page, SeckillInfo seckillInfo) {
        return R.ok(seckillInfoService.page(page, Wrappers.query(seckillInfo)));
    }

    /**
     * 秒杀商品查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "秒杀商品查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:seckillinfo:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(seckillInfoService.getById(id));
    }

    /**
     * 秒杀商品新增
     * @param seckillInfo 秒杀商品
     * @return R
     */
    @ApiOperation(value = "秒杀商品新增")
    @SysLog("新增秒杀商品")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:seckillinfo:add')")
    public R save(@RequestBody SeckillInfo seckillInfo) {
        return R.ok(seckillInfoService.save(seckillInfo));
    }

    /**
     * 秒杀商品修改
     * @param seckillInfo 秒杀商品
     * @return R
     */
    @ApiOperation(value = "秒杀商品修改")
    @SysLog("修改秒杀商品")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:seckillinfo:edit')")
    public R updateById(@RequestBody SeckillInfo seckillInfo) {
        return R.ok(seckillInfoService.updateById(seckillInfo));
    }

    /**
     * 秒杀商品删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "秒杀商品删除")
    @SysLog("删除秒杀商品")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:seckillinfo:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(seckillInfoService.removeById(id));
    }

}
