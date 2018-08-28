package com.poc.event.plugin.datawriter.impl;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.poc.event.exceptions.EventPersistanceException;
import com.poc.event.exceptions.EventProcessingException;
import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.model.EventData;
import com.poc.event.model.EventRequest;
import com.poc.event.plugin.BaseDataWriterPlugin; 

@Component(value="mongoDbDataWriter")

public class MongoDBDataWriterPlugin extends
		BaseDataWriterPlugin<EventRequest, MongoTemplate> {

	private static final Logger _log = Logger
			.getLogger(MongoDBDataWriterPlugin.class);
	
	
	@Override
	public void writeEventData(EventRequest eventRequest)
			throws EventProcessingException {
		// TODO Auto-generated method stub
		EventData eventData = new EventData();
		eventData.buildEventData(eventRequest.getDataToProces());
		try {
			daoAccessProvider.getTemplate().save(eventData);
		} catch (Exception e) {
			_log.error("Event Data Cant be save Due to MongoDBDataWriterPlugin Exception",e);
			
			throw new EventPersistanceException(ExceptionCode.ERROR_CODE_04,
					ExceptionCode.ERROR_DESC_04 + ": MongoDBDataWriterPlugin Writing Error");
		}

	}

}
