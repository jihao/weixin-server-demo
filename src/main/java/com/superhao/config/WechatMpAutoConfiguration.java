package com.superhao.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

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
		        .handler(getInviteHandler()).end()
		        // 关注事件
		        .rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE)
		        .handler(getSubscribeHandler()).end()
		        // .content("CONTENT").rContent("content正则表达式").handler(handler).end()
		        // 只匹配1个条件的路由规则
		        // .rule().msgType("MSG_TYPE").handler(handler).end()
		        // 消息经过这个规则后可以继续尝试后面的路由规则
		        // .rule().msgType("MSG_TYPE").handler(handler).next()
		        // 另一个同步处理的路由规则
		        // .rule().async(false).msgType("MSG_TYPE").handler(handler).end()
		        // 添加了拦截器的路由规则
		        // .rule().msgType("MSG_TYPE").interceptor(interceptor).handler(handler).end()
		        // 兜底路由规则，一般放到最后
		        .rule().async(false).handler(getDefaultHandler()).end();
		return wxMpMessageRouter;
	}

	/**
	 * 邀请关注, 生成用户所属公司的二维码，假设先前关注过的用户，已经设置了companyId
	 * 
	 * @return 一个图文消息，包含邀请二维码
	 */
	private WxMpMessageHandler getInviteHandler() {
		WxMpMessageHandler handler = new WxMpMessageHandler() {

			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
		            WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
				LOGGER.info("wxMpService==null ? " + (wxMpService == null));
				WxMpUser user = wxMpService.getUserService().userInfo(wxMessage.getFromUserName(), "zh_CN");
				// 后台逻辑，获得此用户的公司id，这里是 hard code
				String companyId = "quancheng-ec";

				WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(companyId);
				// "gQFF8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL3Ewd2Y5U2JscUMtUlJ2OVFjMlFMAAIEez6pVwMEAAAAAA==";
				String qrCodePictureUrl = wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
				LOGGER.info("qrCodePictureUrl: " + qrCodePictureUrl);
				WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
				item.setDescription(user.getNickname() + " 邀请您加入公司：" + companyId);
				item.setPicUrl(qrCodePictureUrl);
				item.setTitle("邀请加入");
				item.setUrl(qrCodePictureUrl);

				WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS().fromUser(wxMessage.getToUserName())
		                .toUser(wxMessage.getFromUserName()).addArticle(item).build();

				LOGGER.error("outMessage=" + m.toXml());
				return m;
			}
		};
		return handler;
	}

	/**
	 * 关注事件
	 * 
	 * @return 一个文本消息，提示用户继续完善个人信息，完成绑定流程
	 */
	private WxMpMessageHandler getSubscribeHandler() {
		WxMpMessageHandler handler = new WxMpMessageHandler() {

			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
		            WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
				String eventKey = wxMessage.getEventKey(); // qrscene_quancheng-ec
				String companyId = eventKey;
				LOGGER.info("company id: " + companyId);

				WxMpUser user = wxMpService.getUserService().userInfo(wxMessage.getFromUserName(), "zh_CN");
				String content = "Hi " + user.getNickname() + ", 还差一步<a href=\"#?user=" + user.getOpenId() + "&company="
		                + companyId + "\">完善个人信息</a> 就可以开始申请购票了";

				WxMpXmlOutTextMessage m = WxMpXmlOutTextMessage.TEXT().content(content)
		                .fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();

				LOGGER.error("outMessage" + m.toXml());
				return m;
			}
		};
		return handler;
	}

	private WxMpMessageHandler getDefaultHandler() {
		WxMpMessageHandler handler = new WxMpMessageHandler() {

			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
		            WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
				String echoMessage = "echo: " + wxMessage.getContent();
				System.out.println(echoMessage);
				WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(echoMessage)
		                .fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
				LOGGER.error("outMessage" + m.toXml());
				return m;
			}
		};
		return handler;
	}
}