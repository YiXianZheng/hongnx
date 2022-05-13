package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.DeliveryPlace;

/**
 * 发货地
 *
 * @date 2020-02-09 22:23:53
 */
public interface DeliveryPlaceMapper extends BaseMapper<DeliveryPlace> {

	DeliveryPlace getSpuDeliveryPlace(String spuId);
}
