package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.BargainUser;

/**
 * 砍价记录
 *
 * @date 2019-12-30 11:53:14
 */
public interface BargainUserService extends IService<BargainUser> {

	/**
	 * 发起砍价
	 * @param bargainUser
	 * @return
	 */
	boolean saveBargain(BargainUser bargainUser);

	IPage<BargainUser> page1(IPage<BargainUser> page, BargainUser bargainUser);

	IPage<BargainUser> page2(IPage<BargainUser> page, BargainUser bargainUser);
}
