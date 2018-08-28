package com.poc.event.exceptions;

public interface ExceptionCode {

	public static final String ERROR_CODE_01 ="E01";
	public static final String ERROR_CODE_02 ="E02";
	public static final String ERROR_CODE_03 ="E03";
	public static final String ERROR_CODE_04 ="E04";
	public static final String ERROR_CODE_05 ="E05";
	public static final String ERROR_CODE_06 ="E06";
	public static final String ERROR_CODE_07 ="E07";
	public static final String ERROR_DESC_01 ="Invalid raw data recieved to deserialize";
	public static final String ERROR_DESC_07 ="Invalid raw data recieved to deserialize Out of sequence";
	public static final String ERROR_DESC_02 ="Invalid raw data with mandatory check failed";
	public static final String ERROR_DESC_03 ="Internal System Error";
	public static final String ERROR_DESC_04 ="Exception persisting the event data";
	public static final String ERROR_DESC_05 ="Invalid format of raw data date  parameter :";
	public static final String ERROR_DESC_06 ="Invalid Orientation param:";
	
}
