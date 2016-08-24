package com.superhao.config.wx.handlers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.TextUnderstander;
import com.iflytek.cloud.speech.TextUnderstanderListener;
import com.superhao.semantic.business.CustomTextUnderstanderListener;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

public final class DefaultHandler implements WxMpMessageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHandler.class);
	private String xfyun_appid;
	private String flight_search_url;
	private String flight_user_token;

	public DefaultHandler(String xfyun_appid, String flight_search_url, String flight_user_token) {
		this.xfyun_appid = xfyun_appid;
		this.flight_search_url = flight_search_url;
		this.flight_user_token = flight_user_token;
	}

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
	        WxSessionManager sessionManager) throws WxErrorException {
		String echoMessage = "echo: " + wxMessage.getContent();
		LOGGER.info(echoMessage);

		// 创建文本语义理解对象
		TextUnderstander mTextUnderstander = TextUnderstander.createTextUnderstander(); // 开始语义理解
		mTextUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");  
		
		TextUnderstanderListener searchListener = new CustomTextUnderstanderListener(wxMessage, context, wxMpService, sessionManager, this.xfyun_appid, this.flight_search_url, this.flight_user_token);
		mTextUnderstander.understandText(wxMessage.getContent(), searchListener ); // 初始化监听器

		return null;
	}
}