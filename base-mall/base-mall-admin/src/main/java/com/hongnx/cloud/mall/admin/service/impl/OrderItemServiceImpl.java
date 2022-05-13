package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.OrderItemMapper;
import com.hongnx.cloud.mall.admin.service.OrderItemService;
import com.hongnx.cloud.mall.common.entity.OrderItem;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 商城订单详情
 *
 * @date 2019-09-10 15:31:40
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

	@Override
	public OrderItem getById2(Serializable id) {
		return baseMapper.selectById2(id);
	}
}
