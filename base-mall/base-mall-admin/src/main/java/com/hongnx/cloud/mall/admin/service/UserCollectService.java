package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.UserCollect;

/**
 * 用户收藏
 *
 * @date 2019-09-22 20:45:45
 */
public interface UserCollectService extends IService<UserCollect> {

	IPage<UserCollect> page1(IPage<UserCollect> page, UserCollect userCollect);

	IPage<UserCollect> page2(IPage<UserCollect> page, Wrapper<UserCollect> queryWrapper);
}
