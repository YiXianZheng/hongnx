package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.common.security.util.SecurityUtils;
import com.hongnx.cloud.mall.admin.service.MaterialGroupService;
import com.hongnx.cloud.mall.common.entity.MaterialGroup;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 素材分组
 *
 * @date 2019-10-26 19:29:07
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/materialgroup")
@Api(value = "materialgroup", tags = "素材分组管理")
public class MaterialGroupController {

    private final MaterialGroupService materialGroupService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param materialGroup 素材分组
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:material:index')")
    public R getMaterialGroupPage(Page page, MaterialGroup materialGroup) {
        return R.ok(materialGroupService.page(page,Wrappers.query(materialGroup)));
    }

    /**
    * 通过id查询素材分组
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询素材分组")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:material:index')")
    public R getById(@PathVariable("id") String id){
        return R.ok(materialGroupService.getById(id));
    }

    /**
    * 新增素材分组
    * @param materialGroup 素材分组
    * @return R
    */
	@ApiOperation(value = "新增素材分组")
    @SysLog("新增素材分组")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:material:add')")
    public R save(@RequestBody MaterialGroup materialGroup){
		materialGroup.setCreateId(SecurityUtils.getUser().getId());
        return R.ok(materialGroupService.save(materialGroup));
    }

    /**
    * 修改素材分组
    * @param materialGroup 素材分组
    * @return R
    */
	@ApiOperation(value = "修改素材分组")
    @SysLog("修改素材分组")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:material:edit')")
    public R updateById(@RequestBody MaterialGroup materialGroup){
        return R.ok(materialGroupService.updateById(materialGroup));
    }

    /**
    * 通过id删除素材分组
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除素材分组")
    @SysLog("删除素材分组")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:material:del')")
    public R removeById(@PathVariable String id){
        return R.ok(materialGroupService.removeById(id));
    }

}
