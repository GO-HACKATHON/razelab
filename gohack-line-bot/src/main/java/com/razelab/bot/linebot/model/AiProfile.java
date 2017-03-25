package com.razelab.bot.linebot.model;

public class AiProfile {
	String idType;
	String idNumber;
	
	
	
	public AiProfile(String idType, String idNumber) {
		super();
		this.idType = idType;
		this.idNumber = idNumber;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	
}
