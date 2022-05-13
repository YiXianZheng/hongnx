package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongnx.cloud.mall.common.entity.BargainInfo;
import com.hongnx.cloud.mall.common.entity.BargainUser;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 砍价
 *
 * @date 2019-12-28 18:07:51
 */
public interface BargainInfoMapper extends BaseMapper<BargainInfo> {
	BargainInfo selectById2(Serializable id);

	IPage<BargainInfo> selectPage2(IPage<BargainInfo> page, @Param("query") BargainInfo bargainInfo);

	BargainInfo selectOne2(@Param("query2") BargainUser bargainUser);
}
