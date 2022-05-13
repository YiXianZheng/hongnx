package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.SeckillHall;

import java.io.Serializable;

/**
 * 秒杀会场
 *
 * @date 2020-08-11 16:38:56
 */
public interface SeckillHallMapper extends BaseMapper<SeckillHall> {

	SeckillHall selectById2(Serializable id);
}
