package com.poc.event.exceptions;

public class EventDataValidationException extends EventProcessingException  {

	public EventDataValidationException(String errorCode, String errorDesc) {
		super(errorCode, errorDesc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2347314926727715721L;
}
