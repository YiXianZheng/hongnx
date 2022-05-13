package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.GoodsSku;

import java.util.List;

/**
 * 商品sku
 *
 * @date 2019-08-13 10:18:34
 */
public interface GoodsSkuMapper extends BaseMapper<GoodsSku> {

	List<GoodsSku> listGoodsSkuBySpuId(String spuId);

	GoodsSku selectOneByShoppingCart(String id);

	GoodsSku getGoodsSkuById(String id);
}
