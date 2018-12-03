package com.xyj.oa.api.attendance.exception;


@SuppressWarnings("serial")
public class WrongParamsException extends Exception {
	private APIErrorCode errorcode = APIErrorCode.WrongParams;

	public WrongParamsException() {
	}

	public WrongParamsException(String message) {
		super(message);
	}

	public WrongParamsException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongParamsException(Throwable cause) {
		super(cause);
	}

	public APIErrorCode getErrorcode() {
		return errorcode;
	}

}