package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.GrouponUser;

/**
 * 拼团记录
 *
 * @date 2020-03-17 12:01:53
 */
public interface GrouponUserService extends IService<GrouponUser> {

	IPage<GrouponUser> page1(IPage<GrouponUser> page, GrouponUser grouponUser);

	IPage<GrouponUser> page2(IPage<GrouponUser> page, GrouponUser grouponUser);

	IPage<GrouponUser> getPageGrouponing(IPage<GrouponUser> page, GrouponUser grouponUser);

	Integer selectCountGrouponing(String groupId);
}
