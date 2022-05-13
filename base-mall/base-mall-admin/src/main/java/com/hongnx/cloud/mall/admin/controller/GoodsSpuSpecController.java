package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.GoodsSpuSpecService;
import com.hongnx.cloud.mall.common.entity.GoodsSpuSpec;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * spu规格
 *
 * @date 2019-08-13 16:56:46
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodsspuspec")
@Api(value = "goodsspuspec", tags = "spu规格管理")
public class GoodsSpuSpecController {

    private final GoodsSpuSpecService goodsSpuSpecService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param goodsSpuSpec spu规格
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:goodsspu:index')")
    public R getGoodsSpuSpecPage(Page page, GoodsSpuSpec goodsSpuSpec) {
        return R.ok(goodsSpuSpecService.page(page,Wrappers.query(goodsSpuSpec)));
    }

	/**
	 * 获取商品规格
	 * @param goodsSpuSpec
	 * @return
	 */
	@ApiOperation(value = "获取商品规格")
	@GetMapping("/tree")
	@PreAuthorize("@ato.hasAuthority('mall:goodsspu:index')")
	public R getGoodsSpuSpecTree(GoodsSpuSpec goodsSpuSpec) {
		return R.ok(goodsSpuSpecService.tree(goodsSpuSpec));
	}

    /**
    * 通过id查询spu规格
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询spu规格")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodsspu:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(goodsSpuSpecService.getById(id));
    }

    /**
    * 新增spu规格
    * @param goodsSpuSpec spu规格
    * @return R
    */
	@ApiOperation(value = "新增spu规格")
    @SysLog("新增spu规格")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodsspu:add')")
    public R save(@RequestBody GoodsSpuSpec goodsSpuSpec){
        return R.ok(goodsSpuSpecService.save(goodsSpuSpec));
    }

    /**
    * 修改spu规格
    * @param goodsSpuSpec spu规格
    * @return R
    */
	@ApiOperation(value = "修改spu规格")
    @SysLog("修改spu规格")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodsspu:edit')")
    public R updateById(@RequestBody GoodsSpuSpec goodsSpuSpec){
        return R.ok(goodsSpuSpecService.updateById(goodsSpuSpec));
    }

    /**
    * 通过id删除spu规格
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除spu规格")
    @SysLog("删除spu规格")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodsspu:del')")
    public R removeById(@PathVariable String id){
        return R.ok(goodsSpuSpecService.removeById(id));
    }

}
