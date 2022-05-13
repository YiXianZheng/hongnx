package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.GoodsSpuSpecMapper;
import com.hongnx.cloud.mall.admin.service.GoodsSpuSpecService;
import com.hongnx.cloud.mall.common.entity.GoodsSpuSpec;
import com.hongnx.cloud.mall.common.entity.GoodsSpec;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spu规格
 *
 * @date 2019-08-13 16:56:46
 */
@Service
public class GoodsSpuSpecServiceImpl extends ServiceImpl<GoodsSpuSpecMapper, GoodsSpuSpec> implements GoodsSpuSpecService {

	@Override
	public List<GoodsSpec> tree(GoodsSpuSpec goodsSpuSpec) {
		return baseMapper.selectTree(goodsSpuSpec.getSpuId());
	}
}
