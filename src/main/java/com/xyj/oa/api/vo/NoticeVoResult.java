package com.xyj.oa.api.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoticeVoResult extends ApiResult {

	@JsonProperty("DATA")
	private NoticeVo[] noticeVos;

	public NoticeVoResult() {

	}

	public NoticeVoResult(List<NoticeVo> result) {
		if (result == null || result.size() == 0)
			return;
		this.result = result.size();
		noticeVos = new NoticeVo[result.size()];
		for (int i = 0; i < result.size(); i++) {
			noticeVos[i] = result.get(i);
		}
	}
}
