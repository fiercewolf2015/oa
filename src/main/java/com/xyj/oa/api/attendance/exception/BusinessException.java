package com.xyj.oa.api.attendance.exception;

/**
 * 业务异常基类.
 *
 * @author calvin
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	private Integer errorCode;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
