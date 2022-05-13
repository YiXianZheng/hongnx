package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.GrouponInfo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * 拼团
 *
 * @date 2020-03-17 11:55:32
 */
public interface GrouponInfoMapper extends BaseMapper<GrouponInfo> {

	IPage<GrouponInfo> selectPage2(IPage<GrouponInfo> page, @Param("query") GrouponInfo grouponInfo);

	GrouponInfo selectById2(Serializable id);
}
