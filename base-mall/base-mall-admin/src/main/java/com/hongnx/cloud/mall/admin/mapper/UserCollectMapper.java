package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.UserCollect;
import org.apache.ibatis.annotations.Param;

/**
 * 用户收藏
 *
 * @date 2019-09-22 20:45:45
 */
public interface UserCollectMapper extends BaseMapper<UserCollect> {

	IPage<UserCollect> selectPage1(IPage<UserCollect> page, @Param("query") UserCollect userCollect);

	IPage<UserCollect> selectPage2(IPage<UserCollect> page,  @Param("query") UserCollect userCollect);

	String selectCollectId(@Param("query") UserCollect userCollect);
}
