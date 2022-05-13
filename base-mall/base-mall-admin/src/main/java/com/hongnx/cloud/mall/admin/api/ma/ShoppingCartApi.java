package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.ShoppingCartService;
import com.hongnx.cloud.mall.common.entity.ShoppingCart;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车
 *
 * @date 2019-08-29 21:27:33
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/shoppingcart")
@Api(value = "shoppingcart", tags = "购物车API")
public class ShoppingCartApi{

    private final ShoppingCartService shoppingCartService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param shoppingCart 购物车
	 * @return
	 */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public R getShoppingCartPage(Page page, ShoppingCart shoppingCart) {
		shoppingCart.setUserId(ThirdSessionHolder.getMallUserId());
		return R.ok(shoppingCartService.page2(page, shoppingCart));
    }

	/**
	 * 数量
	 * @param shoppingCart
	 * @return
	 */
	@ApiOperation(value = "查询数量")
	@GetMapping("/count")
	public R getShoppingCartCount(ShoppingCart shoppingCart) {
		shoppingCart.setUserId(ThirdSessionHolder.getMallUserId());
		return R.ok(shoppingCartService.count(Wrappers.query(shoppingCart)));
	}

	/**
	 * 加入购物车
	 * @param shoppingCart
	 * @return
	 */
	@ApiOperation(value = "加入购物车")
	@PostMapping
	public R save(@RequestBody ShoppingCart shoppingCart){
		shoppingCart.setUserId(ThirdSessionHolder.getMallUserId());
		return R.ok(shoppingCartService.save(shoppingCart));
	}

	/**
	 * 修改购物车商品
	 * @param shoppingCart
	 * @return
	 */
	@ApiOperation(value = "修改购物车商品")
	@PutMapping
	public R edit(@RequestBody ShoppingCart shoppingCart){
		shoppingCart.setUserId(ThirdSessionHolder.getMallUserId());
		return R.ok(shoppingCartService.updateById(shoppingCart));
	}

	/**
	 * 删除购物车商品数量
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "删除购物车商品数量")
	@PostMapping("/del")
	public R del(@RequestBody List<String> ids){
		return R.ok(shoppingCartService.removeByIds(ids));
	}
}
