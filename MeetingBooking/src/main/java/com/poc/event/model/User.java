package com.poc.event.model;

public class User {
 String user;
 private String pass;
 private boolean apiAccess;
 
 public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
public boolean isApiAccess() {
	return apiAccess;
}
public void setApiAccess(boolean apiAccess) {
	this.apiAccess = apiAccess;
}
// This is just for testing the POC ,  in real environment this will not be required
public static User getMockUser(String key) {
	if("test".equals(key))
	{
	User user = new User();
	user.setApiAccess(true);
	user.setUser("test");
	user.setPass("test");
	return user;
	}
	else
	{
		return new User();
	}
}

 
}
