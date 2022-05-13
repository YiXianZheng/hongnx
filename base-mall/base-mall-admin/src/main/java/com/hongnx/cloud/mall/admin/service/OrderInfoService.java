package com.hongnx.cloud.mall.admin.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.dto.PlaceOrderDTO;
import com.hongnx.cloud.mall.common.entity.OrderInfo;
import com.hongnx.cloud.mall.common.entity.OrderItem;
import com.hongnx.cloud.mall.common.entity.SeckillInfo;

import java.io.Serializable;

/**
 * 商城订单
 *
 * @date 2019-09-10 15:21:22
 */
public interface OrderInfoService extends IService<OrderInfo> {

	IPage<OrderInfo> page1(IPage<OrderInfo> page, Wrapper<OrderInfo> queryWrapper);

	/**
	 * 下单
	 * @param placeOrderDTO
	 */
	OrderInfo orderSub(PlaceOrderDTO placeOrderDTO, SeckillInfo seckillInfo);

	IPage<OrderInfo> page2(IPage<OrderInfo> page, OrderInfo orderInfo);

	OrderInfo getById2(Serializable id);

	/**
	 * 取消订单
	 * @param orderInfo
	 */
	void orderCancel(OrderInfo orderInfo);
	/**
	 * 订单收货
	 * @param orderInfo
	 */
	void orderReceive(OrderInfo orderInfo);

	/**
	 * 处理订单回调
	 * @param orderInfo
	 */
	void notifyOrder(OrderInfo orderInfo);

	/**
	 * 物流信息回调
	 * @param jsonObject
	 */
	void notifyLogisticsr(String logisticsId, JSONObject jsonObject);
	/**
	 * 修改商城订单价格
	 * @param orderItem
	 */
	void editPrice(OrderItem orderItem);
}
