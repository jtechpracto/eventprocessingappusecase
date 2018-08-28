package com.poc.event.processor.impl;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
 

@Component
public class ErrorProcessor implements  Processor{

	private static final Logger _log = Logger
			.getLogger(ErrorProcessor.class);
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		/*
		 * Log the error in some format later it can be be 
		 * some more sophisticated way to capturing the failures .
		 */
		StringBuilder errorLogBuf = new StringBuilder();
		errorLogBuf.append("\nLogging Error\nINPUT:").append(exchange.getIn().getBody()).
		append("\nOUTPUT:").append(exchange.getOut().getBody());
		_log.error(errorLogBuf);
  	
	}

}
