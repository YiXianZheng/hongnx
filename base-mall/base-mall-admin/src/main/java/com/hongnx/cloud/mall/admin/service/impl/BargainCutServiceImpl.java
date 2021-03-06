package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.*;
import com.hongnx.cloud.mall.admin.service.BargainCutService;
import com.hongnx.cloud.mall.common.entity.*;
import com.hongnx.cloud.mall.admin.mapper.*;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 砍价帮砍记录
 *
 * @date 2019-12-31 12:34:28
 */
@Service
@AllArgsConstructor
public class BargainCutServiceImpl extends ServiceImpl<BargainCutMapper, BargainCut> implements BargainCutService {

	private final BargainInfoMapper bargainInfoMapper;
	private final BargainUserMapper bargainUserMapper;
	private final UserInfoMapper userInfoMapper;
	private final GoodsSkuMapper goodsSkuMapper;

	@Override
	public BigDecimal getTotalCutPrice(String bargainUserId) {
		return baseMapper.getTotalCutPrice(bargainUserId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save2(BargainCut bargainCut) {
		UserInfo userInfo = userInfoMapper.selectById(bargainCut.getUserId());
		BargainUser bargainUser = bargainUserMapper.selectById(bargainCut.getBargainUserId());
		BargainInfo bargainInfo = bargainInfoMapper.selectById(bargainUser.getBargainId());
		GoodsSku goodsSku = goodsSkuMapper.selectById(bargainUser.getSkuId());
		List<BargainCut> listBargainCut = baseMapper.selectList(Wrappers.<BargainCut>lambdaQuery()
				.eq(BargainCut::getBargainUserId,bargainCut.getBargainUserId())
				.eq(BargainCut::getUserId,bargainCut.getUserId()));
		if((listBargainCut == null || listBargainCut.size() <= 0)
				&& MallConstants.BARGAIN_USER_STATUS_0.equals(bargainUser.getStatus())){
			bargainCut.setBargainId(bargainUser.getBargainId());
			bargainCut.setBargainUserId(bargainUser.getId());
			bargainCut.setUserId(bargainCut.getUserId());
			bargainCut.setNickName(userInfo.getNickName());
			bargainCut.setHeadimgUrl(userInfo.getHeadimgUrl());
			//获取该砍价已砍总金额
			BigDecimal totalCutPrice = baseMapper.getTotalCutPrice(bargainUser.getId());
			//还需砍价金额
			BigDecimal needCutPrice = goodsSku.getSalesPrice().subtract(bargainUser.getBargainPrice()).subtract(totalCutPrice);
			//生成随机砍价金额
			float minF = bargainInfo.getCutMin().floatValue();
			float maxF = bargainInfo.getCutMax().floatValue();
			BigDecimal cutPrice = new BigDecimal(Math.random() * (maxF - minF) + minF);
			//判断砍完这刀后是否达到底价
			if(cutPrice.compareTo(needCutPrice) == 1){
				cutPrice = needCutPrice;
				//达到底价修改砍价记录状态
				bargainUser.setStatus(MallConstants.BARGAIN_USER_STATUS_1);
				bargainUserMapper.updateById(bargainUser);
			}
			bargainCut.setCutPrice(cutPrice);
			baseMapper.insert(bargainCut);
		}
		return Boolean.TRUE;
	}
}
