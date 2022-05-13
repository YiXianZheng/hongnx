package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.Ensure;
import com.hongnx.cloud.mall.common.entity.EnsureGoods;

import java.util.List;

/**
 * 商品保障
 *
 * @date 2020-02-10 00:02:09
 */
public interface EnsureGoodsMapper extends BaseMapper<EnsureGoods> {

	/**
	 * 通过spuID，查询商品保障ID
	 *
	 * @param spuId
	 * @return
	 */
	List<String> listEnsureIdsBySpuId(String spuId);

	/**
	 * 通过spuID，查询商品保障
	 *
	 * @param spuId
	 * @return
	 */
	List<Ensure> listEnsureBySpuId(String spuId);
}
