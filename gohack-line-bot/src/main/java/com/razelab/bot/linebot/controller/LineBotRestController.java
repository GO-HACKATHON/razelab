package com.razelab.bot.linebot.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.razelab.bot.linebot.model.AiInput;
import com.razelab.bot.linebot.model.AiMessage;
import com.razelab.bot.linebot.model.AiProfile;
import com.razelab.bot.linebot.model.AiReply;
import com.razelab.bot.linebot.model.LineRequestEvent;
import com.razelab.bot.linebot.service.ReplyService;

import retrofit2.Response;

@RestController
public class LineBotRestController {
	
	
	@Autowired
	ReplyService replyService;

	@RequestMapping(path="/", method = RequestMethod.POST)
    public String botEngine(@RequestBody LineRequestEvent body, @RequestHeader HttpHeaders headers) throws IOException {
		
		System.out.println("Someone sent text");
		int eventN = body.getEvents().size();
		for(int i = 0;i<eventN;i++){
			MessageEvent<TextMessageContent> event = (MessageEvent<TextMessageContent>) body.getEvents().get(i);
			
			AiProfile profile = new AiProfile("line",event.getSource().getUserId());
			String profileName = getProfileName(profile.getIdNumber());
			//process AI output something
			AiReply test = new AiReply("film","none","Hi "+profileName+", ini adalah demo untuk Gojek Hackathon","movie","Beauty and The Beast");
			
			//Line Bot reply service
			replyService.compose(event, test);
		}
		
        return "Firman Bot Application";
    }
	
    public String getProfileName(String userId) throws IOException {
		Response<UserProfileResponse> response =
		        LineMessagingServiceBuilder
		                .create("c6gIJ4sO0S0PepP66P88aTdhByrUFFB+bQsUGIvYffgCfnGvC1G5xuxqFxZavtRnhG9JoZUZghaxTEalAXKlInnVmGZJ6dw+mLCpQ5VDMsgWavtKWvp3X9o6GG872mlkxEKzEyT4PXHiQAzs0HEAuwdB04t89/1O/w1cDnyilFU=")
		                .build()
		                .getProfile(userId)
		                .execute();
		if (response.isSuccessful()) {
		    UserProfileResponse profile = response.body();
		    //System.out.println(profile.getDisplayName());
		    //System.out.println(profile.getPictureUrl());
		    //System.out.println(profile.getStatusMessage());
		    return profile.getDisplayName();
		} else {
		    //System.out.println(response.code() + " " + response.message());
		}
		
		return "not detected";
    }
    
    @RequestMapping(path="/test", method = RequestMethod.POST)
    public String testEngine() throws IOException {
		
    	AiProfile profile = new AiProfile("line","12312312");
		AiMessage message = new AiMessage("hi man");
		AiInput aiInput = new AiInput(profile, message);
		ObjectMapper mapper = new ObjectMapper();
		
		HttpHeaders sendHeaders = new HttpHeaders();
		sendHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(aiInput), sendHeaders);
		
		RestTemplate restTemplateAi = new RestTemplate();
		ResponseEntity<String> aiReply = restTemplateAi.exchange("https://razeproject.com/ai/", HttpMethod.POST, entity, String.class);
		
		String test = aiReply.getBody();
		
		
        return test;
    }
    
}
