package com.xyj.oa.api.attendance.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OvertimesResp {

	public static final String OvertimesRes_Root = "OvertimesResp";

	@XStreamAlias("result")
	private int result = 0; //返回结果，0表示成功。
	@XStreamAlias("error")
	private String error; //失败原因
	@XStreamAlias("add")
	private int add; //新增员工数
	@XStreamAlias("skip")
	private String skip; //忽略的部门序号，英文逗号分隔。
	@XStreamAlias("timestamp")
	private String timestamp;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}

	public String getSkip() {
		return skip;
	}

	public void setSkip(String skip) {
		this.skip = skip;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void increaseAdd() {
		add++;
	}
}
