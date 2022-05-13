package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.NoticeItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商城通知详情
 *
 * @date 2019-11-09 19:39:03
 */
public interface NoticeItemMapper extends BaseMapper<NoticeItem> {

	List<NoticeItem> selectList2(@Param("query") NoticeItem queryWrapper);
}
