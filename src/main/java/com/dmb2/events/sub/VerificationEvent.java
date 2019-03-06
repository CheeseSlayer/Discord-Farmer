package com.dmb2.events.sub;

public class VerificationEvent implements IEvent { 
	
	private String code;
	
	public VerificationEvent(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
}
