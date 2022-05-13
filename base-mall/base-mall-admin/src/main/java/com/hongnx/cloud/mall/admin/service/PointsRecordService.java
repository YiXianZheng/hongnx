package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.PointsRecord;

/**
 * 积分变动记录
 *
 * @date 2019-12-05 19:47:22
 */
public interface PointsRecordService extends IService<PointsRecord> {

	IPage<PointsRecord> page1(IPage<PointsRecord> page, PointsRecord pointsRecord);
}
