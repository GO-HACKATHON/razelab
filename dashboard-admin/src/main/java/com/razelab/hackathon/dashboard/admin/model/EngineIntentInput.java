package com.razelab.hackathon.dashboard.admin.model;

public class EngineIntentInput {

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
	public void setIntent(String currentIntent) {
		this.currentIntent = currentIntent;
	}
	
	
	
}
