package com.hongnx.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.common.dto.WxOpenDataDTO;
import com.hongnx.cloud.mall.common.entity.UserInfo;

/**
 * 商城用户
 *
 * @date 2019-12-04 11:09:55
 */
public interface UserInfoService extends IService<UserInfo> {

	/**
	 * 通过微信用户增加商城用户
	 * @param wxOpenDataDTO
	 * @return
	 */
	R saveByWxUser(WxOpenDataDTO wxOpenDataDTO);

	/**
	 * 新增商城用户（供服务间调用）
	 * @param userInfo
	 * @return
	 */
	boolean saveInside(UserInfo userInfo);
}
