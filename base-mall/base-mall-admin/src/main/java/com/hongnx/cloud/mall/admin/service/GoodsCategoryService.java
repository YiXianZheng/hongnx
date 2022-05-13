package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.GoodsCategory;
import com.hongnx.cloud.mall.common.entity.GoodsCategoryTree;

import java.util.List;

/**
 * 商品类目
 *
 * @date 2019-08-12 11:46:28
 */
public interface GoodsCategoryService extends IService<GoodsCategory> {

	/**
	 * 查询类目树
	 *
	 * @return 树
	 */
	List<GoodsCategoryTree> selectTree(GoodsCategory goodsCategory);
}
