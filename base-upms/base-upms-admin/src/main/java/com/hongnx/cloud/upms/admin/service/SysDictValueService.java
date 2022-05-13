package com.hongnx.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongnx.cloud.upms.common.entity.SysDictValue;
import com.hongnx.cloud.common.core.util.R;

/**
 * 字典项
 */
public interface SysDictValueService extends IService<SysDictValue> {

	/**
	 * 删除字典项
	 *
	 * @param id 字典项ID
	 * @return
	 */
	R removeDictItem(String id);

	/**
	 * 更新字典项
	 *
	 * @param item 字典项
	 * @return
	 */
	R updateDictItem(SysDictValue item);
}
