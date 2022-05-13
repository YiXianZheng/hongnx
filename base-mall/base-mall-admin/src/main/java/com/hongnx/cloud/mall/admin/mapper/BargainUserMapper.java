package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.BargainUser;
import org.apache.ibatis.annotations.Param;

/**
 * 砍价记录
 *
 * @date 2019-12-30 11:53:14
 */
public interface BargainUserMapper extends BaseMapper<BargainUser> {

	IPage<BargainUser> selectPage1(IPage<BargainUser> page, @Param("query") BargainUser bargainUser);

	IPage<BargainUser> selectPage2(IPage<BargainUser> page, @Param("query") BargainUser bargainUser);
}
