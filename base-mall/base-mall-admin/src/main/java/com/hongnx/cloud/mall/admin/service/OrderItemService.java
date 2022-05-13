package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.OrderItem;

import java.io.Serializable;

/**
 * 商城订单详情
 *
 * @date 2019-09-10 15:31:40
 */
public interface OrderItemService extends IService<OrderItem> {

	OrderItem getById2(Serializable id);
}
