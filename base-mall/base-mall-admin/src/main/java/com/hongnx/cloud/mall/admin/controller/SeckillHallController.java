package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.SeckillHallService;
import com.hongnx.cloud.mall.common.entity.SeckillHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀会场
 *
 * @date 2020-08-11 16:38:56
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/seckillhall")
@Api(value = "seckillhall", tags = "秒杀会场管理")
public class SeckillHallController {

    private final SeckillHallService seckillHallService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param seckillHall 秒杀会场
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:seckillhall:index')")
    public R getPage(Page page, SeckillHall seckillHall) {
        return R.ok(seckillHallService.page(page, Wrappers.query(seckillHall)));
    }

    /**
     * 秒杀会场查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "秒杀会场查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:seckillhall:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(seckillHallService.getById2(id));
    }

    /**
     * 秒杀会场新增
     * @param seckillHall 秒杀会场
     * @return R
     */
    @ApiOperation(value = "秒杀会场新增")
    @SysLog("新增秒杀会场")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:seckillhall:add')")
    public R save(@RequestBody SeckillHall seckillHall) {
		try {
			return R.ok(seckillHallService.save(seckillHall));
		}catch (DuplicateKeyException e){
			if(e.getMessage().contains("uk_date_time")){
				return R.failed("会场时间重复");
			}else{
				return R.failed(e.getMessage());
			}
		}
    }

    /**
     * 秒杀会场修改
     * @param seckillHall 秒杀会场
     * @return R
     */
    @ApiOperation(value = "秒杀会场修改")
    @SysLog("修改秒杀会场")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:seckillhall:edit')")
    public R updateById(@RequestBody SeckillHall seckillHall) {
        return R.ok(seckillHallService.updateById1(seckillHall));
    }

    /**
     * 秒杀会场删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "秒杀会场删除")
    @SysLog("删除秒杀会场")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:seckillhall:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(seckillHallService.removeById(id));
    }

}
