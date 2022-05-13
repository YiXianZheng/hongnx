package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.EnsureGoodsService;
import com.hongnx.cloud.mall.common.entity.EnsureGoods;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商品保障
 *
 * @date 2020-02-10 00:02:09
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/ensuregoods")
@Api(value = "ensuregoods", tags = "商品保障管理")
public class EnsureGoodsController {

    private final EnsureGoodsService ensureGoodsService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param ensureGoods 商品保障
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:ensuregoods:index')")
    public R getPage(Page page, EnsureGoods ensureGoods) {
        return R.ok(ensureGoodsService.page(page, Wrappers.query(ensureGoods)));
    }

    /**
     * 商品保障查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "商品保障查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:ensuregoods:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(ensureGoodsService.getById(id));
    }

    /**
     * 商品保障新增
     * @param ensureGoods 商品保障
     * @return R
     */
	@ApiOperation(value = "商品保障新增")
    @SysLog("新增商品保障")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:ensuregoods:add')")
    public R save(@RequestBody EnsureGoods ensureGoods) {
        return R.ok(ensureGoodsService.save(ensureGoods));
    }

    /**
     * 商品保障修改
     * @param ensureGoods 商品保障
     * @return R
     */
	@ApiOperation(value = "商品保障修改")
    @SysLog("修改商品保障")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:ensuregoods:edit')")
    public R updateById(@RequestBody EnsureGoods ensureGoods) {
        return R.ok(ensureGoodsService.updateById(ensureGoods));
    }

    /**
     * 商品保障删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "商品保障删除")
    @SysLog("删除商品保障")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:ensuregoods:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(ensureGoodsService.removeById(id));
    }

}
