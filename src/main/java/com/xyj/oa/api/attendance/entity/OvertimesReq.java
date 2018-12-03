package com.xyj.oa.api.attendance.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class OvertimesReq {

	public static final String OvertimesReq_Root = "Overtimes";

	@XStreamImplicit(itemFieldName = "Overtime")
	private List<OvertimesEntity> overtimes;

	public List<OvertimesEntity> getOvertimes() {
		return overtimes;
	}

	public void setOvertimes(List<OvertimesEntity> overtimes) {
		this.overtimes = overtimes;
	}

}
