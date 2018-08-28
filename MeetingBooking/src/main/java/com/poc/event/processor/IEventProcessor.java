package com.poc.event.processor;

import com.poc.event.model.EventProcessingResponse;
import com.poc.event.model.EventRequest;

public interface IEventProcessor {
   public EventProcessingResponse processEvent(EventRequest eventRequest) throws Exception;
}
