package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.OrderRefunds;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 退款详情
 *
 * @date 2019-11-14 16:35:25
 */
public interface OrderRefundsMapper extends BaseMapper<OrderRefunds> {

	IPage<OrderRefunds> selectPage1(IPage<OrderRefunds> page, @Param("query") OrderRefunds orderRefunds);

	List<OrderRefunds> selectList2(OrderRefunds orderItem);
}
