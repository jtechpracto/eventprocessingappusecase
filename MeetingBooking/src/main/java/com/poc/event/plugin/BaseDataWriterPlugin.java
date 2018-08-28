package com.poc.event.plugin;

import javax.annotation.PostConstruct;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.poc.event.datarepo.PersistanceDataTemplateProvider; 

public abstract class BaseDataWriterPlugin<T,V> implements IEventDataWriter<T>{
	@Autowired
	protected PersistanceDataTemplateProvider<V> daoAccessProvider;
		
	 
	@Autowired
	protected Environment env;
	
	@PostConstruct
	public void initialize() // throws Exception
	{
		// Suppressing Intentionally the exception ,  as this class object is made  with @Component
		// So for POC app will not startup if MONGO DB connectivity not  available ..
		// For production release better  throw some exception to ensure this service 
		// to be used with fully build
		try
		{
		daoAccessProvider.loadTemplate(env);
		}
		catch(Exception e)
		{
			
		}
	}
}
