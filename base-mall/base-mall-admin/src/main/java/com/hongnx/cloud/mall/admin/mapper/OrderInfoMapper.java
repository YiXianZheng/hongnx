package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * 商城订单
 *
 * @date 2019-09-10 15:21:22
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

	IPage<OrderInfo> selectPage1(IPage<OrderInfo> page, @Param("query") OrderInfo orderInfo);

	IPage<OrderInfo> selectPage2(IPage<OrderInfo> page, @Param("query") OrderInfo orderInfo);

	OrderInfo selectById2(Serializable id);
}
