package com.hongnx.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.mall.common.entity.BargainCut;

import java.math.BigDecimal;

/**
 * 砍价帮砍记录
 *
 * @date 2019-12-31 12:34:28
 */
public interface BargainCutMapper extends BaseMapper<BargainCut> {

	/**
	 * 获取砍价已砍总金额
	 * @param bargainUserId
	 * @return
	 */
	BigDecimal getTotalCutPrice(String bargainUserId);
}
