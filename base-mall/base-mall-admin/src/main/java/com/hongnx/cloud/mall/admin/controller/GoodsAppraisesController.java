package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.GoodsAppraisesService;
import com.hongnx.cloud.mall.common.entity.GoodsAppraises;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商品评价
 *
 * @date 2019-09-23 15:51:01
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodsappraises")
@Api(value = "goodsappraises", tags = "商品评价管理")
public class GoodsAppraisesController {

    private final GoodsAppraisesService goodsAppraisesService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param goodsAppraises 商品评价
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:goodsappraises:index')")
    public R getGoodsAppraisesPage(Page page, GoodsAppraises goodsAppraises) {
        return R.ok(goodsAppraisesService.page1(page,goodsAppraises));
    }

    /**
    * 通过id查询商品评价
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询商品评价")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodsappraises:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(goodsAppraisesService.getById(id));
    }

    /**
    * 新增商品评价
    * @param goodsAppraises 商品评价
    * @return R
    */
	@ApiOperation(value = "新增商品评价")
    @SysLog("新增商品评价")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodsappraises:add')")
    public R save(@RequestBody GoodsAppraises goodsAppraises){
        return R.ok(goodsAppraisesService.save(goodsAppraises));
    }

    /**
    * 修改商品评价
    * @param goodsAppraises 商品评价
    * @return R
    */
	@ApiOperation(value = "修改商品评价")
    @SysLog("修改商品评价")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodsappraises:edit')")
    public R updateById(@RequestBody GoodsAppraises goodsAppraises){
        return R.ok(goodsAppraisesService.updateById(goodsAppraises));
    }

    /**
    * 通过id删除商品评价
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除商品评价")
    @SysLog("删除商品评价")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodsappraises:del')")
    public R removeById(@PathVariable String id){
        return R.ok(goodsAppraisesService.removeById(id));
    }

}
