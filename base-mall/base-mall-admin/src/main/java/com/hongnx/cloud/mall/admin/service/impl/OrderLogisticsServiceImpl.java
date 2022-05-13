package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.OrderLogisticsMapper;
import com.hongnx.cloud.mall.admin.service.OrderLogisticsService;
import com.hongnx.cloud.mall.common.entity.OrderLogistics;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 订单物流
 *
 * @date 2019-09-16 09:53:17
 */
@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements OrderLogisticsService {

	@Override
	public OrderLogistics getById(Serializable id) {
		return baseMapper.selectById2(id);
	}
}
