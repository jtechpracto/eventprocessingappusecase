package com.poc.event.model;

import java.util.Map;

public class EventRequest {
	String inputPayload;
	Map<String,String> dataToProces;
	boolean propagationProcessingException;
	
	 
	public boolean isPropagationProcessingException() {
		return propagationProcessingException;
	}
	public void setPropagationProcessingException(
			boolean propagationProcessingException) {
		this.propagationProcessingException = propagationProcessingException;
	}
	
	public void setInputPayload(String inputPayload) {
		this.inputPayload = inputPayload;
	}
	public String getInputPayload() {
		return inputPayload;
	}
	
	public void setDataToProces(Map<String, String> dataToProces) {
		this.dataToProces = dataToProces;
	}
	
	public Map<String, String> getDataToProces() {
		return dataToProces;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return inputPayload;
	}
	 
	}
