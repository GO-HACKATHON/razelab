package com.razelab.hackathon.server.trainer.model;

import java.util.List;

public class EntityRequest {
	String entity;
	List<String> member;
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public List<String> getMember() {
		return member;
	}
	public void setMember(List<String> member) {
		this.member = member;
	}
	
	

}
