package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.mall.common.entity.BargainCut;

import java.math.BigDecimal;

/**
 * 砍价帮砍记录
 *
 * @date 2019-12-31 12:34:28
 */
public interface BargainCutService extends IService<BargainCut> {

	/**
	 * 获取砍价已砍总金额
	 * @param bargainUserId
	 * @return
	 */
	BigDecimal getTotalCutPrice(String bargainUserId);

	/**
	 * 砍一刀
	 * @param bargainCut
	 * @return
	 */
	boolean save2(BargainCut bargainCut);
}
