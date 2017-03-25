package com.razelab.bot.linebot.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AiReply {
	
	String timestamp;
	String intent;//message, confirmation, 
	String topic;
	String message;
	String options;//list, photo, URI
	String optionValue;
	
	
	
	public AiReply(String intent, String topic, String message, String options,
			String optionValue) {
		super();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		this.timestamp  = dateFormat.format(new Date());
		this.intent = intent;
		this.topic = topic;
		this.message = message;
		this.options = options;
		this.optionValue = optionValue;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

}
