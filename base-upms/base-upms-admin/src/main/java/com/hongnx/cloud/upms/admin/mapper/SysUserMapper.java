package com.hongnx.cloud.upms.admin.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.upms.common.dto.UserDTO;
import com.hongnx.cloud.upms.common.entity.SysUser;
import com.hongnx.cloud.upms.common.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	/**
	 * 无租户查询
	 *
	 * @param sysUser
	 * @return SysUser
	 */
	@InterceptorIgnore(tenantLine="true")
	SysUser getByNoTenant(SysUser sysUser);

	/**
	 * 分页查询用户信息（含角色）
	 *
	 * @param page      分页
	 * @param userDTO   查询参数
	 * @return list
	 */
	IPage<List<UserVO>> getUserVosPage(Page page, @Param("query") UserDTO userDTO);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return userVo
	 */
	UserVO getUserVoById(String id);
}
