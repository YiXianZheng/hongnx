package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.GoodsCategoryService;
import com.hongnx.cloud.mall.common.entity.GoodsCategory;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商品类目
 *
 * @date 2019-08-12 11:46:28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodscategory")
@Api(value = "goodscategory", tags = "商品类目管理")
public class GoodsCategoryController {

    private final GoodsCategoryService goodsCategoryService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param goodsCategory 商品类目
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:goodscategory:index')")
    public R getGoodsCategoryPage(Page page, GoodsCategory goodsCategory) {
        return R.ok(goodsCategoryService.page(page,Wrappers.query(goodsCategory)));
    }

	/**
	 *  返回树形集合
	 * @return
	 */
	@ApiOperation(value = "返回树形集合")
	@GetMapping("/tree")
	@PreAuthorize("@ato.hasAuthority('mall:goodscategory:index')")
	public R getGoodsCategoryTree() {
		return R.ok(goodsCategoryService.selectTree(null));
	}

    /**
    * 通过id查询商品类目
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询商品类目")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodscategory:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(goodsCategoryService.getById(id));
    }

    /**
    * 新增商品类目
    * @param goodsCategory 商品类目
    * @return R
    */
	@ApiOperation(value = "新增商品类目")
    @SysLog("新增商品类目")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodscategory:add')")
    public R save(@RequestBody GoodsCategory goodsCategory){
        return R.ok(goodsCategoryService.save(goodsCategory));
    }

    /**
    * 修改商品类目
    * @param goodsCategory 商品类目
    * @return R
    */
	@ApiOperation(value = "修改商品类目")
    @SysLog("修改商品类目")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodscategory:edit')")
    public R updateById(@RequestBody GoodsCategory goodsCategory){
    	if(goodsCategory.getId().equals(goodsCategory.getParentId())){
			return R.failed("不能将本级设为父类");
		}
        return R.ok(goodsCategoryService.updateById(goodsCategory));
    }

    /**
    * 通过id删除商品类目
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除商品类目")
    @SysLog("删除商品类目")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodscategory:del')")
    public R removeById(@PathVariable String id){
        return R.ok(goodsCategoryService.removeById(id));
    }

}
