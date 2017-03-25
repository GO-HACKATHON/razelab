package com.razelab.hackathon.server.trainer.model;

public class IntentRequest {
	String sentence;
	String parentIntent;
	String currentIntent;
	
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public String getParentIntent() {
		return parentIntent;
	}
	public void setParentIntent(String parentIntent) {
		this.parentIntent = parentIntent;
	}
	public String getCurrentIntent() {
		return currentIntent;
	}
	public void setCurrentIntent(String currentIntent) {
		this.currentIntent = currentIntent;
	}
	
	
	
	

}
