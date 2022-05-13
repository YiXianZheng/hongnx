package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.CouponUserMapper;
import com.hongnx.cloud.mall.admin.service.CouponInfoService;
import com.hongnx.cloud.mall.admin.service.CouponUserService;
import com.hongnx.cloud.mall.common.entity.CouponInfo;
import com.hongnx.cloud.mall.common.entity.CouponUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 电子券用户记录
 *
 * @date 2019-12-17 10:43:53
 */
@Service
@AllArgsConstructor
public class CouponUserServiceImpl extends ServiceImpl<CouponUserMapper, CouponUser> implements CouponUserService {

	private final CouponInfoService couponInfoService;

	@Override
	public IPage<CouponUser> page1(IPage<CouponUser> page, CouponUser couponUser) {
		return baseMapper.selectPage1(page, couponUser);
	}

	@Override
	public IPage<CouponUser> page2(IPage<CouponUser> page, CouponUser couponUser) {
		return baseMapper.selectPage2(page, couponUser);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean receiveCoupon(CouponUser couponUser, CouponInfo cuponInfo) {
		couponUser.setName(cuponInfo.getName());
		couponUser.setType(cuponInfo.getType());
		couponUser.setPremiseAmount(cuponInfo.getPremiseAmount());
		couponUser.setReduceAmount(cuponInfo.getReduceAmount());
		couponUser.setDiscount(cuponInfo.getDiscount());
		couponUser.setSuitType(cuponInfo.getSuitType());
		baseMapper.insert(couponUser);
		//更新电子券的库存，乐观锁处理
		cuponInfo.setStock(cuponInfo.getStock() - 1);
		if(!couponInfoService.updateById(cuponInfo)){
			throw new RuntimeException("请重新领取");
		}
		return Boolean.TRUE;
	}
}
