package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.CouponUser;
import org.apache.ibatis.annotations.Param;

/**
 * 电子券用户记录
 *
 * @date 2019-12-17 10:43:53
 */
public interface CouponUserMapper extends BaseMapper<CouponUser> {

	IPage<CouponUser> selectPage1(IPage<CouponUser> page, @Param("query") CouponUser couponUser);

	IPage<CouponUser> selectPage2(IPage<CouponUser> page, @Param("query") CouponUser couponUser);
}
