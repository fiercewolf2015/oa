package com.xyj.oa.api.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StaffVoResult extends ApiResult {

	@JsonProperty("DATA")
	private ParentDeptVo[] parentDeptVos;

	public StaffVoResult() {

	}

	public StaffVoResult(List<ParentDeptVo> result, Integer flag) {
		this.result = flag;
		if (result == null || result.size() == 0)
			return;
		parentDeptVos = new ParentDeptVo[result.size()];
		for (int i = 0; i < result.size(); i++) {
			parentDeptVos[i] = result.get(i);
		}
	}

	public ParentDeptVo[] getParentDeptVos() {
		return parentDeptVos;
	}

	public void setParentDeptVos(ParentDeptVo[] parentDeptVos) {
		this.parentDeptVos = parentDeptVos;
	}

}
