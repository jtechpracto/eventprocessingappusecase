package com.poc.event.plugin;
 
import com.poc.event.exceptions.EventProcessingException;
import com.poc.event.model.EventRequest;

public interface IEventPlugin{
	public  void  executePlugin(EventRequest request) throws EventProcessingException;
}
