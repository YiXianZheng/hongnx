package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.SeckillInfoMapper;
import com.hongnx.cloud.mall.admin.service.SeckillHallInfoService;
import com.hongnx.cloud.mall.admin.service.SeckillInfoService;
import com.hongnx.cloud.mall.common.entity.SeckillHallInfo;
import com.hongnx.cloud.mall.common.entity.SeckillInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 秒杀商品
 *
 * @date 2020-08-12 11:03:50
 */
@Service
@AllArgsConstructor
public class SeckillInfoServiceImpl extends ServiceImpl<SeckillInfoMapper, SeckillInfo> implements SeckillInfoService {

	private final SeckillHallInfoService seckillHallInfoService;

	@Override
	public IPage<SeckillInfo> page2(IPage<SeckillInfo> page, SeckillInfo seckillInfo, SeckillHallInfo seckillHallInfo) {
		return baseMapper.selectPage2(page, seckillInfo, seckillHallInfo);
	}

	@Override
	public SeckillInfo getById2(String id) {
		return baseMapper.selectById2(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		//先删除关联商品
		seckillHallInfoService.remove(Wrappers.<SeckillHallInfo>lambdaQuery()
				.eq(SeckillHallInfo::getSeckillInfoId,id));
		return super.removeById(id);
	}
}
