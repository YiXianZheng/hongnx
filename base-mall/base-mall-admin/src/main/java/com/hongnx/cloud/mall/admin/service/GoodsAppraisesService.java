package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.GoodsAppraises;

import java.util.List;

/**
 * 商品评价
 *
 * @date 2019-09-23 15:51:01
 */
public interface GoodsAppraisesService extends IService<GoodsAppraises> {

	/**
	 * 发表评价
	 * @param listGoodsAppraises
	 */
	void doAppraises(List<GoodsAppraises> listGoodsAppraises);

	IPage<GoodsAppraises> page1(IPage<GoodsAppraises> page, GoodsAppraises godsAppraises);
}
