package com.hongnx.cloud.upms.common.dto;

import com.hongnx.cloud.upms.common.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
	/**
	 * 角色ID
	 */
	private List<String> roleIds;

	private String organId;

	/**
	 * 新密码
	 */
	private String newpassword1;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 操作类型
	 */
	private String doType;
}
