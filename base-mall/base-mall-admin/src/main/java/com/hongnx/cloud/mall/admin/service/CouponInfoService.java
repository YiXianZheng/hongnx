package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.CouponGoods;
import com.hongnx.cloud.mall.common.entity.CouponInfo;
import com.hongnx.cloud.mall.common.entity.CouponUser;

import java.io.Serializable;

/**
 * 电子券
 *
 * @date 2019-12-14 11:30:58
 */
public interface CouponInfoService extends IService<CouponInfo> {

	boolean updateById1(CouponInfo entity);

	CouponInfo getById2(Serializable id);

	IPage<CouponInfo> page2(IPage<CouponInfo> page, CouponInfo couponInfo, CouponGoods cuponGoods, CouponUser couponUser);
}
