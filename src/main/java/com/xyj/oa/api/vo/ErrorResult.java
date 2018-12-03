package com.xyj.oa.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResult extends ApiResult {

	@JsonProperty("ERROR")
	private String error;

	public ErrorResult(APIErrorCode e) {
		if (e != null)
			error = e.getErrorCode();
		result = 0;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
