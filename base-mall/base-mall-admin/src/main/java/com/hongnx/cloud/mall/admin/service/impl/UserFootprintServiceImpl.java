package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.UserFootprintMapper;
import com.hongnx.cloud.mall.admin.service.UserFootprintService;
import com.hongnx.cloud.mall.common.entity.UserFootprint;
import org.springframework.stereotype.Service;

/**
 * 用户足迹
 *
 * @date 2020-12-24 22:15:45
 */
@Service
public class UserFootprintServiceImpl extends ServiceImpl<UserFootprintMapper, UserFootprint> implements UserFootprintService {
	@Override
	public IPage<UserFootprint> page1(IPage<UserFootprint> page, UserFootprint userFootprint) {
		return baseMapper.selectPage1(page,userFootprint);
	}
	@Override
	public IPage<UserFootprint> page2(IPage<UserFootprint> page, UserFootprint userFootprint) {
		return baseMapper.selectPage2(page,userFootprint);
	}
}
