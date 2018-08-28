package com.poc.event.plugin;


import com.poc.event.exceptions.EventProcessingException;

public interface IEventDataWriter<T> {

	void writeEventData(T dataToWrite) throws EventProcessingException;	
}
