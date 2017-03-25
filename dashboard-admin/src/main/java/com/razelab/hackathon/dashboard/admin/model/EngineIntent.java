package com.razelab.hackathon.dashboard.admin.model;

public class EngineIntent {

	String sentence;
	String parentIntent;
	String currentIntent;
	
	public EngineIntent(String sentence, String parentIntent, String currentIntent) {
		super();
		this.sentence = sentence;
		this.parentIntent = parentIntent;
		this.currentIntent = currentIntent;
	}
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
