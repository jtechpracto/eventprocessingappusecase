package com.poc.event.processor.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.poc.event.exceptions.EventProcessingException;
import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.model.EventProcessingResponse;
import com.poc.event.model.EventRequest;
import com.poc.event.plugin.IEventDataWriter;
import com.poc.event.plugin.IEventPlugin;
import com.poc.event.processor.IEventProcessor;
 

public class EventProcessor implements IEventProcessor {

	private List<IEventPlugin> eventProcessorPluginChain;
	public IEventDataWriter<EventRequest> getDataWriterPrimary() {
		return dataWriterPrimary;
	}

	public void setDataWriterPrimary(
			IEventDataWriter<EventRequest> dataWriterPrimary) {
		this.dataWriterPrimary = dataWriterPrimary;
	}

	public IEventDataWriter<EventRequest> getDataWriterFailover() {
		return dataWriterFailover;
	}

	public void setDataWriterFailover(
			IEventDataWriter<EventRequest> dataWriterFailover) {
		this.dataWriterFailover = dataWriterFailover;
	}


	private IEventDataWriter<EventRequest> dataWriterPrimary;
	private IEventDataWriter<EventRequest> dataWriterFailover;
	private static final Logger _log = Logger
			.getLogger(EventProcessor.class);
	
	public void setEventProcessorPluginChain(
			List<IEventPlugin> eventProcessorPluginChain) {
		this.eventProcessorPluginChain = eventProcessorPluginChain;
	}
	
	public List<IEventPlugin> getEventProcessorPluginChain() {
		return eventProcessorPluginChain;
	}
 
	
	@Override
	public EventProcessingResponse processEvent(EventRequest eventRequest)
			throws Exception {
		
		EventProcessingResponse resp = null;
		try {
			if(_log.isDebugEnabled())
			{
				_log.debug("Request:"+eventRequest.getInputPayload());
			}
			
			for (IEventPlugin plugin : eventProcessorPluginChain) {
				plugin.executePlugin(eventRequest);
			}
			
			persistEventPacket(eventRequest);
			
			resp = EventProcessingResponse.buildSuccessResponse();
			_log.info(resp);
		} catch (EventProcessingException e) {
			// TODO Auto-generated catch block
			resp = EventProcessingResponse.buildErrorResponse(e.getErrorCode(), e.getErrorDesc());
			_log.info(resp);
			if(_log.isDebugEnabled())
			{
				_log.error("Failed Event Processing",e);
			}
			
			if(eventRequest.isPropagationProcessingException())
				throw e;
		}
		 catch (Exception e) {
				// TODO Auto-generated catch block
				resp = EventProcessingResponse.buildErrorResponse(ExceptionCode.ERROR_CODE_03, e.toString());
				_log.info(resp);
				if(_log.isDebugEnabled())
				{
					_log.error("Failed Due to unexpected exception",e);
				}
				
				if(eventRequest.isPropagationProcessingException())
					throw e;
			}
	  
		return resp;
	}

	private void persistEventPacket(EventRequest eventRequest) throws EventProcessingException{
		// TODO Auto-generated method stub
		
		try{
			dataWriterPrimary.writeEventData(eventRequest);
		}
		catch(EventProcessingException exception)
		{
			dataWriterFailover.writeEventData(eventRequest);
		}
	}

}
