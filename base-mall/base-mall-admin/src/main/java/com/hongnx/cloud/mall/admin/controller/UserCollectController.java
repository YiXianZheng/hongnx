package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.UserCollectService;
import com.hongnx.cloud.mall.common.entity.UserCollect;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 用户收藏
 *
 * @date 2019-09-22 20:45:45
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/usercollect")
@Api(value = "usercollect", tags = "用户收藏管理")
public class UserCollectController {

    private final UserCollectService userCollectService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param userCollect 用户收藏
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:usercollect:index')")
    public R getUserCollectPage(Page page, UserCollect userCollect) {
        return R.ok(userCollectService.page1(page,userCollect));
    }

    /**
    * 通过id查询用户收藏
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询用户收藏")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:usercollect:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(userCollectService.getById(id));
    }

    /**
    * 新增用户收藏
    * @param userCollect 用户收藏
    * @return R
    */
	@ApiOperation(value = "新增用户收藏")
    @SysLog("新增用户收藏")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:usercollect:add')")
    public R save(@RequestBody UserCollect userCollect){
        return R.ok(userCollectService.save(userCollect));
    }

    /**
    * 修改用户收藏
    * @param userCollect 用户收藏
    * @return R
    */
	@ApiOperation(value = "修改用户收藏")
    @SysLog("修改用户收藏")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:usercollect:edit')")
    public R updateById(@RequestBody UserCollect userCollect){
        return R.ok(userCollectService.updateById(userCollect));
    }

    /**
    * 通过id删除用户收藏
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除用户收藏")
    @SysLog("删除用户收藏")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:usercollect:del')")
    public R removeById(@PathVariable String id){
        return R.ok(userCollectService.removeById(id));
    }

}
