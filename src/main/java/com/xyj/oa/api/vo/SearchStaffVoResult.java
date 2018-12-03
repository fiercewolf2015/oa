package com.xyj.oa.api.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchStaffVoResult extends ApiResult {

	@JsonProperty("DATA")
	private SearchStaffVo[] searchStaffVo;

	public SearchStaffVoResult() {

	}

	public SearchStaffVoResult(List<SearchStaffVo> result, Integer flag) {
		this.result = flag;
		if (result == null || result.size() == 0)
			return;
		searchStaffVo = new SearchStaffVo[result.size()];
		for (int i = 0; i < result.size(); i++) {
			searchStaffVo[i] = result.get(i);
		}
	}

	public SearchStaffVo[] getSearchStaffVo() {
		return searchStaffVo;
	}

	public void setSearchStaffVo(SearchStaffVo[] searchStaffVo) {
		this.searchStaffVo = searchStaffVo;
	}

}
