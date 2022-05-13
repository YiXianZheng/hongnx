package com.hongnx.cloud.weixin.common.entity;

import lombok.Data;

/**
 * 微信自定义菜单分组
 *
 * @date 2020-02-22 19:34:22
 */
@Data
public class MenuRule {
    private static final long serialVersionUID=1L;

    /**
     * 用户标签的id
     */
    private String tag_id;

    private String id;

    private String menuType;

	private String name;
}
