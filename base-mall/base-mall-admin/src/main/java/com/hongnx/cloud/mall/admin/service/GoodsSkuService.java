package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.GoodsSku;

import java.util.List;

/**
 * 商品sku
 *
 * @date 2019-08-13 10:18:34
 */
public interface GoodsSkuService extends IService<GoodsSku> {

	List<GoodsSku> listGoodsSkuBySpuId(String spuId);
}
