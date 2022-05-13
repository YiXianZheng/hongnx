package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.SeckillHallInfo;
import com.hongnx.cloud.mall.common.entity.SeckillInfo;

/**
 * 秒杀商品
 *
 * @date 2020-08-12 11:03:50
 */
public interface SeckillInfoService extends IService<SeckillInfo> {

	IPage<SeckillInfo> page2(IPage<SeckillInfo> page, SeckillInfo seckillInfo, SeckillHallInfo seckillHallInfo);

	SeckillInfo getById2(String id);
}
