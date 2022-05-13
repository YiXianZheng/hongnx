package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.BargainInfo;
import com.hongnx.cloud.mall.common.entity.BargainUser;

/**
 * 砍价
 *
 * @date 2019-12-28 18:07:51
 */
public interface BargainInfoService extends IService<BargainInfo> {
	IPage<BargainInfo> page2(IPage<BargainInfo> page, BargainInfo brgainInfo);

	BargainInfo getOne2(BargainUser bargainUser);
}
