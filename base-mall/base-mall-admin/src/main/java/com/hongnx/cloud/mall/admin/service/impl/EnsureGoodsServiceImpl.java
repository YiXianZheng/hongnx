package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.EnsureGoodsMapper;
import com.hongnx.cloud.mall.admin.service.EnsureGoodsService;
import com.hongnx.cloud.mall.common.entity.Ensure;
import com.hongnx.cloud.mall.common.entity.EnsureGoods;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品保障
 *
 * @date 2020-02-10 00:02:09
 */
@Service
public class EnsureGoodsServiceImpl extends ServiceImpl<EnsureGoodsMapper, EnsureGoods> implements EnsureGoodsService {

	@Override
	public List<Ensure> listEnsureBySpuId(String spuId) {
		return baseMapper.listEnsureBySpuId(spuId);
	}
}
