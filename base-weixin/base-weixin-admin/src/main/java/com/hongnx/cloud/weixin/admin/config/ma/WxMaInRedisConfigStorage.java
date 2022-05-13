package com.hongnx.cloud.weixin.admin.config.ma;

import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的微信配置provider.
 *
 */
@SuppressWarnings("serial")
public class WxMaInRedisConfigStorage extends WxMaDefaultConfigImpl {

	public static final String ACCESS_TOKEN_KEY = "wx:ma:access_token:";

	public final static String JSAPI_TICKET_KEY = "wx:ma:jsapi_ticket:";

	public final static String CARDAPI_TICKET_KEY = "wx:ma:cardapi_ticket:";

	private final RedisTemplate<String, String> redisTemplate;

	public WxMaInRedisConfigStorage(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private String accessTokenKey;

	private String jsapiTicketKey;

	private String cardapiTicketKey;

	/**
	 * 每个公众号生成独有的存储key.
	 */
	@Override
	public void setAppid(String appId) {
		super.setAppid(appId);
		this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
		this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(appId);
		this.cardapiTicketKey = CARDAPI_TICKET_KEY.concat(appId);
	}


	/**
	 *
	 * @return
	 */
	@Override
	public String getAccessToken() {
		return redisTemplate.opsForValue().get(this.accessTokenKey);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public boolean isAccessTokenExpired() {
		return redisTemplate.getExpire(accessTokenKey) < 2;
	}

	/**
	 *
	 * @param accessToken
	 * @param expiresInSeconds
	 */
	@Override
	public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
		redisTemplate.opsForValue().set(this.accessTokenKey, accessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
	}

	/**
	 *
	 */
	@Override
	public void expireAccessToken() {
		redisTemplate.expire(this.accessTokenKey, 0, TimeUnit.SECONDS);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getJsapiTicket() {
		return redisTemplate.opsForValue().get(this.jsapiTicketKey);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getCardApiTicket() {
		return redisTemplate.opsForValue().get(cardapiTicketKey);
	}
}
