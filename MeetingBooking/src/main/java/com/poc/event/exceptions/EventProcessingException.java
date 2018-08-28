package com.poc.event.exceptions;

public class EventProcessingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1993468034756336482L;

	protected String errorCode;
	protected String errorDesc;
	
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	
	public EventProcessingException(String errorCode, String errorDesc) {
		// TODO Auto-generated constructor stub
		super(errorCode+":"+errorDesc);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		
	}

}
