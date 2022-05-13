package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.GrouponUserMapper;
import com.hongnx.cloud.mall.admin.service.GrouponUserService;
import com.hongnx.cloud.mall.common.entity.GrouponUser;
import org.springframework.stereotype.Service;

/**
 * 拼团记录
 *
 * @date 2020-03-17 12:01:53
 */
@Service
public class GrouponUserServiceImpl extends ServiceImpl<GrouponUserMapper, GrouponUser> implements GrouponUserService {

	@Override
	public IPage<GrouponUser> page1(IPage<GrouponUser> page, GrouponUser grouponUser) {
		return baseMapper.selectPage1(page, grouponUser);
	}

	@Override
	public IPage<GrouponUser> page2(IPage<GrouponUser> page, GrouponUser grouponUser) {
		return baseMapper.selectPage2(page, grouponUser);
	}

	@Override
	public IPage<GrouponUser> getPageGrouponing(IPage<GrouponUser> page, GrouponUser grouponUser) {
		return baseMapper.selectPageGrouponing(page, grouponUser);
	}

	@Override
	public Integer selectCountGrouponing(String groupId) {
		return baseMapper.selectCountGrouponing(groupId);
	}
}
