package com.xyj.oa.api.attendance.exception;


public class WrongXMLFormatException extends Exception {

	private static final long serialVersionUID = 352728479325144216L;
	private APIErrorCode errorcode = APIErrorCode.WrongXMLFormat;

	public WrongXMLFormatException() {
	}

	public WrongXMLFormatException(String message) {
		super(message);
	}

	public WrongXMLFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongXMLFormatException(Throwable cause) {
		super(cause);
	}

	public APIErrorCode getErrorcode() {
		return errorcode;
	}

}