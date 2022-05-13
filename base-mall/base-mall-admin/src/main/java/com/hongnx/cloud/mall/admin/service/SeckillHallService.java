package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.SeckillHall;

import java.io.Serializable;

/**
 * 秒杀会场
 *
 * @date 2020-08-11 16:38:56
 */
public interface SeckillHallService extends IService<SeckillHall> {

	boolean updateById1(SeckillHall entity);

	SeckillHall getById2(Serializable id);
}
