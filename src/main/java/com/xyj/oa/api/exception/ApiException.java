package com.xyj.oa.api.exception;

import com.xyj.oa.api.vo.APIErrorCode;

public class ApiException extends Exception {
	private static final long serialVersionUID = -5690247865791816327L;
	private APIErrorCode error;

	public ApiException(APIErrorCode e) {
		error = e;
	}

	public APIErrorCode getError() {
		return error;
	}

	public void setError(APIErrorCode error) {
		this.error = error;
	}

}
