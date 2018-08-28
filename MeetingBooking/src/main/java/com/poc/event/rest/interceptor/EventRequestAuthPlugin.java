package com.poc.event.rest.interceptor;

 

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import org.apache.cxf.common.util.Base64Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.poc.event.model.User;
import com.poc.event.rest.cache.EventAppCacheInterface;
 
@Component
public class EventRequestAuthPlugin  implements ContainerRequestFilter {
	 
	@Autowired
	EventAppCacheInterface cacheContainerInterface;
 
	
   @Override
   public void filter(ContainerRequestContext requestContext) throws IOException {
       String header = requestContext.getHeaderString("Authorization");
       try {
			String[] parts = header.split(" ");
			if (parts.length != 2 || !"Basic".equals(parts[0])) {
			     throw new IOException("User Auth failed");
			}
			 
			String  decodedValue = new String(Base64Utility.decode(parts[1]));
			
			
			//String[] namePassword = decodedValue.split(":"); 
			if (processAuth(decodedValue,requestContext)) {
			    // let request to continue
			} else {
			   
			    throw new IOException("User Auth failed");
			     
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new IOException("User Auth failed");
		}
   }
   private   boolean processAuth(String key , ContainerRequestContext requestContext) {
		// TODO Auto-generated method stub
   	try{
		User user =  this.cacheContainerInterface.getUserFromCache(key.split(":")[1]);
   	if(user != null && user.isApiAccess())
   	{
   		//requestContext.getHeaders()
   		requestContext.setProperty("user", user); 
   		// Just Setting in case of we 
   		// further need this in request life cycle 
   		// like for user based validation or user needed to
   		// logged at the end.
   		return true;
   	 }
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
		return false;
	}
	private Response createFaultResponse() {
       return Response.status(401).header("WWW-Authenticate", "Provide Basic Auth").build();
   }
}