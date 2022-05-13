package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.OrderLogisticsDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流详情
 *
 * @date 2019-09-21 12:39:00
 */
public interface OrderLogisticsDetailMapper extends BaseMapper<OrderLogisticsDetail> {

	List<OrderLogisticsDetail> selectList2(@Param("query") OrderLogisticsDetail queryWrapper);
}
