package com.hongnx.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.mall.admin.mapper.GoodsSpecMapper;
import com.hongnx.cloud.mall.admin.mapper.GoodsSpecValueMapper;
import com.hongnx.cloud.mall.admin.service.GoodsSpecService;
import com.hongnx.cloud.mall.common.entity.GoodsSpec;
import com.hongnx.cloud.mall.common.entity.GoodsSpecValue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 规格
 *
 * @date 2019-08-13 16:10:54
 */
@Service
@AllArgsConstructor
public class GoodsSpecServiceImpl extends ServiceImpl<GoodsSpecMapper, GoodsSpec> implements GoodsSpecService {

	private final GoodsSpecValueMapper goodsSpecValueMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		goodsSpecValueMapper.delete(Wrappers.<GoodsSpecValue>update().lambda()
				.eq(GoodsSpecValue::getSpecId, id));
		return super.removeById(id);
	}
}
