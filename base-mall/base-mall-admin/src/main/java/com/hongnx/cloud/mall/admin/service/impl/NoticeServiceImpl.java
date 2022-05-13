package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.NoticeMapper;
import com.hongnx.cloud.mall.admin.service.NoticeService;
import com.hongnx.cloud.mall.common.entity.Notice;
import org.springframework.stereotype.Service;

/**
 * 商城通知
 *
 * @date 2019-11-09 19:37:56
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {


	@Override
	public Notice getOne(Wrapper<Notice> queryWrapper) {
		return baseMapper.getOne2(queryWrapper.getEntity());
	}
}
