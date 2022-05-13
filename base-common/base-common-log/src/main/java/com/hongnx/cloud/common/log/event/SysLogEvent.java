package com.hongnx.cloud.common.log.event;

import com.hongnx.cloud.upms.common.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author
 * 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {
	private final SysLog sysLog;
}
