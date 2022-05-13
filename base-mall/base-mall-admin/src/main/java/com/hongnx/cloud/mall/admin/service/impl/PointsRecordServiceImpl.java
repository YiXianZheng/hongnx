package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.PointsRecordMapper;
import com.hongnx.cloud.mall.admin.service.PointsRecordService;
import com.hongnx.cloud.mall.common.entity.PointsRecord;
import org.springframework.stereotype.Service;

/**
 * 积分变动记录
 *
 * @date 2019-12-05 19:47:22
 */
@Service
public class PointsRecordServiceImpl extends ServiceImpl<PointsRecordMapper, PointsRecord> implements PointsRecordService {

	@Override
	public IPage<PointsRecord> page1(IPage<PointsRecord> page, PointsRecord pointsRecord) {
		return baseMapper.selectPage1(page,pointsRecord);
	}
}
