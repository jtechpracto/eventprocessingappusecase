package com.poc.event.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;
import com.poc.event.datarepo.PersistanceDataTemplateProvider;
import com.poc.event.model.EventData;
import com.poc.event.rest.service.EventOperationService;

@Service(value="mongoOperationService")
public class EventMongoOperationService implements
		EventOperationService<EventData> {

	@Autowired
	PersistanceDataTemplateProvider<MongoTemplate> mongoDaoService;

	@Override
	public EventData getEventData(String eventId) {
		// TODO Auto-generated method stub
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(eventId));
			return mongoDaoService.getTemplate().findOne(query, EventData.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public int deleteEventData(String eventId) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			WriteResult ob = null;
			Query query = new Query();

			query.addCriteria(Criteria.where("_id").is(eventId));

			ob = mongoDaoService.getTemplate().remove(query,
					EventData.class);
			return ob.getN();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
 

	}

}
