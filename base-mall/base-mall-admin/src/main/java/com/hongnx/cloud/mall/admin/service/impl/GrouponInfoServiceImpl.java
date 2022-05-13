package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.GrouponInfoMapper;
import com.hongnx.cloud.mall.admin.service.GrouponInfoService;
import com.hongnx.cloud.mall.common.entity.GrouponInfo;
import org.springframework.stereotype.Service;

/**
 * 拼团
 *
 * @date 2020-03-17 11:55:32
 */
@Service
public class GrouponInfoServiceImpl extends ServiceImpl<GrouponInfoMapper, GrouponInfo> implements GrouponInfoService {

	@Override
	public IPage<GrouponInfo> page2(IPage<GrouponInfo> page, GrouponInfo grouponInfo) {
		return baseMapper.selectPage2(page, grouponInfo);
	}

	@Override
	public GrouponInfo getById2(String id) {
		return baseMapper.selectById2(id);
	}
}
