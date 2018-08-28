package com.poc.event.rest.sei.impl;

import javax.servlet.http.HttpServletRequest; 
import javax.ws.rs.core.Response;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.model.EventData;
import com.poc.event.model.EventProcessingResponse;
import com.poc.event.model.EventRequest;
import com.poc.event.processor.IEventProcessor;
import com.poc.event.rest.sei.RestEndpointEventAPI;
import com.poc.event.rest.service.EventOperationService;

@Component
public class RestEndpointEventAPIImpl implements RestEndpointEventAPI{
	 
	@Autowired
	@Qualifier(value="mongoOperationService")
	EventOperationService<EventData> mongoOperationService;
	
	@Autowired
	@Qualifier(value="sqlOperationService")
	EventOperationService<EventData> sqlOperationService;
 
	@Autowired
	@Qualifier(value="eventProcessorRef")
	IEventProcessor eventProcesor;
	
	/*
	 *ProducerTemplate only used to send request to JMS Queue for POC ..  
	 */
	@EndpointInject(uri="jms:EVENT_QUEUE")
	ProducerTemplate producer;
	
	@Override
	public Response sendEvent(HttpServletRequest request) {
		// TODO Auto-generated method stub
		EventRequest er = new EventRequest();
		EventProcessingResponse resp = null;
		try {
			er.setInputPayload(request.getParameter("input_payload"));
		   resp =  eventProcesor.processEvent(er);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp = EventProcessingResponse.buildErrorResponse(ExceptionCode.ERROR_CODE_03,ExceptionCode.ERROR_DESC_03 );
		}
		return Response.ok(resp).build();
	}

	@Override
	public Response deleteEvent(String eventId) {
		// TODO Auto-generated method stub
		 	int res = mongoOperationService.deleteEventData(eventId);
			if(res <=0  )
			{
				res = sqlOperationService.deleteEventData(eventId);
			}
			 
		return Response.ok(String.valueOf(res)).build();
	}

	@Override
	public Response getEvent(String eventId) {
		// TODO Auto-generated method stub
		EventData res = mongoOperationService.getEventData(eventId);
		if(res == null )
		{
			res = sqlOperationService.getEventData(eventId);
		}
		return Response.ok(res).build();
		 
	}

	@Override
	public Response sendEventToJMS(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String payload = request.getParameter("input_payload");
		producer.sendBody(request.getParameter("input_payload"));
		return Response.ok("Produced:"+payload).build();
	}

	 
   
	
}
