package com.hongnx.cloud.upms.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.upms.admin.service.SysRoleService;
import com.hongnx.cloud.upms.admin.service.SysTenantService;
import com.hongnx.cloud.upms.admin.service.SysUserRoleService;
import com.hongnx.cloud.upms.admin.service.SysUserService;
import com.hongnx.cloud.upms.admin.service.*;
import com.hongnx.cloud.upms.common.dto.UserDTO;
import com.hongnx.cloud.upms.common.dto.UserInfo;
import com.hongnx.cloud.upms.common.dto.UserRegister;
import com.hongnx.cloud.upms.common.entity.SysRole;
import com.hongnx.cloud.upms.common.entity.SysTenant;
import com.hongnx.cloud.upms.common.entity.SysUser;
import com.hongnx.cloud.common.core.constant.CacheConstants;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.common.security.annotation.Inside;
import com.hongnx.cloud.common.security.util.SecurityUtils;
import com.hongnx.cloud.upms.common.entity.SysUserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "user", tags = "??????????????????")
public class SysUserController {
	private final SysUserService sysUserService;
	private final SysRoleService sysRoleService;
	private final SysUserRoleService sysUserRoleService;
	private final RedisTemplate redisTemplate;
	private final SysTenantService sysTenantService;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	/**
	 * ??????????????????????????????
	 *
	 * @return ????????????
	 */
	@ApiOperation(value = "??????????????????????????????")
	@GetMapping(value = {"/info"})
	public R info() {
		String username = SecurityUtils.getUser().getUsername();
		SysUser user = sysUserService.getOne(Wrappers.<SysUser>query()
				.lambda().eq(SysUser::getUsername, username));
		if (user == null) {
			return R.failed(null, "??????????????????????????????");
		}
		return R.ok(sysUserService.findUserInfo(user));
	}

