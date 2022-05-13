package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.CouponInfoMapper;
import com.hongnx.cloud.mall.admin.service.CouponGoodsService;
import com.hongnx.cloud.mall.admin.service.CouponInfoService;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.entity.CouponGoods;
import com.hongnx.cloud.mall.common.entity.CouponInfo;
import com.hongnx.cloud.mall.common.entity.CouponUser;
import com.hongnx.cloud.mall.common.entity.GoodsSpu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 电子券
 *
 * @date 2019-12-14 11:30:58
 */
@Service
@AllArgsConstructor
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

	private final CouponGoodsService couponGoodsService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(CouponInfo entity) {
		super.save(entity);
		if(MallConstants.COUPON_SUIT_TYPE_2.equals(entity.getSuitType())){//是否指定商品
			doCouponGoods(entity);
		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById1(CouponInfo entity) {
		super.updateById(entity);
		if(entity.getListGoodsSpu() != null) {
			//先删除关联商品
			couponGoodsService.remove(Wrappers.<CouponGoods>lambdaQuery()
					.eq(CouponGoods::getCouponId, entity.getId()));
			if (MallConstants.COUPON_SUIT_TYPE_2.equals(entity.getSuitType())) {//是否指定商品
				doCouponGoods(entity);
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * 批量添加关联商品
	 * @param entity
	 */
	protected void doCouponGoods(CouponInfo entity){
		List<GoodsSpu> listGoodsSpu = entity.getListGoodsSpu();
		List<CouponGoods> listCouponGoods = new ArrayList<>();
		if(listGoodsSpu != null && listGoodsSpu.size() > 0){
			listGoodsSpu.forEach(goodsSpu -> {
				CouponGoods couponGoods = new CouponGoods();
				couponGoods.setCouponId(entity.getId());
				couponGoods.setSpuId(goodsSpu.getId());
				listCouponGoods.add(couponGoods);
			});
			//添加关联商品
			couponGoodsService.saveBatch(listCouponGoods);
		}
	}

	@Override
	public CouponInfo getById2(Serializable id) {
		return baseMapper.selectById2(id);
	}

	@Override
	public IPage<CouponInfo> page2(IPage<CouponInfo> page, CouponInfo couponInfo, CouponGoods cuponGoods, CouponUser couponUser) {
		return baseMapper.selectPage2(page, couponInfo, cuponGoods, couponUser);
	}

	@Override
	public boolean removeById(Serializable id) {
		//先删除关联商品
		couponGoodsService.remove(Wrappers.<CouponGoods>lambdaQuery()
				.eq(CouponGoods::getCouponId,id));
		return super.removeById(id);
	}
}
