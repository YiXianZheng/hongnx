package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.BargainInfoMapper;
import com.hongnx.cloud.mall.admin.service.BargainInfoService;
import com.hongnx.cloud.mall.common.entity.BargainInfo;
import com.hongnx.cloud.mall.common.entity.BargainUser;
import org.springframework.stereotype.Service;

/**
 * 砍价
 *
 * @date 2019-12-28 18:07:51
 */
@Service
public class BargainInfoServiceImpl extends ServiceImpl<BargainInfoMapper, BargainInfo> implements BargainInfoService {

	@Override
	public IPage<BargainInfo> page2(IPage<BargainInfo> page, BargainInfo brgainInfo) {
		return baseMapper.selectPage2(page, brgainInfo);
	}

	@Override
	public BargainInfo getOne2(BargainUser bargainUser) {
		return baseMapper.selectOne2(bargainUser);
	}
}
