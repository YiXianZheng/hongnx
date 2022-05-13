package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.SeckillHallInfoService;
import com.hongnx.cloud.mall.common.entity.SeckillHallInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀会场商品
 *
 * @date 2020-08-12 14:01:32
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/seckillhallinfo")
@Api(value = "seckillhallinfo", tags = "秒杀会场商品管理")
public class SeckillHallInfoController {

    private final SeckillHallInfoService seckillHallInfoService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param seckillHallInfo 秒杀会场商品
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:seckillhallinfo:index')")
    public R getPage(Page page, SeckillHallInfo seckillHallInfo) {
        return R.ok(seckillHallInfoService.page(page, Wrappers.query(seckillHallInfo)));
    }

    /**
     * 秒杀会场商品查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "秒杀会场商品查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:seckillhallinfo:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(seckillHallInfoService.getById(id));
    }

    /**
     * 秒杀会场商品新增
     * @param seckillHallInfo 秒杀会场商品
     * @return R
     */
    @ApiOperation(value = "秒杀会场商品新增")
    @SysLog("新增秒杀会场商品")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:seckillhallinfo:add')")
    public R save(@RequestBody SeckillHallInfo seckillHallInfo) {
        return R.ok(seckillHallInfoService.save(seckillHallInfo));
    }

    /**
     * 秒杀会场商品修改
     * @param seckillHallInfo 秒杀会场商品
     * @return R
     */
    @ApiOperation(value = "秒杀会场商品修改")
    @SysLog("修改秒杀会场商品")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:seckillhallinfo:edit')")
    public R updateById(@RequestBody SeckillHallInfo seckillHallInfo) {
        return R.ok(seckillHallInfoService.updateById(seckillHallInfo));
    }

    /**
     * 秒杀会场商品删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "秒杀会场商品删除")
    @SysLog("删除秒杀会场商品")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:seckillhallinfo:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(seckillHallInfoService.removeById(id));
    }

}
