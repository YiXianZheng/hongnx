package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.NoticeItemService;
import com.hongnx.cloud.mall.admin.service.NoticeService;
import com.hongnx.cloud.mall.common.entity.Notice;
import com.hongnx.cloud.mall.common.entity.NoticeItem;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商城通知详情
 *
 * @date 2019-11-09 19:39:03
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/noticeitem")
@Api(value = "noticeitem", tags = "商城通知详情管理")
public class NoticeItemController {

    private final NoticeItemService noticeItemService;

	private final NoticeService noticeService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param noticeItem 商城通知详情
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:noticeitem:index')")
    public R getNoticeItemPage(Page page, NoticeItem noticeItem) {
		Notice notice = new Notice();
		notice.setAppId(noticeItem.getAppId());
		notice.setType(noticeItem.getNoticeType());
		notice = noticeService.getOne(Wrappers.query(notice));
		if(notice == null){
			return R.ok(page);
		}
		noticeItem.setNoticeId(notice.getId());
        return R.ok(noticeItemService.page(page,Wrappers.query(noticeItem)));
    }

    /**
    * 通过id查询商城通知详情
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询商城通知详情")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:noticeitem:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(noticeItemService.getById(id));
    }

    /**
    * 新增商城通知详情
    * @param noticeItem 商城通知详情
    * @return R
    */
	@ApiOperation(value = "新增商城通知详情")
    @SysLog("新增商城通知详情")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:noticeitem:add')")
    public R save(@RequestBody NoticeItem noticeItem){
        return R.ok(noticeItemService.save(noticeItem));
    }

    /**
    * 修改商城通知详情
    * @param noticeItem 商城通知详情
    * @return R
    */
	@ApiOperation(value = "修改商城通知详情")
    @SysLog("修改商城通知详情")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:noticeitem:edit')")
    public R updateById(@RequestBody NoticeItem noticeItem){
        return R.ok(noticeItemService.updateById(noticeItem));
    }

    /**
    * 通过id删除商城通知详情
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除商城通知详情")
    @SysLog("删除商城通知详情")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:noticeitem:del')")
    public R removeById(@PathVariable String id){
        return R.ok(noticeItemService.removeById(id));
    }

}
