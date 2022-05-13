package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.ShoppingCart;

/**
 * 购物车
 *
 * @date 2019-08-29 21:27:33
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

	IPage<ShoppingCart> page2(IPage<ShoppingCart> page, ShoppingCart shoppingCart);
}
