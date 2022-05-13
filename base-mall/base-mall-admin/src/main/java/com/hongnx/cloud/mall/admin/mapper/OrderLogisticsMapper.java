package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.OrderLogistics;

import java.io.Serializable;

/**
 * 订单物流
 *
 * @date 2019-09-16 09:53:17
 */
public interface OrderLogisticsMapper extends BaseMapper<OrderLogistics> {

	OrderLogistics selectById2(Serializable id);
}
