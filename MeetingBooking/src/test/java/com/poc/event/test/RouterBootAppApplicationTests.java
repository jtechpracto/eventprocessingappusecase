package com.poc.event.test;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.model.EventProcessingResponse;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouterBootAppApplicationTests extends AbstractTester{


	@Override
	public void testBServiceGetEvent() {
		// TODO Auto-generated method stub
		Response resp = testService.getEvent(testEventId);
		Assert.assertEquals( resp.getStatus(),200);
		Assert.assertEquals( getEventId(resp),testEventId);
		_log.info("Case Tested PASS: Get Event");
	}

	@Override
	public void testCServiceDeleteEvent() {
		// TODO Auto-generated method stub
		Response resp = testService.deleteEvent(testEventId);
		Assert.assertEquals( resp.getStatus(),200);
		Assert.assertEquals("1",resp.getEntity());
		_log.info("Case Tested PASS: DeleteEvent");
	}

	
 
	@Override
	public void testAServiceSendEvent() {
		// TODO Auto-generated method stub
		 
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getParameter("input_payload")).thenReturn(testEventId+getValidPayloadPart());
		Response resp = testService.sendEvent(mockRequest);
		
		EventProcessingResponse expectedResponse = EventProcessingResponse.buildSuccessResponse();
		
		Assert.assertEquals( resp.getStatus(),200);
		
		Assert.assertEquals( expectedResponse, resp.getEntity());
		_log.info("Case Tested PASS: SendEvent");
	}
	
	@Override
	public void testValidationOfBlankPayload() {
		// TODO Auto-generated method stub
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getParameter("input_payload")).thenReturn(getBlankPayloadPart());
		Response resp = testService.sendEvent(mockRequest);
		//ERROR_CODE_02
		EventProcessingResponse expectedResponse = EventProcessingResponse.buildErrorResponse(ExceptionCode.ERROR_CODE_01,ExceptionCode.ERROR_DESC_01);
		
		Assert.assertEquals( resp.getStatus(),200);
		
		Assert.assertEquals( expectedResponse.getResponseCode(), ((EventProcessingResponse)resp.getEntity()).getResponseCode());
		_log.info("Case Tested PASS:"+expectedResponse);
	}
	
	@Override
	public void testMandatoryParameterCheckPart() {
		// TODO Auto-generated method stub
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getParameter("input_payload")).thenReturn(testEventId+getMandatoryParameterCheckPart());
		Response resp = testService.sendEvent(mockRequest);
		//ERROR_CODE_02
		EventProcessingResponse expectedResponse = EventProcessingResponse.buildErrorResponse(ExceptionCode.ERROR_CODE_02,ExceptionCode.ERROR_DESC_02);
		Assert.assertEquals( resp.getStatus(),200);
		Assert.assertEquals( expectedResponse.getResponseCode(), ((EventProcessingResponse)resp.getEntity()).getResponseCode());
		
		_log.info("Case Tested PASS:"+expectedResponse);
	}
	
	@Override
	public void testValidationInValidDateParameterPart() {
		// TODO Auto-generated method stub
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getParameter("input_payload")).thenReturn(testEventId+getInValidDatePayloadPart());
		Response resp = testService.sendEvent(mockRequest);
		//ERROR_CODE_02
		EventProcessingResponse expectedResponse = EventProcessingResponse.buildErrorResponse(ExceptionCode.ERROR_CODE_05,ExceptionCode.ERROR_DESC_05);
		
		Assert.assertEquals( resp.getStatus(),200);
		
		Assert.assertEquals( expectedResponse.getResponseCode(), ((EventProcessingResponse)resp.getEntity()).getResponseCode());
		_log.info("Case Tested PASS:"+expectedResponse);
	}
	 
	@Override
	public void testValidationOfInvalidEventOrientationData() {
		// TODO Auto-generated method stub
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getParameter("input_payload")).thenReturn(testEventId+geInvalidEventOrientationPart());
		Response resp = testService.sendEvent(mockRequest);
		//ERROR_CODE_02
		EventProcessingResponse expectedResponse = EventProcessingResponse.buildErrorResponse(ExceptionCode.ERROR_CODE_06,"");
		
		Assert.assertEquals( resp.getStatus(),200);
	 	Assert.assertEquals( expectedResponse.getResponseCode(), ((EventProcessingResponse)resp.getEntity()).getResponseCode());
	 	_log.info("Case Tested PASS:"+expectedResponse);
	 }
	
 
	@Override
	public void testValidationOfInvalidEventOutOfSequenceParameter() {
		// TODO Auto-generated method stub
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getParameter("input_payload")).thenReturn(testEventId+geInvalidEventOrderPart());
		Response resp = testService.sendEvent(mockRequest);
		//ERROR_CODE_02
		EventProcessingResponse expectedResponse = EventProcessingResponse.buildErrorResponse(ExceptionCode.ERROR_CODE_07,ExceptionCode.ERROR_DESC_07);
		
		Assert.assertEquals( resp.getStatus(),200);
	 	Assert.assertEquals( expectedResponse.getResponseCode(), ((EventProcessingResponse)resp.getEntity()).getResponseCode());
		_log.info("Case Tested PASS:"+expectedResponse);
	}

	
	@Test
	public void testRestAuthValidation() {
		ContainerRequestContext mockContext = mock(ContainerRequestContext.class);
		when(mockContext.getHeaderString("Authorization")).thenReturn(
				"Basic dGVzdDp0ZXN0");
		assertSame(true, testAuth(mockContext));
		_log.info("Case Tested PASS: VALID AUTH");
	}

	@Test
	public void testRestAuthValidationFailure() {
		ContainerRequestContext mockContext = mock(ContainerRequestContext.class);
		when(mockContext.getHeaderString("Authorization")).thenReturn("fffff");
		assertSame(false, testAuth(mockContext));
		_log.info("Case Tested PASS:INVALID USER ID / AUTH" );
	}
	


}