package com.razelab.hackathon.dashboard.admin.model;

import java.util.List;

public class EngineEntity {

	String entity;
	List<String> member;
	
	public EngineEntity(){
		
	}
	
	public EngineEntity(String entity, List<String> member) {
		super();
		this.entity = entity;
		this.member = member;
	}
	
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
