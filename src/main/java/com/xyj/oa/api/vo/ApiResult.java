package com.xyj.oa.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ApiResult {
	
	@JsonProperty("RESULT")
	int result = 0;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
