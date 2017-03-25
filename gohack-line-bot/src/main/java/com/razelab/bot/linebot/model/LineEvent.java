package com.razelab.bot.linebot.model;

import java.time.Instant;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;

public class LineEvent {

	String type;
	String replyToken;
	Source source;
	Instant timestamp;
	Message message;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReplyToken() {
		return replyToken;
	}
	public void setReplyToken(String replyToken) {
		this.replyToken = replyToken;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
	
}
