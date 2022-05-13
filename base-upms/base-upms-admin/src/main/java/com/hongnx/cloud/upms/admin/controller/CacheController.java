package com.hongnx.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.hongnx.cloud.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 缓存监控
 *
 * @author ruoyi
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cache")
public class CacheController {
	private final RedisTemplate<String, String> redisTemplate;

	@PreAuthorize("@ato.hasAuthority('sys:cache:index')")
	@GetMapping
	public R getInfo() {
		Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
		Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
		Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

		Map<String, Object> result = new HashMap<>(3);
		result.put("info", info);
		result.put("dbSize", dbSize);

		List<Map<String, String>> pieList = new ArrayList<>();
		commandStats.stringPropertyNames().forEach(key -> {
			Map<String, String> data = new HashMap<>(2);
			String property = commandStats.getProperty(key);
			data.put("name", StrUtil.removePrefix(key, "cmdstat_"));
			data.put("value", StrUtil.subBetween(property, "calls=", ",usec"));
			pieList.add(data);
		});
		result.put("commandStats", pieList);
		return R.ok(result);
	}
}
