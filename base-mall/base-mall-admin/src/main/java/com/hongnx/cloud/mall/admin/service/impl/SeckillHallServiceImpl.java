package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.SeckillHallMapper;
import com.hongnx.cloud.mall.admin.service.SeckillHallInfoService;
import com.hongnx.cloud.mall.admin.service.SeckillHallService;
import com.hongnx.cloud.mall.admin.service.SeckillInfoService;
import com.hongnx.cloud.mall.common.entity.SeckillHall;
import com.hongnx.cloud.mall.common.entity.SeckillHallInfo;
import com.hongnx.cloud.mall.common.entity.SeckillInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 秒杀会场
 *
 * @date 2020-08-11 16:38:56
 */
@Service
@AllArgsConstructor
public class SeckillHallServiceImpl extends ServiceImpl<SeckillHallMapper, SeckillHall> implements SeckillHallService {

	private final SeckillHallInfoService seckillHallInfoService;
	private final SeckillInfoService seckillInfoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(SeckillHall entity) {
		super.save(entity);
		doSeckillHallInfo(entity);
		return Boolean.TRUE;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById1(SeckillHall entity) {
		super.updateById(entity);
		if(entity.getListSeckillInfo() != null){
			//先删除关联商品
			seckillHallInfoService.remove(Wrappers.<SeckillHallInfo>lambdaQuery()
					.eq(SeckillHallInfo::getSeckillHallId,entity.getId()));
			doSeckillHallInfo(entity);
		}
		return Boolean.TRUE;
	}

	/**
	 * 批量添加关联秒杀商品
	 * @param entity
	 */
	protected void doSeckillHallInfo(SeckillHall entity){
		List<SeckillInfo> listSeckillInfo = entity.getListSeckillInfo();
		List<SeckillHallInfo> listSeckillHallInfo = new ArrayList<>();
		if(listSeckillInfo != null && listSeckillInfo.size() > 0){
			listSeckillInfo.forEach(seckillInfo -> {
				SeckillHallInfo seckillHallInfo = new SeckillHallInfo();
				seckillHallInfo.setSeckillHallId(entity.getId());
				seckillHallInfo.setSeckillInfoId(seckillInfo.getId());
				listSeckillHallInfo.add(seckillHallInfo);
			});
			//添加关联商品
			seckillHallInfoService.saveBatch(listSeckillHallInfo);
		}
	}

	@Override
	public boolean removeById(Serializable id) {
		//先删除关联商品
		seckillHallInfoService.remove(Wrappers.<SeckillHallInfo>lambdaQuery()
				.eq(SeckillHallInfo::getSeckillHallId,id));
		return super.removeById(id);
	}

	@Override
	public SeckillHall getById2(Serializable id) {
		return baseMapper.selectById2(id);
	}
}
