package com.poc.event.plugin.datawriter.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.poc.event.exceptions.EventPersistanceException;
import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.model.EventRequest;
import com.poc.event.plugin.BaseDataWriterPlugin;

@Component(value="sqlDataWriter")
public class RDBMSDataWriterPlugin extends
		BaseDataWriterPlugin<EventRequest, NamedParameterJdbcTemplate> {

	@Value("${rdbms.insert_query}")
	private String insertQuery;
	
	private static final Logger _log = Logger
			.getLogger(RDBMSDataWriterPlugin.class);
	
	@Override
	public void writeEventData(EventRequest eventRequest)
			throws EventPersistanceException {
		try {
			daoAccessProvider.getTemplate().update(insertQuery,
					eventRequest.getDataToProces());
		} catch (Exception e) {

			_log.error("Event Data Cant be save Due to RDBMSDataWriterPlugin Exception",e);
			
			throw new EventPersistanceException(ExceptionCode.ERROR_CODE_04,
					ExceptionCode.ERROR_DESC_04 + ":RDBMS");

		}
	}

}
