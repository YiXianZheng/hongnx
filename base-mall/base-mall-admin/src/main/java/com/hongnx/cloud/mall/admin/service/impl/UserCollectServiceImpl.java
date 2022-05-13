package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.UserCollectMapper;
import com.hongnx.cloud.mall.admin.service.UserCollectService;
import com.hongnx.cloud.mall.common.entity.UserCollect;
import org.springframework.stereotype.Service;

/**
 * 用户收藏
 *
 * @date 2019-09-22 20:45:45
 */
@Service
public class UserCollectServiceImpl extends ServiceImpl<UserCollectMapper, UserCollect> implements UserCollectService {

	@Override
	public IPage<UserCollect> page1(IPage<UserCollect> page, UserCollect userCollect) {
		return baseMapper.selectPage1(page,userCollect);
	}

	@Override
	public IPage<UserCollect> page2(IPage<UserCollect> page, Wrapper<UserCollect> queryWrapper) {
		return baseMapper.selectPage2(page, queryWrapper.getEntity());
	}
}
