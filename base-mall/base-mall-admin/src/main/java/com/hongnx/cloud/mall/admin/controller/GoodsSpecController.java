package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.GoodsSpecService;
import com.hongnx.cloud.mall.common.entity.GoodsSpec;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 规格
 *
 * @date 2019-08-13 16:10:54
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodsspec")
@Api(value = "goodsspec", tags = "规格管理")
public class GoodsSpecController {

    private final GoodsSpecService goodsSpecService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param goodsSpec 规格
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:goodsspec:index')")
    public R getGoodsSpecPage(Page page, GoodsSpec goodsSpec) {
        return R.ok(goodsSpecService.page(page,Wrappers.query(goodsSpec)));
    }

	@ApiOperation(value = "list查询")
	@GetMapping("/list")
	public R getGoodsSpecList(GoodsSpec goodsSpec) {
		return R.ok(goodsSpecService.list(Wrappers.query(goodsSpec)));
	}

    /**
    * 通过id查询规格
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询规格")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodsspec:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(goodsSpecService.getById(id));
    }

    /**
    * 新增规格
    * @param goodsSpec 规格
    * @return R
    */
	@ApiOperation(value = "新增规格")
    @SysLog("新增规格")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodsspu:index')")
    public R save(@RequestBody GoodsSpec goodsSpec){
		goodsSpecService.save(goodsSpec);
        return R.ok(goodsSpec);
    }

    /**
    * 修改规格
    * @param goodsSpec 规格
    * @return R
    */
	@ApiOperation(value = "修改规格")
    @SysLog("修改规格")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodsspec:edit')")
    public R updateById(@RequestBody GoodsSpec goodsSpec){
        return R.ok(goodsSpecService.updateById(goodsSpec));
    }

    /**
    * 通过id删除规格
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除规格")
    @SysLog("删除规格")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodsspec:del')")
    public R removeById(@PathVariable String id){
        return R.ok(goodsSpecService.removeById(id));
    }

}
