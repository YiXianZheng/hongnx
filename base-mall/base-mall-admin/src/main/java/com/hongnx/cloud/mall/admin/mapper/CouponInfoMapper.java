package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.CouponGoods;
import com.hongnx.cloud.mall.common.entity.CouponInfo;
import com.hongnx.cloud.mall.common.entity.CouponUser;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * 电子券
 *
 * @date 2019-12-14 11:30:58
 */
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

	CouponInfo selectById2(Serializable id);

	IPage<CouponInfo> selectPage2(IPage<CouponInfo> page, @Param("query") CouponInfo couponInfo, @Param("query2") CouponGoods cuponGoods, @Param("query3") CouponUser couponUser);
}
