package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.UserFootprint;
import org.apache.ibatis.annotations.Param;

/**
 * 用户足迹
 *
 * @date 2020-12-24 22:15:45
 */
public interface UserFootprintMapper extends BaseMapper<UserFootprint> {

	IPage<UserFootprint> selectPage1(IPage<UserFootprint> page, @Param("query") UserFootprint userFootprint);
	IPage<UserFootprint> selectPage2(IPage<UserFootprint> page, @Param("query") UserFootprint userFootprint);
}
