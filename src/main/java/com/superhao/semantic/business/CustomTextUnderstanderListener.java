package com.superhao.semantic.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.TextUnderstanderListener;
import com.iflytek.cloud.speech.UnderstanderResult;
import com.superhao.flights.model.FlightSchedule;
import com.superhao.flights.model.FlightSearchResponse;
import com.superhao.flights.utils.CityToAirportCode;
import com.superhao.semantic.model.Semantic;
import com.superhao.semantic.model.SemanticResponse;
import com.superhao.semantic.model.Slots;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.custombuilder.NewsBuilder;

public class CustomTextUnderstanderListener implements TextUnderstanderListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomTextUnderstanderListener.class);
	
	private WxMpXmlMessage wxMessage;
	private Map<String, Object> context;
	private WxMpService wxMpService;
    private WxSessionManager sessionManager;
	private String xfyun_appid;

	private String flight_search_url;

	private String flight_user_token;
	
	public CustomTextUnderstanderListener(WxMpXmlMessage wxMessage, Map<String, Object> context,
	        WxMpService wxMpService, WxSessionManager sessionManager, String xfyun_appid, String flight_search_url, String flight_user_token) {
		super();
		this.wxMessage = wxMessage;
		this.context = context;
		this.wxMpService = wxMpService;
		this.sessionManager = sessionManager;
		
		this.xfyun_appid = xfyun_appid;
		this.flight_search_url = flight_search_url;
		this.flight_user_token = flight_user_token;
		
		SpeechUtility.createUtility(SpeechConstant.APPID + "=" + this.xfyun_appid);
	}

	// 语义结果回调
	public void onResult(UnderstanderResult result) {
		String resultString = result.getResultString();
		LOGGER.info("onResult = " + resultString);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			SemanticResponse response = objectMapper.readValue(resultString, SemanticResponse.class);
			if (response.getRc() == 0) {
				Semantic sematic = response.getSemantic();
				Slots slots = sematic.getSlots();
				if(slots.getStartLoc()!=null && slots.getEndLoc()!=null && slots.getStartDate()!=null) {
					String fromCity = CityToAirportCode.toCode(slots.getStartLoc().getCityAddr());
					String toCity = CityToAirportCode.toCode(slots.getEndLoc().getCityAddr());
					LOGGER.info("fromCity = {}, toCity = {}",fromCity,toCity);
					if(fromCity == null) {
						fromCity = "SHA";
					}
					if(toCity == null) {
						toCity = "BJS";
					}
					String fromDate = slots.getStartDate().getDate();
					String request = "{  \"fromCity\": \""+fromCity+"\",  \"toCity\": \""+toCity+"\",  \"fromDate\": \"" + fromDate +"\",  \"sort\": \"date\",  \"spName\":\"tdx\",  \"orderRule\":\"asc\",  \"userToken\":\""
							+ this.flight_user_token+"\",  \"operation\": \"SEARCHLOWEST\" } ";
					RestTemplate rt = new RestTemplate();
					// 机票搜索服务
					String stringResponse =  rt.postForObject(this.flight_search_url, request, String.class);
					stringResponse = new String(stringResponse.getBytes("ISO-8859-1"), "UTF-8");
					
					FlightSearchResponse flightSearchResponse = objectMapper.readValue(stringResponse, FlightSearchResponse.class);
					if(flightSearchResponse.getRescode().equals("0")) {
						List<FlightSchedule> schedules = flightSearchResponse.getFlightSchedules();
						
						WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
						article1.setUrl("#");
						article1.setPicUrl("http://pngimg.com/upload/plane_PNG5249.png");
						article1.setDescription("总共"+schedules.size()+"条航班");
						article1.setTitle(slots.getStartLoc().getCityAddr()+"到"+slots.getEndLoc().getCityAddr()+"的前5条航班");
						List<WxMpCustomMessage.WxArticle> articles = new ArrayList<>();
						articles.add(article1);
						
						for (int i = 0; i < schedules.size() && i < 5; i++) {
							FlightSchedule flightSchedule = schedules.get(i);
							String line = flightSchedule.getAirlineCompany() +" - " +flightSchedule.getFlightNo() +" - ￥:"+ flightSchedule.getLowestPrice()
							+" - 起飞:"+ flightSchedule.getFromDate().substring(0,flightSchedule.getFromDate().indexOf(".")) 
							+" - 到达:"+ flightSchedule.getToDate().substring(0,flightSchedule.getToDate().indexOf(".")) ;
							
							WxMpCustomMessage.WxArticle article2 = new WxMpCustomMessage.WxArticle();
							article2.setUrl("#");
							article2.setPicUrl("http://pngimg.com/upload/plane_PNG5249.png");
							article2.setDescription("确认订票？");
							article2.setTitle(line);
							articles.add(article2);
							
						}
						NewsBuilder builder = WxMpCustomMessage.NEWS().toUser(wxMessage.getFromUserName());
						for (WxMpCustomMessage.WxArticle wxArticle : articles) {
							builder.addArticle(wxArticle);
						}
						WxMpCustomMessage message = builder.build();
						// 设置消息的内容等信息
						wxMpService.customMessageSend(message);
						
					}  else {
						LOGGER.error(flightSearchResponse.toString());
					}
				} else {
					LOGGER.equals(slots.toString());
				}
			}
		} catch (WxErrorException e) {
			LOGGER.error("Failed to send custom message", e);
		}catch (Exception e1) {
			LOGGER.error("Unexpected exception", e1);
		} 
	}

	// 语义错误回调
	public void onError(SpeechError error) {
		WxMpCustomMessage message = WxMpCustomMessage.TEXT().content("echo:"+wxMessage.getContent())
		        .toUser(wxMessage.getFromUserName()).build();;
		try {
			wxMpService.customMessageSend(message);
		} catch (WxErrorException e) {
			LOGGER.error("Failed to send custom message", e);
		}
		LOGGER.error("Failed to recognize content" + error.toString(), error.getCause());
	}
}