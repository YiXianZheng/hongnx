package com.hongnx.cloud.weixin.common.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.hongnx.cloud.weixin.common.entity.ThirdSession;
import lombok.experimental.UtilityClass;

/**
 * @author
 * thirdSession工具类
 */
@UtilityClass
public class ThirdSessionHolder {

	private final ThreadLocal<ThirdSession> THREAD_LOCAL_THIRD_SESSION = new TransmittableThreadLocal<>();


	/**
	 * TTL 设置thirdSession
	 *
	 * @param thirdSession
	 */
	public void setThirdSession(ThirdSession thirdSession) {
		THREAD_LOCAL_THIRD_SESSION.set(thirdSession);
	}

	/**
	 * 获取TTL中的thirdSession
	 *
	 * @return
	 */
	public ThirdSession getThirdSession() {
		return THREAD_LOCAL_THIRD_SESSION.get();
	}

	/**
	 * 获取用户商城ID
	 * @return
	 */
	public String getMallUserId(){
		return THREAD_LOCAL_THIRD_SESSION.get().getMallUserId();
	}
	public void clear() {
		THREAD_LOCAL_THIRD_SESSION.remove();
	}
}
