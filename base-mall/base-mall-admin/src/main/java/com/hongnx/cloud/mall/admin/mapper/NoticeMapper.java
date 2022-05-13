package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.Notice;
import org.apache.ibatis.annotations.Param;

/**
 * 商城通知
 *
 * @date 2019-11-09 19:37:56
 */
public interface NoticeMapper extends BaseMapper<Notice> {
	Notice getOne2(@Param("query") Notice notice);
}
