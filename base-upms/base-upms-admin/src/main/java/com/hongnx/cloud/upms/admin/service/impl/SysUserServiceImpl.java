package com.hongnx.cloud.upms.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.upms.admin.mapper.SysUserMapper;
import com.hongnx.cloud.upms.admin.service.SysOrganRelationService;
import com.hongnx.cloud.upms.admin.service.SysOrganService;
import com.hongnx.cloud.upms.admin.service.SysMenuService;
import com.hongnx.cloud.upms.admin.service.SysRoleMenuService;
import com.hongnx.cloud.upms.admin.service.SysRoleService;
import com.hongnx.cloud.upms.admin.service.SysUserRoleService;
import com.hongnx.cloud.upms.admin.service.SysUserService;
import com.hongnx.cloud.upms.common.dto.UserDTO;
import com.hongnx.cloud.upms.common.dto.UserInfo;
import com.hongnx.cloud.upms.common.dto.UserRegister;
import com.hongnx.cloud.upms.common.entity.SysOrgan;
import com.hongnx.cloud.upms.common.entity.SysRole;
import com.hongnx.cloud.upms.common.entity.SysRoleMenu;
import com.hongnx.cloud.upms.common.entity.SysUser;
import com.hongnx.cloud.upms.common.entity.SysUserRole;
import com.hongnx.cloud.upms.common.vo.MenuVO;
import com.hongnx.cloud.upms.common.vo.UserVO;
import com.hongnx.cloud.common.core.constant.CacheConstants;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final SysMenuService sysMenuService;
	private final SysRoleService sysRoleService;
	private final SysOrganService sysOrganService;
	private final SysUserRoleService sysUserRoleService;
	private final SysOrganRelationService sysOrganRelationService;
	private final SysRoleMenuService sysRoleMenuService;
	private final CacheManager cacheManager;

	/**
	 * ??????????????????
	 *
	 * @param userDto DTO ??????
	 * @return ok/fail
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		baseMapper.insert(sysUser);
		List<SysUserRole> userRoleList = userDto.getRoleIds()
				.stream().map(roleId -> {
					SysUserRole userRole = new SysUserRole();
					userRole.setUserId(sysUser.getId());
					userRole.setRoleId(roleId);
					return userRole;
				}).collect(Collectors.toList());
		return sysUserRoleService.saveBatch(userRoleList);
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param sysUser ??????
	 * @return
	 */
	@Override
	public UserInfo findUserInfo(SysUser sysUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);
		//??????????????????  ???ID???
		List<String> roleIds = sysRoleService.findRoleIdsByUserId(sysUser.getId());
		userInfo.setRoles(ArrayUtil.toArray(roleIds, String.class));

		//?????????????????????menu.permission???
		Set<String> permissions = new HashSet<>();
		roleIds.forEach(roleId -> {
			List<String> permissionList = sysMenuService.findMenuByRoleId(roleId)
					.stream()
					.filter(menuVo -> StrUtil.isNotEmpty(menuVo.getPermission()))
					.map(MenuVO::getPermission)
					.collect(Collectors.toList());
			permissions.addAll(permissionList);
		});
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return userInfo;
	}

	/**
	 * ????????????????????????????????????????????????
	 *
	 * @param page    ????????????
	 * @param userDTO ????????????
	 * @return
	 */
	@Override
	public IPage getUsersWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO);
	}

	/**
	 * ??????ID??????????????????
	 *
	 * @param id ??????ID
	 * @return ????????????
	 */
	@Override
	public UserVO selectUserVoById(String id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * ????????????
	 *
	 * @param sysUser ??????
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_CACHE, key = "#sysUser.username")
	public Boolean deleteUserById(SysUser sysUser) {
		sysUserRoleService.deleteByUserId(sysUser.getId());
		this.removeById(sysUser.getId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_CACHE, key = "#userDto.username")
	public Boolean updateUserInfo(UserDTO userDto) {
		UserVO userVO = baseMapper.getUserVoById(userDto.getId());
		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(userDto.getPassword())
				&& StrUtil.isNotBlank(userDto.getNewpassword1())) {//????????????
			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
			} else {
				log.warn("????????????????????????????????????:{}", userDto.getUsername());
				throw new RuntimeException("??????????????????????????????");
			}
		}
		sysUser.setId(userVO.getId());
		sysUser.setAvatar(userDto.getAvatar());
		sysUser.setEmail(userDto.getEmail());
		baseMapper.updateById(sysUser);
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_CACHE, key = "#userDto.username")
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setUpdateTime(LocalDateTime.now());
		sysUser.setPassword(null);
		this.updateById(sysUser);

		sysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
				.eq(SysUserRole::getUserId, userDto.getId()));
		userDto.getRoleIds().forEach(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});
		return Boolean.TRUE;
	}

	/**
	 * ?????????????????????????????????
	 *
	 * @param username ?????????
	 * @return R
	 */
	@Override
	public List<SysUser> listAncestorUsers(String username) {
		SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getUsername, username));

		SysOrgan sysOrgan = sysOrganService.getById(sysUser.getOrganId());
		if (sysOrgan == null) {
			return null;
		}

		String parentId = sysOrgan.getParentId();
		return this.list(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getOrganId, parentId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R register(UserRegister userRegister) {
		String tenantId = IdUtil.simpleUUID();
		TenantContextHolder.setTenantId(tenantId);
		//????????????
		SysOrgan sysOrgan = new SysOrgan();
		sysOrgan.setId(tenantId);
		sysOrgan.setName(userRegister.getOrganname());
		sysOrgan.setParentId(CommonConstants.PARENT_ID);
		sysOrgan.setType(CommonConstants.ORGAN_TYPE_1);
		sysOrgan.setCode("10000");
		sysOrganService.save(sysOrgan);
		//??????????????????
		sysOrganRelationService.insertOrganRelation(sysOrgan);
		//????????????
		SysUser sysUser = new SysUser();
		sysUser.setOrganId(sysOrgan.getId());
		sysUser.setUsername(userRegister.getUsername());
		sysUser.setEmail(userRegister.getEmail());
		sysUser.setPassword(ENCODER.encode(userRegister.getPassword()));
		baseMapper.insert(sysUser);
		//????????????
		SysRole sysRole = new SysRole();
		sysRole.setRoleName("?????????");
		sysRole.setRoleCode(CommonConstants.ROLE_CODE_ADMIN);
		sysRole.setRoleDesc(userRegister.getOrganname()+"?????????");
		sysRole.setDsType(CommonConstants.DS_TYPE_0);
		sysRoleService.save(sysRole);
		//??????????????????
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setRoleId(sysRole.getId());
		sysUserRole.setUserId(sysUser.getId());
		sysUserRoleService.save(sysUserRole);
		//????????????????????????role_id???2?????????????????????????????????????????????????????????
		List<SysRoleMenu> listSysRoleMenu = sysRoleMenuService
				.list(Wrappers.<SysRoleMenu>query().lambda()
				.eq(SysRoleMenu::getRoleId,2))
				.stream().map(sysRoleMenu -> {
					sysRoleMenu.setRoleId(sysRole.getId());
					return sysRoleMenu;
				}).collect(Collectors.toList());
		sysRoleMenuService.saveBatch(listSysRoleMenu);
		return R.ok();
	}

	@Override
	public SysUser getByNoTenant(SysUser sysUser) {
		return baseMapper.getByNoTenant(sysUser);
	}

	@Override
	public void bindPhone(UserDTO userDto) {
		SysUser sysUser = baseMapper.selectById(userDto.getId());
		LambdaUpdateWrapper<SysUser> userUpdateWrapper = new UpdateWrapper<SysUser>().lambda();
		userUpdateWrapper.eq(SysUser::getId,userDto.getId());
		if("2".equals(userDto.getDoType())){//????????????
			if(StrUtil.isNotBlank(sysUser.getPhone())){
				throw new RuntimeException("?????????????????????????????????????????????");
			}
			userUpdateWrapper.set(SysUser::getPhone, userDto.getPhone());
		}else if("3".equals(userDto.getDoType())){//??????
			userUpdateWrapper.set(SysUser::getPhone, null);
		}
		this.update(userUpdateWrapper);
		//????????????
		cacheManager.getCache(CacheConstants.USER_CACHE).evict(sysUser.getUsername());
	}

}
