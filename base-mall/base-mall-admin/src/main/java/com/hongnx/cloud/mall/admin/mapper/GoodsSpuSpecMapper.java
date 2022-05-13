package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.GoodsSpec;
import com.hongnx.cloud.mall.common.entity.GoodsSpuSpec;

import java.util.List;

/**
 * spu规格
 *
 * @date 2019-08-13 16:56:46
 */
public interface GoodsSpuSpecMapper extends BaseMapper<GoodsSpuSpec> {

	List<GoodsSpec> selectTree(String spuId);
}
