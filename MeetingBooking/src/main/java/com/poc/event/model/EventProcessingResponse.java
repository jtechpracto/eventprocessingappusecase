package com.poc.event.model;

 
public class EventProcessingResponse {
	@Override
	public String toString() {
		return "EventProcessingResponse [responseCode=" + responseCode
				+ ", responseDesc=" + responseDesc + "]";
	}

	private String responseCode;
	private String responseDesc;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseDesc() {
		return responseDesc;
	}
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	
	
	
	
	public static EventProcessingResponse buildErrorResponse(String errorCode,String errorDesc)
	{
		EventProcessingResponse resp = new EventProcessingResponse();
		resp.setResponseCode(errorCode);
		resp.setResponseDesc(errorDesc);
		return resp;
	}
	
	public static EventProcessingResponse buildSuccessResponse()
	{
		EventProcessingResponse resp = new EventProcessingResponse();
		resp.setResponseCode("200");
		resp.setResponseDesc("Success");
		return resp;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		EventProcessingResponse resp = (EventProcessingResponse)obj;
		return (resp.getResponseCode().equals(responseCode) && resp.getResponseDesc().equals(responseDesc));
		}
}
