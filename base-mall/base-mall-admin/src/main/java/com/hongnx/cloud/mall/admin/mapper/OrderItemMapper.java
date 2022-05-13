package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.OrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * 商城订单详情
 *
 * @date 2019-09-10 15:31:40
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

	List<OrderItem> selectList2(OrderItem orderItem);

	OrderItem selectById2(Serializable id);
}
