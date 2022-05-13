package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.GrouponInfoService;
import com.hongnx.cloud.mall.admin.service.GrouponUserService;
import com.hongnx.cloud.mall.common.entity.GrouponUser;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 拼团记录
 *
 * @date 2020-03-17 12:01:53
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/grouponuser")
@Api(value = "grouponuser", tags = "拼团记录Api")
public class GrouponUserApi {

    private final GrouponUserService grouponUserService;
	private final GrouponInfoService grouponInfoService;

	/**
	 * 拼团中分页列表
	 * @param page 分页对象
	 * @param grouponUser 拼团记录
	 * @return
	 */
	@ApiOperation(value = "拼团中分页列表")
	@GetMapping("/page/grouponing")
	public R getPageGrouponing(Page page, GrouponUser grouponUser) {
		return R.ok(grouponUserService.getPageGrouponing(page, grouponUser));
	}

    /**
     * 分页列表
     * @param page 分页对象
     * @param grouponUser 拼团记录
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    public R getPage(Page page, GrouponUser grouponUser) {
		return R.ok(grouponUserService.page2(page, grouponUser));
    }

	/**
	 * 拼团记录查询
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "拼团记录查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") String id) {
		GrouponUser grouponUser = new GrouponUser();
		grouponUser.setUserId(ThirdSessionHolder.getMallUserId());
		String userId = grouponUser.getUserId();
		grouponUser = grouponUserService.getById(id);
		grouponUser.setGrouponInfo(grouponInfoService.getById2((grouponUser.getGrouponId())));
		//查询当前用户是否已参与
		grouponUser.setGrouponUser(
				grouponUserService.getOne(Wrappers.<GrouponUser>lambdaQuery()
				.eq(GrouponUser::getGroupId,id)
				.eq(GrouponUser::getUserId,userId)));
		return R.ok(grouponUser);
	}

}
