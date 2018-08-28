package com.poc.event.rest.service;

public interface EventOperationService<T> {

	 public T getEventData(String eventId) ;
	 public int deleteEventData(String eventId);
}
