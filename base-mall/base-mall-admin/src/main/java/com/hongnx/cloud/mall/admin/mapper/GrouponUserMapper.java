package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.GrouponUser;
import org.apache.ibatis.annotations.Param;

/**
 * 拼团记录
 *
 * @date 2020-03-17 12:01:53
 */
public interface GrouponUserMapper extends BaseMapper<GrouponUser> {

	IPage<GrouponUser> selectPage1(IPage<GrouponUser> page, @Param("query") GrouponUser grouponUser);

	IPage<GrouponUser> selectPage2(IPage<GrouponUser> page, @Param("query") GrouponUser grouponUser);

	IPage<GrouponUser> selectPageGrouponing(IPage<GrouponUser> page, @Param("query") GrouponUser grouponUser);

	Integer selectCountGrouponing(String groupId);
}
