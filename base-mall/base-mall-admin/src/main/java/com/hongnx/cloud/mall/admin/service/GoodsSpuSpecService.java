package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.GoodsSpuSpec;
import com.hongnx.cloud.mall.common.entity.GoodsSpec;

import java.util.List;

/**
 * spu规格
 *
 * @date 2019-08-13 16:56:46
 */
public interface GoodsSpuSpecService extends IService<GoodsSpuSpec> {

	List<GoodsSpec> tree(GoodsSpuSpec goodsSpuSpec);
}
