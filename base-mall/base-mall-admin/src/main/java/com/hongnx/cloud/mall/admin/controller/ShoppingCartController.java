package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.ShoppingCartService;
import com.hongnx.cloud.mall.common.entity.ShoppingCart;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 购物车
 *
 * @date 2019-08-29 21:27:33
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/shoppingcart")
@Api(value = "shoppingcart", tags = "购物车管理")
public class ShoppingCartController {

	private final ShoppingCartService shoppingCartService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param shoppingCart 购物车
	 * @return
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('mall:shoppingcart:index')")
	public R getShoppingCartPage(Page page, ShoppingCart shoppingCart) {
		return R.ok(shoppingCartService.page(page,Wrappers.query(shoppingCart)));
	}

	/**
	 * 通过id查询购物车
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询购物车")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('mall:shoppingcart:get')")
	public R getById(@PathVariable("id") String id){
		return R.ok(shoppingCartService.getById(id));
	}

	/**
	 * 新增购物车
	 * @param shoppingCart 购物车
	 * @return R
	 */
	@ApiOperation(value = "新增购物车")
	@SysLog("新增购物车")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('mall:shoppingcart:add')")
	public R save(@RequestBody ShoppingCart shoppingCart){
		return R.ok(shoppingCartService.save(shoppingCart));
	}

	/**
	 * 修改购物车
	 * @param shoppingCart 购物车
	 * @return R
	 */
	@ApiOperation(value = "修改购物车")
	@SysLog("修改购物车")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('mall:shoppingcart:edit')")
	public R updateById(@RequestBody ShoppingCart shoppingCart){
		return R.ok(shoppingCartService.updateById(shoppingCart));
	}

	/**
	 * 通过id删除购物车
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除购物车")
	@SysLog("删除购物车")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('mall:shoppingcart:del')")
	public R removeById(@PathVariable String id){
		return R.ok(shoppingCartService.removeById(id));
	}

}
