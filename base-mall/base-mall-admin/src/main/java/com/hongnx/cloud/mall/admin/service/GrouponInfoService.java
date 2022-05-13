package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.GrouponInfo;

/**
 * 拼团
 *
 * @date 2020-03-17 11:55:32
 */
public interface GrouponInfoService extends IService<GrouponInfo> {

	IPage<GrouponInfo> page2(IPage<GrouponInfo> page, GrouponInfo grouponInfo);

	GrouponInfo getById2(String id);
}
