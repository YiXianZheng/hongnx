package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.NoticeService;
import com.hongnx.cloud.mall.common.entity.Notice;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商城通知
 *
 * @date 2019-11-09 19:37:56
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/notice")
@Api(value = "notice", tags = "商城通知管理")
public class NoticeController {

    private final NoticeService noticeService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param notice 商城通知
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:notice:index')")
    public R getNoticePage(Page page, Notice notice) {
        return R.ok(noticeService.page(page,Wrappers.query(notice)));
    }

    /**
    * 通过id查询商城通知
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询商城通知")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:notice:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(noticeService.getById(id));
    }

    /**
    * 新增商城通知
    * @param notice 商城通知
    * @return R
    */
	@ApiOperation(value = "新增商城通知")
    @SysLog("新增商城通知")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:notice:add')")
    public R save(@RequestBody Notice notice){
        return R.ok(noticeService.save(notice));
    }

    /**
    * 修改商城通知
    * @param notice 商城通知
    * @return R
    */
	@ApiOperation(value = "修改商城通知")
    @SysLog("修改商城通知")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:notice:edit')")
    public R updateById(@RequestBody Notice notice){
        return R.ok(noticeService.updateById(notice));
    }

    /**
    * 通过id删除商城通知
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除商城通知")
    @SysLog("删除商城通知")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:notice:del')")
    public R removeById(@PathVariable String id){
        return R.ok(noticeService.removeById(id));
    }

}
