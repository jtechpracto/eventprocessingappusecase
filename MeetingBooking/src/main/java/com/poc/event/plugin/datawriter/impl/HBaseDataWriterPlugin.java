package com.poc.event.plugin.datawriter.impl;

import com.poc.event.exceptions.EventProcessingException;
import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.model.EventRequest;
import com.poc.event.plugin.IEventDataWriter;

public class HBaseDataWriterPlugin  implements IEventDataWriter<EventRequest> {

	
	@Override
	public void writeEventData(EventRequest eventRequest)
			throws EventProcessingException {
	 	throw new EventProcessingException(ExceptionCode.ERROR_CODE_03,ExceptionCode.ERROR_DESC_03+":hbase feature Not available");

	}
}