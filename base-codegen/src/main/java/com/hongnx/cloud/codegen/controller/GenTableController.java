package com.hongnx.cloud.codegen.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.codegen.service.GenTableService;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.codegen.entity.GenTable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 代码生成配置表
 *
 * @date 2020-04-07 19:14:52
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/gentable")
@Api(value = "gentable", tags = "代码生成配置表管理")
public class GenTableController {

    private final GenTableService genTableService;

    /**
     * 代码生成配置表查询
     * @param tableName
     * @return R
     */
    @ApiOperation(value = "代码生成配置表查询")
    @GetMapping("/{tableName}")
    public R getById(@PathVariable("tableName") String tableName) {
        return R.ok(genTableService.getOne(Wrappers.<GenTable>query().lambda()
				.eq(GenTable::getTableName,tableName)));
    }

    /**
     * 代码生成配置表修改
     * @param genTable 代码生成配置表
     * @return R
     */
    @ApiOperation(value = "代码生成配置表修改")
    @SysLog("修改代码生成配置表")
    @PutMapping
    public R updateById(@RequestBody GenTable genTable) {
        return R.ok(genTableService.saveOrUpdate(genTable));
    }

}
