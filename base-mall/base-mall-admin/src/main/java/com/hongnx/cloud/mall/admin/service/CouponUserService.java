package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.CouponInfo;
import com.hongnx.cloud.mall.common.entity.CouponUser;

/**
 * 电子券用户记录
 *
 * @date 2019-12-17 10:43:53
 */
public interface CouponUserService extends IService<CouponUser> {

	IPage<CouponUser> page1(IPage<CouponUser> page, CouponUser couponUser);

	IPage<CouponUser> page2(IPage<CouponUser> page, CouponUser couponUser);

	/**
	 * 领券
	 * @param couponUser
	 */
	Boolean receiveCoupon(CouponUser couponUser, CouponInfo cuponInfo);
}
