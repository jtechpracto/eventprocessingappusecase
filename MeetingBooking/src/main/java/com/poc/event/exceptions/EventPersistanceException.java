package com.poc.event.exceptions;

public class EventPersistanceException extends EventProcessingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2068982332774964318L;

	public EventPersistanceException(String errorCode, String errorDesc) {
		super(errorCode, errorDesc);
		// TODO Auto-generated constructor stub
	}

 
}
