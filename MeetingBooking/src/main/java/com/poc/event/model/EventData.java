package com.poc.event.model;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.poc.common.CommonEventConstants;
import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.exceptions.EventDataValidationException;

@Document(collection = "event_data")
public class EventData {
	
	@Id
	@Indexed(name="_id")
	private String eventId;
	
	private Date eventDate;
	
	private String deviceId;
	
	/*
	 * Attributes related to the Sensor
	 */

	public static FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	private SensorOrientation orientation;
	private SensorType sensorType;
	private String sensorReading;

	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public SensorOrientation getOrientation() {
		return orientation;
	}
	public void setOrientation(SensorOrientation orientation) {
		this.orientation = orientation;
	}
	public SensorType getSensorType() {
		return sensorType;
	}
	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}
	public String getSensorReading() {
		return sensorReading;
	}
	public void setSensorReading(String sensorReading) {
		this.sensorReading = sensorReading;
	}
	
	public void buildEventData(Map<String,String> rawData) throws EventDataValidationException
	{
		try {
			setEventId(rawData.get(CommonEventConstants.CONST_EVENT_ID));
			setSensorType(SensorType.valueOf(rawData.get(CommonEventConstants.CONST_SENSOR_TYPE)));
			setSensorReading(rawData.get(CommonEventConstants.CONST_SENSOR_READING));
			setDeviceId(rawData.get(CommonEventConstants.CONST_SENSOR_DEV_ID));
			String date= rawData.get(CommonEventConstants.CONST_EVENT_DATE);
			setEventDate(dateFormat.parse(date));
			setOrientation( SensorOrientation.build(rawData.get(CommonEventConstants.CONST_SENSOR_ORIENTATION)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new EventDataValidationException(ExceptionCode.ERROR_CODE_05,ExceptionCode.ERROR_DESC_05+" Sensor Parameters Can not be build");
		}
		
	}
		
	}
