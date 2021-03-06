package com.hongnx.cloud.common.core.constant;

/**
 * @author
 * <p>
 * 缓存的key 常量
 */
public interface CacheConstants {

	/**
	 * 用户信息缓存
	 */
	String USER_CACHE = "user_cache";

	/**
	 * oauth 客户端信息
	 */
	String OAUTH_CLIENT_CACHE = "base_oauth:client:cache";

	/**
	 * 菜单信息缓存
	 */
	String MENU_CACHE = "menu_cache";

	/**
	 * spring boot admin 事件key
	 */
	String EVENT_KEY = "event_key";

	/**
	 * 路由缓存
	 */
	String ROUTE_CACHE = "gateway_route_cache";

	/**
	 * 字典缓存
	 */
	String DICT_CACHE = "dict_cache";

	/**
	 * 默认验证码前缀
	 */
	String VER_CODE_DEFAULT = "ver_code_default:";

	/**
	 * 注册验证码前缀
	 */
	String VER_CODE_REGISTER = "ver_code_register:";

	/**
	 * wxapp缓存
	 */
	String WXAPP_WEIXIN_SIGN_CACHE = "wx:weixin_Sign";

	/**
	 * wxapp缓存
	 */
	String WXAPP_APP_ID_CACHE = "wx:app_id";
}
