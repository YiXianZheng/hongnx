package com.hongnx.cloud.weixin.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.weixin.admin.mapper.WxMsgMapper;
import com.hongnx.cloud.weixin.admin.service.WxMsgService;
import com.hongnx.cloud.weixin.common.entity.WxMsg;
import com.hongnx.cloud.weixin.common.entity.WxMsgVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信消息
 *
 * @date 2019-05-28 16:12:10
 */
@Service
public class WxMsgServiceImpl extends ServiceImpl<WxMsgMapper, WxMsg> implements WxMsgService {

	@Override
	public IPage<List<WxMsgVO>> listWxMsgMapGroup(Page page, WxMsgVO wxMsgVO) {
		return baseMapper.listWxMsgMapGroup(page, wxMsgVO);
	}
}
