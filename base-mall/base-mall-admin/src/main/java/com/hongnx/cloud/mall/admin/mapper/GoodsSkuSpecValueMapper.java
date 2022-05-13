package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.GoodsSkuSpecValue;
import com.hongnx.cloud.mall.common.entity.GoodsSpuSpec;
import com.hongnx.cloud.mall.common.entity.GoodsSpec;

import java.util.List;

/**
 * 商品sku规格值
 *
 * @date 2019-08-13 10:19:09
 */
public interface GoodsSkuSpecValueMapper extends BaseMapper<GoodsSkuSpecValue> {

	List<GoodsSkuSpecValue> listGoodsSkuSpecValueBySkuId(String skuId);

	List<GoodsSpec> selectBySpecId(GoodsSpuSpec goodsSpuSpec);
}
