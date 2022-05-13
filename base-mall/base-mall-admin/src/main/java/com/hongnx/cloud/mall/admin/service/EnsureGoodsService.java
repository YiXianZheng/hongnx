package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.Ensure;
import com.hongnx.cloud.mall.common.entity.EnsureGoods;

import java.util.List;

/**
 * 商品保障
 *
 * @date 2020-02-10 00:02:09
 */
public interface EnsureGoodsService extends IService<EnsureGoods> {

	/**
	 * 通过spuID，查询商品保障
	 * @param spuId
	 * @return
	 */
	List<Ensure> listEnsureBySpuId(String spuId);
}
