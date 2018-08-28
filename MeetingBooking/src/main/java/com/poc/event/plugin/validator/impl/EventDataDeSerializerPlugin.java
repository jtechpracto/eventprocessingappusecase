package com.poc.event.plugin.validator.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.poc.common.CommonEventConstants; 
import com.poc.event.exceptions.EventProcessingException;
import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.exceptions.EventDataValidationException;
import com.poc.event.model.EventRequest;
import com.poc.event.plugin.IEventPlugin;
import com.poc.event.processor.impl.EventProcessor;

@Component(value="eventDeserializer")
public class EventDataDeSerializerPlugin implements IEventPlugin{

	/*
	 * Environment API to read  application properties  
	 */
	
	@Autowired
	private Environment env;
	
	private String delimeter;
	private String[] parametersInOrder;
	private static final Logger _log = Logger
			.getLogger(EventDataDeSerializerPlugin.class);
	
	
	
	@PostConstruct
	public void initialize()
	{
		parametersInOrder = env.getProperty(CommonEventConstants.CONST_PARAMS_IN_ORDER).split(",");
		delimeter = env.getProperty(CommonEventConstants.CONST_PARAMS_DELIMETER, "\\|");
	}
	@Override
	public void  executePlugin(EventRequest eventRequest) throws  EventProcessingException{
	
		String eventPacket = eventRequest.getInputPayload();
		if(StringUtils.isEmpty(eventPacket))
			throw new EventDataValidationException(ExceptionCode.ERROR_CODE_01,ExceptionCode.ERROR_DESC_01+":EventData is Blank");
		String delm = "\\|";
		boolean eq = delm.equals(delimeter);
		String[] splittedEventPacket = eventPacket.split(delimeter);
		
		if(splittedEventPacket.length != parametersInOrder.length)
		{
			_log.error("Failed Due to "+ExceptionCode.ERROR_CODE_01);
			if(_log.isDebugEnabled())
			{
				_log.debug("RawEvent"+eventRequest.getDataToProces());
			}
			throw new EventDataValidationException(ExceptionCode.ERROR_CODE_07,ExceptionCode.ERROR_DESC_07);
		}
		
		Map<String,String> eventDataMap = new HashMap<String,String>();
		
		for(int i = 0; i< splittedEventPacket.length ; i++)
		{
			eventDataMap.put(parametersInOrder[i],splittedEventPacket[i]);
		}
		
		eventRequest.setDataToProces(eventDataMap);
	}

}
