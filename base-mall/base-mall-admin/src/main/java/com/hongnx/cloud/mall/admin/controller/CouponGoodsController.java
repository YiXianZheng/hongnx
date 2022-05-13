package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.CouponGoodsService;
import com.hongnx.cloud.mall.common.entity.CouponGoods;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 电子券商品关联
 *
 * @date 2019-12-16 17:42:19
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/coupongoods")
@Api(value = "coupongoods", tags = "电子券商品关联管理")
public class CouponGoodsController {

    private final CouponGoodsService couponGoodsService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param couponGoods 电子券商品关联
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:coupongoods:index')")
    public R getCouponGoodsPage(Page page, CouponGoods couponGoods) {
        return R.ok(couponGoodsService.page(page, Wrappers.query(couponGoods)));
    }

    /**
     * 电子券商品关联查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "电子券商品关联查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:coupongoods:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(couponGoodsService.getById(id));
    }

    /**
     * 电子券商品关联新增
     * @param couponGoods 电子券商品关联
     * @return R
     */
	@ApiOperation(value = "电子券商品关联新增")
    @SysLog("新增电子券商品关联")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:coupongoods:add')")
    public R save(@RequestBody CouponGoods couponGoods) {
        return R.ok(couponGoodsService.save(couponGoods));
    }

    /**
     * 电子券商品关联修改
     * @param couponGoods 电子券商品关联
     * @return R
     */
	@ApiOperation(value = "电子券商品关联修改")
    @SysLog("修改电子券商品关联")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:coupongoods:edit')")
    public R updateById(@RequestBody CouponGoods couponGoods) {
        return R.ok(couponGoodsService.updateById(couponGoods));
    }

    /**
     * 电子券商品关联删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "电子券商品关联删除")
    @SysLog("删除电子券商品关联")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:coupongoods:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(couponGoodsService.removeById(id));
    }

}
