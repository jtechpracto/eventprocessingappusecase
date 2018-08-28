package com.poc.event.model;

import com.poc.event.exceptions.ExceptionCode;
import com.poc.event.exceptions.EventDataValidationException;

public class SensorOrientation {

	/*
	 * Attributes representing Device Position when sensor sensed the device.
	 */

	int xAxis;
	int yAxis;
	int zAxis;
	
	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}

	public void setzAxis(int zAxis) {
		this.zAxis = zAxis;
	}



	public SensorOrientation() {

	}

	public SensorOrientation(int xAxis, int yAxis, int zAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.zAxis = zAxis;
	}

	public int getyAxis() {
		return yAxis;
	}

	public int getzAxis() {
		return zAxis;
	}

	public int getxAxis() {
		return xAxis;
	}

	public void setxAxis(int xAxis) {
		this.xAxis = xAxis;
	}

	public static SensorOrientation build(String dimensions) throws EventDataValidationException {
		// TODO Auto-generated method stub
		SensorOrientation so = new SensorOrientation();
		try {
			
			String []arry = dimensions.split(",");
			so.setxAxis(Integer.parseInt(arry[0]));
			so.setyAxis(Integer.parseInt(arry[1]));
			so.setzAxis(Integer.parseInt(arry[2]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// pass Code and Detail 
			throw new EventDataValidationException(ExceptionCode.ERROR_CODE_06,ExceptionCode.ERROR_DESC_05+" Parameters:"+dimensions);
		}
		return so;
		}

}
