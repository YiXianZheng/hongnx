package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.GoodsAppraisesMapper;
import com.hongnx.cloud.mall.admin.service.GoodsAppraisesService;
import com.hongnx.cloud.mall.admin.service.OrderInfoService;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.entity.GoodsAppraises;
import com.hongnx.cloud.mall.common.entity.OrderInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品评价
 *
 * @date 2019-09-23 15:51:01
 */
@Service
@AllArgsConstructor
public class GoodsAppraisesServiceImpl extends ServiceImpl<GoodsAppraisesMapper, GoodsAppraises> implements GoodsAppraisesService {
	private final OrderInfoService orderInfoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void doAppraises(List<GoodsAppraises> listGoodsAppraises) {
		super.saveBatch(listGoodsAppraises);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(listGoodsAppraises.get(0).getOrderId());
		orderInfo.setAppraisesStatus(MallConstants.APPRAISES_STATUS_1);
		orderInfo.setClosingTime(LocalDateTime.now());
		orderInfoService.updateById(orderInfo);
	}

	@Override
	public IPage<GoodsAppraises> page1(IPage<GoodsAppraises> page, GoodsAppraises godsAppraises) {
		return baseMapper.selectPage1(page,godsAppraises);
	}
}
