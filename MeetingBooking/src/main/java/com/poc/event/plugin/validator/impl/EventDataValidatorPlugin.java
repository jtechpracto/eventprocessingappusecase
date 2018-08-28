package com.poc.event.plugin.validator.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.poc.common.CommonEventConstants;
import com.poc.event.exceptions.EventProcessingException;
import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.exceptions.EventDataValidationException;
import com.poc.event.model.EventData;
import com.poc.event.model.EventRequest;
import com.poc.event.model.SensorOrientation;
import com.poc.event.plugin.IEventPlugin;
 
@Component(value="eventDataValidator")
public class EventDataValidatorPlugin implements
		IEventPlugin  {

	/*
	 * Environment API to read application properties
	 */


	public static FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private Environment env;
	List<String> mandatoryParameters = new ArrayList<String>();
	private static final Logger _log = Logger
			.getLogger(EventDataDeSerializerPlugin.class);
	
	
	@PostConstruct
	public void initialize() {
		String arr[] = env.getProperty(
				CommonEventConstants.CONST_MANDATORY_PARAMS, "").split(",");
		for (String param : arr) {
			mandatoryParameters.add(param);
		}
	}

	 

	private void checkMandatoryParameter(Map<String, String> sourceEvent)
			throws EventDataValidationException {
		// TODO Auto-generated method stub
		
		for (String param : mandatoryParameters) {
			String eventVal = sourceEvent.get(param);
			if (StringUtils.isEmpty(eventVal)) {
				
				if(_log.isDebugEnabled())
				{
					_log.error("Failed Due to "+ExceptionCode.ERROR_CODE_02+":"+param);
				}
				throw new EventDataValidationException(ExceptionCode.ERROR_CODE_02,ExceptionCode.ERROR_DESC_02+":"+param);
			}
			
			SensorOrientation.build(sourceEvent.get(CommonEventConstants.CONST_SENSOR_ORIENTATION));
		}
	}
	
	private void validateSensorOrientation(Map<String, String> eventPacket) throws EventDataValidationException
	{
		SensorOrientation.build(eventPacket.get(CommonEventConstants.CONST_SENSOR_ORIENTATION));
 
	}
	
	public void vailidateEventDataDate(Map<String, String> eventPacket) throws EventDataValidationException
	{
		try{
		String date= eventPacket.get(CommonEventConstants.CONST_EVENT_DATE);
		if(StringUtils.isEmpty(date))
		{
			date = dateFormat.format(Calendar.getInstance().getTime());
		}
		dateFormat.parse(date); 
		}
		catch(Exception e)
		{
		throw new EventDataValidationException(ExceptionCode.ERROR_CODE_05,ExceptionCode.ERROR_DESC_05);
		}
	}
	
	@Override
	public void executePlugin(EventRequest eventRequest)
			throws EventProcessingException {
		// TODO Auto-generated method stub
		Map<String,String> eventPacket = eventRequest.getDataToProces();
		checkMandatoryParameter(eventPacket);
		validateSensorOrientation(eventPacket);
		vailidateEventDataDate(eventPacket);
	}

}
