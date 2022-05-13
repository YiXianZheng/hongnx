package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.PointsRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 积分变动记录
 *
 * @date 2019-12-05 19:47:22
 */
public interface PointsRecordMapper extends BaseMapper<PointsRecord> {

	IPage<PointsRecord> selectPage1(IPage<PointsRecord> page, @Param("query") PointsRecord pointsRecord);
}
