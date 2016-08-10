package com.superhao.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

@Controller
@RequestMapping("/weixin")
public class WeiXinController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinController.class);
	@Autowired
	WxMpConfigStorage wxMpConfigStorage;
	
	@Autowired
	WxMpService wxMpService;

	@Autowired
	WxMpMessageRouter wxMpMessageRouter;
	
	@RequestMapping(value = "/connect", method = { RequestMethod.GET })
	@ResponseBody
	public String connect(@RequestParam String signature, @RequestParam String nonce, @RequestParam String timestamp,
	        @RequestParam String echostr) {
		/**
		 * 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，
		 * 则接入生效，成为开发者成功，否则接入失败。
		 * 
		 * 加密/校验流程如下： 1. 将token、timestamp、nonce三个参数进行字典序排序 2.
		 * 将三个参数字符串拼接成一个字符串进行sha1加密 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		 */
		System.out.println(wxMpService == null);
		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			return "非法请求";
		}

		return echostr;
	}

	/**
	 * 微信服务器POST过来的数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/connect", method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public String processRequest(HttpServletRequest request, @RequestParam String nonce, @RequestParam String timestamp,
	        @RequestParam(defaultValue = "raw") String encrypt_type) throws IOException {

		WxMpXmlMessage inMessage = null;

		if ("raw".equals(encrypt_type)) {
			// 明文传输的消息
			inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
		} else if ("aes".equals(encrypt_type)) {
			// 是aes加密的消息
			String msgSignature = request.getParameter("msg_signature");
			inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpConfigStorage, timestamp, nonce,
			        msgSignature);
		} else {
			return "不可识别的加密类型";
		}

		WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);

		if (outMessage != null) {
			if ("raw".equals(encrypt_type)) {
				return outMessage.toXml();
			} else if ("aes".equals(encrypt_type)) {
				return outMessage.toEncryptedXml(wxMpConfigStorage);
			}
		} else {
			LOGGER.error("outMessage is null");
		}
		return "服务号不可用";
	}

}
