package com.xyj.oa.api.attendance.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class LeaveBillReq {
	public static final String LeaveBillReq_Root = "Leaves";

	@XStreamImplicit(itemFieldName = "leave")
	private List<LeaveBillEntity> leaves;

	public List<LeaveBillEntity> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<LeaveBillEntity> leaves) {
		this.leaves = leaves;
	}

}
