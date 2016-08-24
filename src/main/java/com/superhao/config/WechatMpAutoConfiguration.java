package com.superhao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.superhao.config.wx.handlers.DefaultHandler;
import com.superhao.config.wx.handlers.InviteHandler;
import com.superhao.config.wx.handlers.SubscribeHandler;
import com.superhao.config.wx.handlers.VoiceHandler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * wechat map auto configuration
 *
 * @author linux_china
 */
@Configuration
@ConditionalOnClass({ WxMpService.class, WxMpMessageRouter.class })
@EnableConfigurationProperties(WechatMpProperties.class)
public class WechatMpAutoConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatMpAutoConfiguration.class);

	@Autowired
	private WechatMpProperties properties;
	
	@Value("${xfyun.appid}")
	String xfyun_appid;
	
	@Value("${flight.search_url}")
	String flight_search_url;
	
	@Value("${flight.user_token}")
	String flight_user_token;

	@Bean
	@ConditionalOnMissingBean
	public WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(properties.getAppId());
		configStorage.setSecret(properties.getSecret());
		configStorage.setToken(properties.getToken());
		configStorage.setAesKey(properties.getAesKey());
		configStorage.setPartnerId(properties.getPartnerId());
		configStorage.setPartnerKey(properties.getPartnerKey());
		
		return configStorage;
	}

	@Bean
	@ConditionalOnMissingBean
	public WxMpService wxMpService(WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage) {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
		return wxMpService;
	}

	/**
	 * WxMpMessageRouter支持从4个角度对消息进行匹配，然后交给事先指定的WxMpMessageRouter：
	 * 
	 * msgType event eventKey content
	 * 
	 * @param wxMpService
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
		WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
		wxMpMessageRouter
		        // 邀请加入
		        .rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_CLICK).eventKey("V1002_INVITE")
		        .handler(new InviteHandler()).end()
		        // 关注事件
		        .rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE)
		        .handler(new SubscribeHandler()).end()
		        // .content("CONTENT").rContent("content正则表达式").handler(handler).end()
		        // 只匹配1个条件的路由规则
		        // .rule().msgType("MSG_TYPE").handler(handler).end()
		        // 消息经过这个规则后可以继续尝试后面的路由规则
		        // .rule().msgType("MSG_TYPE").handler(handler).next()
		        // 另一个同步处理的路由规则
		        // .rule().async(false).msgType("MSG_TYPE").handler(handler).end()
		        // 添加了拦截器的路由规则
		        // .rule().msgType("MSG_TYPE").interceptor(interceptor).handler(handler).end()
		        // 语音消息，根据识别的字符串解析语义
		        .rule().async(true).msgType(WxConsts.XML_MSG_VOICE).handler(new VoiceHandler(this.xfyun_appid, this.flight_search_url, this.flight_user_token)).end()
		        // 兜底路由规则，一般放到最后
		        .rule().async(true).handler(new DefaultHandler(this.xfyun_appid, this.flight_search_url, this.flight_user_token)).end();
		return wxMpMessageRouter;
	}

}