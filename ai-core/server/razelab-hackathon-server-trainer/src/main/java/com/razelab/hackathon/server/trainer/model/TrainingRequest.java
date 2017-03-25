package com.razelab.hackathon.server.trainer.model;

import java.util.List;

public class TrainingRequest {
	
	String inputString;
	String parentIntent;
	String subIntent;
	List<String> entity;
	
	public TrainingRequest(){
	}

	public String getInputString() {
		return inputString;
	}

	public void setInputString(String inputString) {
		this.inputString = inputString;
	}

	public String getParentIntent() {
		return parentIntent;
	}

	public void setParentIntent(String parentIntent) {
		this.parentIntent = parentIntent;
	}

	public String getSubIntent() {
		return subIntent;
	}

	public void setSubIntent(String subIntent) {
		this.subIntent = subIntent;
	}

	public List<String> getEntity() {
		return entity;
	}

	public void setEntity(List<String> entity) {
		this.entity = entity;
	}
	
	
	
	

}
