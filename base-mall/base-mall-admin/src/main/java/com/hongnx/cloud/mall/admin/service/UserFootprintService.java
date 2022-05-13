package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.UserFootprint;

/**
 * 用户足迹
 *
 * @date 2020-12-24 22:15:45
 */
public interface UserFootprintService extends IService<UserFootprint> {
	IPage<UserFootprint> page1(IPage<UserFootprint> page, UserFootprint userFootprint);
	IPage<UserFootprint> page2(IPage<UserFootprint> page, UserFootprint userFootprint);
}
