package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.OrderRefunds;

/**
 * 退款详情
 *
 * @date 2019-11-14 16:35:25
 */
public interface OrderRefundsService extends IService<OrderRefunds> {

	/**
	 * 申请退款
	 * @param entity
	 * @return
	 */
	boolean saveRefunds(OrderRefunds entity);

	/**
	 * 操作退款
	 * @param entity
	 * @return
	 */
	boolean doOrderRefunds(OrderRefunds entity);

	/**
	 * 处理退款回调
	 * @param orderRefunds
	 */
	void notifyRefunds(OrderRefunds orderRefunds);

	IPage<OrderRefunds> page1(IPage<OrderRefunds> page, OrderRefunds orderRefunds);
}
