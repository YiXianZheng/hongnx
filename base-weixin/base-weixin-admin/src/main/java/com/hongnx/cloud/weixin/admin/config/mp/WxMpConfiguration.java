package com.hongnx.cloud.weixin.admin.config.mp;

import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.weixin.admin.service.WxAppService;
import com.hongnx.cloud.weixin.admin.config.open.WxOpenConfiguration;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import com.hongnx.cloud.weixin.admin.handler.LocationHandler;
import com.hongnx.cloud.weixin.admin.handler.MassMsgHandler;
import com.hongnx.cloud.weixin.admin.handler.MenuHandler;
import com.hongnx.cloud.weixin.admin.handler.MsgHandler;
import com.hongnx.cloud.weixin.admin.handler.StoreCheckNotifyHandler;
import com.hongnx.cloud.weixin.admin.handler.SubscribeHandler;
import com.hongnx.cloud.weixin.admin.handler.UserActivateCardHandler;
import com.hongnx.cloud.weixin.admin.handler.UserGetCardHandler;
import com.hongnx.cloud.weixin.admin.handler.KfSessionHandler;
import com.hongnx.cloud.weixin.admin.handler.LogHandler;
import com.hongnx.cloud.weixin.admin.handler.NullHandler;
import com.hongnx.cloud.weixin.admin.handler.UnSubscribeHandler;
import com.hongnx.cloud.weixin.admin.handler.UserDelCardHandler;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts.EventType;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 公众号Configuration
 *
 */
@Slf4j
@Configuration
public class WxMpConfiguration {

	private static RedisTemplate redisTemplate;
	private static WxAppService wxAppService;
	private static LogHandler logHandler;
	private static NullHandler nullHandler;
	private static KfSessionHandler kfSessionHandler;
	private static StoreCheckNotifyHandler storeCheckNotifyHandler;
	private static LocationHandler locationHandler;
	private static MenuHandler menuHandler;
	private static MsgHandler msgHandler;
	private static UnSubscribeHandler unSubscribeHandler;
	private static SubscribeHandler subscribeHandler;
	private static MassMsgHandler massMsgHandler;
	private static UserGetCardHandler userGetCardHandler;
	private static UserDelCardHandler userDelCardHandler;
	private static UserActivateCardHandler userActivateCardHandler;

	@Autowired
	public WxMpConfiguration(LogHandler logHandler, NullHandler nullHandler
			, KfSessionHandler kfSessionHandler, StoreCheckNotifyHandler storeCheckNotifyHandler
			, LocationHandler locationHandler, MenuHandler menuHandler
			, MsgHandler msgHandler, UnSubscribeHandler unSubscribeHandler
			, SubscribeHandler subscribeHandler, MassMsgHandler massMsgHandler
			, UserGetCardHandler userGetCardHandler, UserDelCardHandler userDelCardHandler
			, UserActivateCardHandler userActivateCardHandler
			, RedisTemplate redisTemplate, WxAppService wxAppService) {
		this.logHandler = logHandler;
		this.nullHandler = nullHandler;
		this.kfSessionHandler = kfSessionHandler;
		this.storeCheckNotifyHandler = storeCheckNotifyHandler;
		this.locationHandler = locationHandler;
		this.menuHandler = menuHandler;
		this.msgHandler = msgHandler;
		this.unSubscribeHandler = unSubscribeHandler;
		this.subscribeHandler = subscribeHandler;
		this.massMsgHandler = massMsgHandler;
		this.userGetCardHandler = userGetCardHandler;
		this.userDelCardHandler = userDelCardHandler;
		this.userActivateCardHandler = userActivateCardHandler;
		this.redisTemplate = redisTemplate;
		this.wxAppService = wxAppService;
	}

	/**
	 *  获取WxMpService
	 * @param appId
	 * @return
	 */
	public static WxMpService getMpService(String appId) {
		WxMpService wxMpService = null;
		WxApp wxApp = wxAppService.findByAppId(appId);
		if(wxApp!=null) {
			if(CommonConstants.YES.equals(wxApp.getIsComponent())){//第三方授权账号
				wxMpService = WxOpenConfiguration.getOpenService().getWxOpenComponentService().getWxMpServiceByAppid(appId);
			}else{
				WxMpInRedisConfigStorage configStorage = new WxMpInRedisConfigStorage(redisTemplate);
				configStorage.setAppId(wxApp.getId());
				configStorage.setSecret(wxApp.getSecret());
				configStorage.setToken(wxApp.getToken());
				configStorage.setAesKey(wxApp.getAesKey());
				wxMpService = new WxMpServiceImpl();
				wxMpService.setWxMpConfigStorage(configStorage);
			}
		}
		return wxMpService;
    }

	/**
	 *  获取全局缓存WxMpMessageRouter
	 * @param appId
	 * @return
	 */
	public static WxMpMessageRouter getWxMpMessageRouter(String appId) {
		WxMpMessageRouter wxMpMessageRouter = newRouter(getMpService(appId));
		return wxMpMessageRouter;
    }
	
	private static WxMpMessageRouter newRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
            .handler(kfSessionHandler).end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
            .handler(kfSessionHandler)
            .end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
            .handler(kfSessionHandler).end();

        // 门店审核事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(WxMpEventConstants.POI_CHECK_NOTIFY)
            .handler(storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(MenuButtonType.CLICK).handler(menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(MenuButtonType.VIEW).handler(menuHandler).end();

		// 扫码事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(EventType.SCANCODE_WAITMSG).handler(menuHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.SUBSCRIBE).handler(subscribeHandler)
            .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.UNSUBSCRIBE)
            .handler(unSubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.LOCATION).handler(locationHandler)
            .end();
        
        // 卡券领取事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.CARD_USER_GET_CARD).handler(userGetCardHandler).end();
        
        // 卡券删除事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.CARD_USER_DEL_CARD).handler(userDelCardHandler).end();
        
        // 卡券激活事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.CARD_SUBMIT_MEMBERCARD_USER_INFO).handler(userActivateCardHandler).end();

		// 群发回调事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(EventType.MASS_SEND_JOB_FINISH).handler(massMsgHandler).end();

        // 默认
        newRouter.rule().async(false).handler(msgHandler).end();

        return newRouter;
    }
}
