package com.hongnx.cloud.codegen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.codegen.mapper.GenTableMapper;
import com.hongnx.cloud.codegen.service.GenTableService;
import com.hongnx.cloud.codegen.entity.GenTable;
import org.springframework.stereotype.Service;

/**
 * 代码生成配置表
 *
 * @date 2020-04-07 19:14:52
 */
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements GenTableService {

}
