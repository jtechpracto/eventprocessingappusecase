package com.poc.event.rest.sei;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.poc.event.model.EventData;
import com.poc.event.model.EventProcessingResponse;
 
 
@Path("/")
@Service
public interface RestEndpointEventAPI {

/*
    @GET
    @Path("sendTemplateMessage")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED )
    Response sendTemplateMessageWithGet(@Context HttpServletRequest request);
    
    @POST
    @Path("sendTemplateMessage")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED )
    Response sendTemplateMessageWithPost(@Context HttpServletRequest request);
    */
	
	

    @POST
    @Path("produceEventToJMS")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED )
    @Produces({MediaType.TEXT_PLAIN})
    Response sendEventToJMS(@Context HttpServletRequest request);
    
    
	
    @POST
    @Path("sendEvent")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED )
    Response sendEvent(@Context HttpServletRequest request);
    
    @DELETE
    @Path("/deleteEvent/{event_id}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED )
    Response deleteEvent(@PathParam("event_id") String eventId);
    
    @GET
    @Path("/getEvent/{event_id}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED )
    Response getEvent(@PathParam("event_id") String eventId);
    
    
 }

 