package com.xyj.oa.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyj.oa.system.entity.Notice;

public class NoticeVo {

	@JsonProperty("name")
	private String name;

	@JsonProperty("message")
	private String message;

	@JsonProperty("a_name")
	private String attachmentName;

	@JsonProperty("n_id")
	private Long id;

	@JsonProperty("ct_time")
	private String createTimeStr;

	public NoticeVo() {

	}

	public NoticeVo(Notice n) {
		this.name = n.getTitle();
		this.message = n.getMessage();
		this.attachmentName = n.getAttachmentName();
		this.id = n.getId();
		this.createTimeStr = n.getCreateTimeStr();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

}
