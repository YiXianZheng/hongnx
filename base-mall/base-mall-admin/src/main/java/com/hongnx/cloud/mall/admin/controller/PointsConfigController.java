package com.hongnx.cloud.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.PointsConfigService;
import com.hongnx.cloud.mall.common.entity.PointsConfig;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 积分设置
 *
 * @date 2019-12-06 16:15:01
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/pointsconfig")
@Api(value = "pointsconfig", tags = "积分设置管理")
public class PointsConfigController {

    private final PointsConfigService pointsConfigService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param pointsConfig 积分设置
     * @return
     */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:pointsconfig:index')")
    public R getPointsConfigPage(Page page, PointsConfig pointsConfig) {
        return R.ok(pointsConfigService.page(page, Wrappers.query(pointsConfig)));
    }

    /**
     * 通过id查询积分设置
     * @param id
     * @return R
     */
	@ApiOperation(value = "通过id查询积分设置")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:pointsconfig:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(pointsConfigService.getById(id));
    }

    /**
     * 新增积分设置
     * @param pointsConfig 积分设置
     * @return R
     */
	@ApiOperation(value = "新增积分设置")
    @SysLog("新增积分设置")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:pointsconfig:add')")
    public R save(@RequestBody PointsConfig pointsConfig) {
        return R.ok(pointsConfigService.save(pointsConfig));
    }

    /**
     * 修改积分设置
     * @param pointsConfig 积分设置
     * @return R
     */
	@ApiOperation(value = "修改积分设置")
    @SysLog("修改积分设置")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:pointsconfig:edit')")
    public R updateById(@RequestBody PointsConfig pointsConfig) {
    	if(StrUtil.isNotBlank(pointsConfig.getId())){
			pointsConfigService.updateById(pointsConfig);
			return R.ok(pointsConfig);
		}else{
			pointsConfigService.save(pointsConfig);
			return R.ok(pointsConfig);
		}
    }

    /**
     * 通过id删除积分设置
     * @param id
     * @return R
     */
	@ApiOperation(value = "通过id删除积分设置")
    @SysLog("删除积分设置")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:pointsconfig:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(pointsConfigService.removeById(id));
    }

	/**
	 * 查询积分设置
	 * @return R
	 */
	@ApiOperation(value = "查询积分设置")
	@GetMapping()
	@PreAuthorize("@ato.hasAuthority('mall:pointsconfig:get')")
	public R get() {
		return R.ok(pointsConfigService.getOne(Wrappers.emptyWrapper()));
	}
}
