package com.hongnx.cloud.upms.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongnx.cloud.upms.common.entity.SysOrganRelation;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 */
public interface SysOrganRelationMapper extends BaseMapper<SysOrganRelation> {
	/**
	 * 删除机构关系表数据
	 *
	 * @param id 机构ID
	 */
	void deleteOrganRelationsById(String id);

	/**
	 * 更改机构关系表数据
	 *
	 * @param sysOrganRelation
	 */
	void updateOrganRelations(SysOrganRelation sysOrganRelation);

}
