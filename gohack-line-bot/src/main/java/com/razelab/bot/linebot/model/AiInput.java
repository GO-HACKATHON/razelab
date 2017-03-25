package com.razelab.bot.linebot.model;

public class AiInput {
	
	AiProfile inputProfile;
	AiMessage inputMessage;
	
	
	
	public AiInput(AiProfile inputProfile, AiMessage inputMessage) {
		super();
		this.inputProfile = inputProfile;
		this.inputMessage = inputMessage;
	}
	public AiProfile getInputProfile() {
		return inputProfile;
	}
	public void setInputProfile(AiProfile inputProfile) {
		this.inputProfile = inputProfile;
	}
	public AiMessage getInputMessage() {
		return inputMessage;
	}
	public void setInputMessage(AiMessage inputMessage) {
		this.inputMessage = inputMessage;
	}
	
	@Override
	public String toString() {
		return "AiInput [inputProfile=" + inputProfile + ", inputMessage=" + inputMessage + "]";
	}
	
	
	
	
}
