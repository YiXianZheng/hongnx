package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.mall.admin.mapper.NoticeItemMapper;
import com.hongnx.cloud.mall.admin.mapper.NoticeMapper;
import com.hongnx.cloud.mall.admin.service.NoticeItemService;
import com.hongnx.cloud.mall.common.entity.Notice;
import com.hongnx.cloud.mall.common.entity.NoticeItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城通知详情
 *
 * @date 2019-11-09 19:39:03
 */
@Service
@AllArgsConstructor
public class NoticeItemServiceImpl extends ServiceImpl<NoticeItemMapper, NoticeItem> implements NoticeItemService {

	private final NoticeMapper noticeMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(NoticeItem entity) {
		Notice notice = new Notice();
		notice.setAppId(entity.getAppId());
		notice.setType(entity.getNoticeType());
		Notice notice2 = noticeMapper.selectOne(Wrappers.query(notice));
		if(notice2 == null){
			notice.setEnable(CommonConstants.YES);
			noticeMapper.insert(notice);
			entity.setNoticeId(notice.getId());
		}else{
			entity.setNoticeId(notice2.getId());
		}
		return super.save(entity);
	}
}
