package com.poc.event.rest.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;  
import java.sql.SQLException;  

import com.poc.common.CommonEventConstants;
import com.poc.event.datarepo.PersistanceDataTemplateProvider;
import com.poc.event.model.EventData;
import com.poc.event.model.SensorOrientation;
import com.poc.event.model.SensorType;
import com.poc.event.rest.service.EventOperationService;

@Service(value="sqlOperationService")
public class EventSqlOperationService implements
		EventOperationService<EventData> {

	@Value("${rdbms.select_event_qry}")
	private String selectQuery;
	@Value("${rdbms.del_event_qry}")
	private String deleteQuery;
	 
	@Autowired
	PersistanceDataTemplateProvider<NamedParameterJdbcTemplate> sqlDaoService;

	@Override
	public EventData getEventData(String eventId) {
		// TODO Auto-generated method stub
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put(CommonEventConstants.CONST_EVENT_ID,eventId);
		EventData ob = null; 
		
		try {
			ob = (EventData)sqlDaoService.getTemplate().
					queryForObject(
							selectQuery,parameters,
							new RowMapper<EventData>(){  
								@Override  
								public EventData mapRow(ResultSet rs, int rownumber) throws SQLException {  
									EventData e=new EventData();  
									buildEventData(rs,e);
									return e;  
								}
								
								public void buildEventData(ResultSet rawData,EventData ev)
								{
									try {
										ev.setEventId(rawData.getString(CommonEventConstants.CONST_EVENT_ID));
										ev.setSensorType(SensorType.valueOf(rawData.getString(CommonEventConstants.CONST_SENSOR_TYPE)));
										ev.setSensorReading(rawData.getString(CommonEventConstants.CONST_SENSOR_READING));
										ev.setDeviceId(rawData.getString(CommonEventConstants.CONST_SENSOR_DEV_ID));
										ev.setEventDate(rawData.getDate(CommonEventConstants.CONST_EVENT_DATE));
										ev.setOrientation( SensorOrientation.build(rawData.getString(CommonEventConstants.CONST_SENSOR_ORIENTATION)));
									} catch (Exception e) {
										// Do some action here may be we can propogate by declaring this method throws ...
									}
									
								}
								
								});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Do some logging here .... 
			Exception e1 = e;
			e.printStackTrace();
		}
		return ob;
		
		}


	@Override
	public int deleteEventData(String eventId) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			Map<String,String> parameters = new HashMap<String,String>();
			parameters.put(CommonEventConstants.CONST_EVENT_ID,eventId);
			return  sqlDaoService.getTemplate().update(deleteQuery, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}

	}

}
