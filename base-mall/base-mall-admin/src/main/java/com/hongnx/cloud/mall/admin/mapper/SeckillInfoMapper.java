package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.GoodsSpu;
import com.hongnx.cloud.mall.common.entity.SeckillHallInfo;
import com.hongnx.cloud.mall.common.entity.SeckillInfo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 秒杀商品
 *
 * @date 2020-08-12 11:03:50
 */
public interface SeckillInfoMapper extends BaseMapper<SeckillInfo> {

	IPage<SeckillInfo> selectPage2(IPage<SeckillInfo> page, @Param("query") SeckillInfo seckillInfo, @Param("query2") SeckillHallInfo seckillHallInfo);

	SeckillInfo selectById2(Serializable id);

	/**
	 * 查询秒杀会场的关联商品
	 * @param seckillHallId
	 * @return
	 */
	List<GoodsSpu> selectListBySeckillHallId(String seckillHallId);
}
