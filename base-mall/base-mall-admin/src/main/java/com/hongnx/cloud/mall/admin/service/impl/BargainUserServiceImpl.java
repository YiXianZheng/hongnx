package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.mall.admin.mapper.BargainInfoMapper;
import com.hongnx.cloud.mall.admin.mapper.BargainUserMapper;
import com.hongnx.cloud.mall.admin.service.BargainCutService;
import com.hongnx.cloud.mall.admin.service.BargainUserService;
import com.hongnx.cloud.mall.common.entity.BargainCut;
import com.hongnx.cloud.mall.common.entity.BargainInfo;
import com.hongnx.cloud.mall.common.entity.BargainUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 砍价记录
 *
 * @date 2019-12-30 11:53:14
 */
@Service
@AllArgsConstructor
public class BargainUserServiceImpl extends ServiceImpl<BargainUserMapper, BargainUser> implements BargainUserService {

	private final BargainInfoMapper bargainInfoMapper;
	private final BargainCutService bargainCutService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveBargain(BargainUser bargainUser) {
		//获取砍价详情
		BargainInfo bargainInfo = bargainInfoMapper.selectOne(Wrappers.<BargainInfo>lambdaQuery()
				.eq(BargainInfo::getId,bargainUser.getBargainId())
				.eq(BargainInfo::getEnable, CommonConstants.YES));
		if(bargainInfo != null && LocalDateTime.now().isAfter(bargainInfo.getValidBeginTime())
				&& LocalDateTime.now().isBefore(bargainInfo.getValidEndTime())){
			bargainUser.setSpuId(bargainInfo.getSpuId());
			bargainUser.setSkuId(bargainInfo.getSkuId());
			bargainUser.setValidBeginTime(bargainInfo.getValidBeginTime());
			bargainUser.setValidEndTime(bargainInfo.getValidEndTime());
			bargainUser.setBargainPrice(bargainInfo.getBargainPrice());
			bargainUser.setFloorBuy(bargainInfo.getFloorBuy());
			baseMapper.insert(bargainUser);
			if(CommonConstants.YES.equals(bargainInfo.getSelfCut())){//是否自砍一刀
				BargainCut bargainCut = new BargainCut();
				bargainCut.setUserId(bargainUser.getUserId());
				bargainCut.setBargainUserId(bargainUser.getId());
				bargainCutService.save2(bargainCut);//自砍一刀
			}
			bargainInfo.setLaunchNum(bargainInfo.getLaunchNum()+1);
			bargainInfoMapper.updateById(bargainInfo);
		}
		return Boolean.TRUE;
	}

	@Override
	public IPage<BargainUser> page1(IPage<BargainUser> page, BargainUser bargainUser) {
		return baseMapper.selectPage1(page, bargainUser);
	}

	@Override
	public IPage<BargainUser> page2(IPage<BargainUser> page, BargainUser bargainUser) {
		return baseMapper.selectPage2(page, bargainUser);
	}

}
