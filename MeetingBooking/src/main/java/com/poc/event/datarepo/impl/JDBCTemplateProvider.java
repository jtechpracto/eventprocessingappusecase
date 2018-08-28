package com.poc.event.datarepo.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.poc.event.datarepo.PersistanceDataTemplateProvider;

@Component 
public class JDBCTemplateProvider implements PersistanceDataTemplateProvider<NamedParameterJdbcTemplate> {

	private NamedParameterJdbcTemplate template;
	

 
	@Override
	public void loadTemplate(Environment env) {
		// TODO Auto-generated method stub
		
 
		DataSource dataSource =  DataSourceBuilder.create().
				driverClassName(env.getProperty("rdbms.conn.driverClassName")).
				url(env.getProperty("rdbms.conn.url")).
				username(env.getProperty("rdbms.conn.username")).
				password(env.getProperty("rdbms.conn.password"))
				.build();
	    template = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public NamedParameterJdbcTemplate getTemplate() {
		// TODO Auto-generated method stub
		return template;
	}

}
