package com.poc.event.datarepo.impl;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.poc.event.datarepo.PersistanceDataTemplateProvider;

@Component
public class MongoDBTemplateProvider implements PersistanceDataTemplateProvider<MongoTemplate>{

	 
	private MongoTemplate mongoTempate;
	
	@Override
	public void loadTemplate(Environment env) {
		
		MongoClient mclient = null;
		MongoMappingContext mongomappingContext = new MongoMappingContext();
		MongoClientOptions mooptions = MongoClientOptions
					.builder()
					.connectTimeout(1000)
					.socketTimeout(1000)
					.serverSelectionTimeout(1500)
					
					.connectionsPerHost(
							Integer.parseInt(env.getProperty("mongodb.connections-per-host","10"))) // Read this value from configuration 
					.minConnectionsPerHost(
							Integer.parseInt(env.getProperty("mongodb.min-connections-per-host","2")))
					.readPreference(ReadPreference.valueOf(env.getProperty("mongodb.read-preference","primaryPreferred")))
					.writeConcern(WriteConcern.valueOf(env.getProperty("mongodb.write-concern","ACKNOWLEDGED")))
					.build();

		mclient = new MongoClient(env.getProperty("mongodb.mongoClients","localhost:27017"), mooptions);
			

			SimpleMongoDbFactory smfactory = new SimpleMongoDbFactory(
					mclient, env.getProperty("mongodb.dbname","test"));
			DefaultMongoTypeMapper dmtypem = new DefaultMongoTypeMapper(null);
			MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(
					smfactory, mongomappingContext);
			mappingMongoConverter.setTypeMapper(dmtypem);
			mongoTempate = new MongoTemplate(smfactory,
					mappingMongoConverter);	
	}

	@Override
	public MongoTemplate getTemplate() {
		// TODO Auto-generated method stub
		return mongoTempate;
	}

 

}
