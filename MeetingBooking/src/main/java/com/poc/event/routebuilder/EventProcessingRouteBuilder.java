package com.poc.event.routebuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.poc.event.exceptions.EventPersistanceException; 
import com.poc.event.model.EventRequest;
import com.poc.event.processor.IEventProcessor;

public class EventProcessingRouteBuilder extends RouteBuilder {
	
	private String startEndpoint;
	
	@Value( "${route.max_error_retry}")
	private String maxErrorRetry;
	@Value( "${route.redelivery_delay}")
	private String redeliveryDelay;
	
	private IEventProcessor eventProcessor;
	
	@Autowired
	private Processor errorProcessor;
	
	public void setStartEndpoint(String startEndpoint) {
		this.startEndpoint = startEndpoint;
	}
	
	public String getStartEndpoint() {
		return startEndpoint;
	}
	
	public Processor getErrorProcessor() {
		return errorProcessor;
	}
	
	public void setEventProcessor(IEventProcessor eventProcessor) {
		this.eventProcessor = eventProcessor;
	}
	
	public IEventProcessor getEventProcessor() {
		return eventProcessor;
	}
	
	
	@Override
	public void configure() throws Exception {

		onException(EventPersistanceException.class).maximumRedeliveries(maxErrorRetry).redeliveryDelay(redeliveryDelay)
		.process(errorProcessor).handled(true);
		
		onException(Exception.class).maximumRedeliveries(0)
		.process(errorProcessor).handled(true);
	
		from(startEndpoint).process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				// TODO Auto-generated method stub
				String body =exchange.getIn().getBody(String.class);
				EventRequest eventRequest = new EventRequest();
				eventRequest.setPropagationProcessingException(true);
				eventRequest.setInputPayload(body);
				exchange.getIn().setBody(eventRequest);
			}
		}).bean(eventProcessor);
	}

}
