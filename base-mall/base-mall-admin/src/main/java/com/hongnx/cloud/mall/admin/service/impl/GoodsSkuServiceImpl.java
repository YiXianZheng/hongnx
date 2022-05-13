package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.GoodsSkuMapper;
import com.hongnx.cloud.mall.admin.service.GoodsSkuService;
import com.hongnx.cloud.mall.common.entity.GoodsSku;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品sku
 *
 * @date 2019-08-13 10:18:34
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {

	@Override
	public List<GoodsSku> listGoodsSkuBySpuId(String spuId) {
		return baseMapper.listGoodsSkuBySpuId(spuId);
	}
}
