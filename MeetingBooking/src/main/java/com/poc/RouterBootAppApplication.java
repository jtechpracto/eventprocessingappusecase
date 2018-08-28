package com.poc;
 
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.Main;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.ImportResource;

import java.io.File;
import java.io.IOException;

import com.poc.event.rest.interceptor.EventRequestAuthPlugin;
import com.poc.event.rest.sei.RestEndpointEventAPI;
@SpringBootApplication
@EnableAutoConfiguration(
		exclude={CamelAutoConfiguration.class,
				DataSourceAutoConfiguration.class,
				MongoAutoConfiguration.class,
				MongoDataAutoConfiguration.class,
				org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class
				 })
//@ImportResource(locations="file:/home/apps/MeetingBooking/cfg/startwrapper.xml")
//@ImportResource(locations="${app.context_xml}")
@ImportResource(locations="classpath:com/poc/main/config/appcontext.xml")
public class RouterBootAppApplication {

	@Autowired
	private Bus bus;
	
	
	@Autowired
	RestEndpointEventAPI rcsInterface;
	@Autowired  
	EventRequestAuthPlugin authPlugin;
	
	
	public static void main(String[] args) {
	 	//ConfigurableApplicationContext con = SpringApplication.run(RouterBootAppApplication.class, args);
		//<camelContext id="router_context" allowUseOriginalMessage="false"  streamCache="false"  xmlns="http://camel.apache.org/schema/spring">
		SpringApplication.run(RouterBootAppApplication.class, args);
		 
	}
	
	
	@Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setProvider(authPlugin);
        endpoint.setProvider(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
        endpoint.setServiceBeans(Arrays.<Object>asList(rcsInterface));
        endpoint.setAddress("/"); 
         return endpoint.create();
	}
	

    
	
}