	/**
	 * ??????????????????????????????
	 *
	 * @return ????????????
	 */
	@ApiOperation(value = "??????????????????????????????")
	@Inside
	@GetMapping("/info/{username}")
	public R info(@PathVariable String username) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser = sysUserService.getByNoTenant(sysUser);
		if (sysUser == null) {
			return R.failed(null, String.format("?????????????????? %s", username));
		}
		TenantContextHolder.setTenantId(sysUser.getTenantId());
		if(CommonConstants.STATUS_NORMAL.equals(sysUser.getLockFlag())){
			//?????????????????????????????????????????????????????????
			SysTenant sysTenant = sysTenantService.getById(sysUser.getTenantId());
			if(CommonConstants.STATUS_LOCK.equals(sysTenant.getStatus())){
				sysUser.setLockFlag(CommonConstants.STATUS_LOCK);
			}
		}
		UserInfo userInfo = sysUserService.findUserInfo(sysUser);
		return R.ok(userInfo);
	}

	/**
	 * ??????ID??????????????????
	 *
	 * @param id ID
	 * @return ????????????
	 */
	@ApiOperation(value = "??????ID??????????????????")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:user:get')")
	public R user(@PathVariable String id) {
		return R.ok(sysUserService.selectUserVoById(id));
	}

	/**
	 * ?????????????????????????????????
	 *
	 * @param username ?????????
	 * @return
	 */
	@ApiOperation(value = "?????????????????????????????????")
	@GetMapping("/detail/{username}")
	public R userByUsername(@PathVariable String username) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		return R.ok(sysUserService.getByNoTenant(sysUser));
	}

	/**
	 * ??????????????????
	 *
	 * @param id ID
	 * @return R
	 */
	@SysLog("??????????????????")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:user:del')")
	@ApiOperation(value = "????????????", notes = "??????ID????????????")
	@ApiImplicitParam(name = "id", value = "??????ID", required = true, dataType = "int", paramType = "path")
	public R userDel(@PathVariable String id) {
		//??????????????????????????????????????????
		SysRole sysRole = sysRoleService.getOne(Wrappers.<SysRole>update().lambda()
				.eq(SysRole::getRoleCode,CommonConstants.ROLE_CODE_ADMIN));
		List<SysUserRole> listSysUserRole = sysUserRoleService.list(Wrappers.<SysUserRole>update().lambda()
				.eq(SysUserRole::getUserId,id).eq(SysUserRole::getRoleId,sysRole.getId()));
		if(listSysUserRole.size()>0){
			return R.failed("??????????????????????????????????????????");
		}
		SysUser sysUser = sysUserService.getById(id);
		return R.ok(sysUserService.deleteUserById(sysUser));
	}

	/**
	 * ????????????
	 *
	 * @param userDto ????????????
	 * @return ok/false
	 */
	@ApiOperation(value = "????????????")
	@SysLog("????????????")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys:user:add')")
	public R user(@RequestBody UserDTO userDto) {
		try{
			return R.ok(sysUserService.saveUser(userDto));
		}catch (DuplicateKeyException e){
			return judeParm(e);
		}
	}

	/**
	 * ??????????????????
	 *
	 * @param userDto ????????????
	 * @return R
	 */
	@ApiOperation(value = "??????????????????")
	@SysLog("??????????????????")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys:user:edit')")
	public R updateUser(@Valid @RequestBody UserDTO userDto) {
		try{
			//???????????????????????????????????????????????????????????????1?????????
			SysRole sysRole = sysRoleService.getOne(Wrappers.<SysRole>update().lambda()
					.eq(SysRole::getRoleCode,CommonConstants.ROLE_CODE_ADMIN));
			if(!CollUtil.contains(userDto.getRoleIds(),sysRole.getId())){
				List<SysUserRole> listSysUserRole = sysUserRoleService.list(Wrappers.<SysUserRole>update().lambda()
						.eq(SysUserRole::getRoleId,sysRole.getId()));
				if(listSysUserRole.size()<=1){//???????????????????????????????????????????????????
					listSysUserRole = sysUserRoleService.list(Wrappers.<SysUserRole>update().lambda()
							.eq(SysUserRole::getRoleId,sysRole.getId())
							.eq(SysUserRole::getUserId,userDto.getId()));
					if(listSysUserRole.size()>0){
						return R.failed("?????????????????????????????????????????????");
					}
				}
			}
			return R.ok(sysUserService.updateUser(userDto));
		}catch (DuplicateKeyException e){
			return judeParm(e);
		}
	}

	/**
	 * ??????????????????
	 * @param userDto
	 * @return
	 */
	@ApiOperation(value = "??????????????????")
	@SysLog("??????????????????")
	@PutMapping("/password")
	@PreAuthorize("@ato.hasAuthority('sys:user:password')")
	public R editPassword(@Valid @RequestBody UserDTO userDto) {
		SysUser sysUser = new SysUser();
		sysUser.setId(userDto.getId());
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		sysUserService.updateById(sysUser);
		return R.ok();
	}

	/**
	 * ??????????????????
	 *
	 * @param page    ?????????
	 * @param userDTO ??????????????????
	 * @return ????????????
	 */
	@ApiOperation(value = "????????????")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys:user:index')")
	public R getUserPage(Page page, UserDTO userDTO) {
		return R.ok(sysUserService.getUsersWithRolePage(page, userDTO));
	}

	/**
	 * ????????????
	 * @param sysUser
	 * @return
	 */
	@ApiOperation(value = "????????????")
	@GetMapping("/count")
	@PreAuthorize("@ato.hasAuthority('sys:user:index')")
	public R getCount(SysUser sysUser) {
		return R.ok(sysUserService.count(Wrappers.query(sysUser)));
	}

	/**
	 * ??????????????????
	 *
	 * @param userDto userDto
	 * @return ok/false
	 */
	@ApiOperation(value = "??????????????????")
	@SysLog("??????????????????")
	@PutMapping("/edit")
	public R updateUserInfo(@Valid @RequestBody UserDTO userDto) {
		return R.ok(sysUserService.updateUserInfo(userDto));
	}

	/**
	 * ??????/???????????????
	 *
	 * @param userDto userDto
	 * @return
	 */
	@ApiOperation(value = "??????/???????????????")
	@SysLog("??????/???????????????")
	@PutMapping("/phone")
	public R bindPhone(@RequestBody UserDTO userDto) {
		//???????????????
		if(StrUtil.isBlank(userDto.getCode())){
			return R.failed("?????????????????????");
		}
		String key = CacheConstants.VER_CODE_DEFAULT + SecurityConstants.SMS_LOGIN + ":" + userDto.getPhone();
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		if (!redisTemplate.hasKey(key)) {
			return R.failed("???????????????");
		}
		Object codeObj = redisTemplate.opsForValue().get(key);
		if (codeObj == null) {
			return R.failed("??????????????????");
		}
		String saveCode = codeObj.toString();
		if (StrUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			return R.failed("??????????????????");
		}
		if (!StrUtil.equals(saveCode, userDto.getCode())) {
			return R.failed("??????????????????");
		}
		sysUserService.bindPhone(userDto);
		return R.ok();
	}

	/**
	 * @param username ????????????
	 * @return ????????????????????????
	 */
	@ApiOperation(value = "??????")
	@GetMapping("/ancestor/{username}")
	public R listAncestorUsers(@PathVariable String username) {
		return R.ok(sysUserService.listAncestorUsers(username));
	}

	/**
	 * ????????????
	 * @param userRegister
	 * @return
	 */
	@ApiOperation(value = "????????????")
	@PostMapping("/register")
	public R register(@RequestBody UserRegister userRegister){
		//???????????????
		String key = CacheConstants.VER_CODE_REGISTER + CommonConstants.EMAIL + ":" + userRegister.getEmail();
		Object codeObj = redisTemplate.opsForValue().get(key);
		if(codeObj == null || !codeObj.equals(userRegister.getCode())){
			return R.failed("???????????????");
		}
		try{
			return sysUserService.register(userRegister);
		}catch (DuplicateKeyException e){
			return judeParm(e);
		}
	}

	/**
	 *
	 * @param e
	 * @return
	 */
	R judeParm(DuplicateKeyException e){
		if(e.getMessage().contains("uk_username")){
			return R.failed("?????????????????????");
		}else if(e.getMessage().contains("uk_email")){
			return R.failed("??????????????????");
		}else{
			return R.failed(e.getMessage());
		}
	}
}
