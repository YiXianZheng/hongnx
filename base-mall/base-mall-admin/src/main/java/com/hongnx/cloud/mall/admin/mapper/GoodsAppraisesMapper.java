package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.GoodsAppraises;
import org.apache.ibatis.annotations.Param;

/**
 * 商品评价
 *
 * @date 2019-09-23 15:51:01
 */
public interface GoodsAppraisesMapper extends BaseMapper<GoodsAppraises> {

	IPage<GoodsAppraises> selectPage1(IPage<GoodsAppraises> page, @Param("query") GoodsAppraises goodsAppraises);
}
