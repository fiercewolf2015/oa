package com.xyj.oa.api.attendance.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class LeaveBillEntity {

	@XStreamAlias("name")
	private String name;

	@XStreamAlias("mobile")
	private String mobile;

	@XStreamAlias("jobNo")
	private String jobNo;

	@XStreamAlias("type")
	private String leaveType;

	@XStreamAlias("beginDate")
	private String beginDate;

	@XStreamAlias("endDate")
	private String endDate;

	@XStreamAlias("beginTime")
	private String beginTime;

	@XStreamAlias("endTime")
	private String endTime;

	@XStreamAlias("duration")
	private String duration;

	@XStreamAlias("durationByDay")
	private String durationByDay;

	@XStreamAlias("genre")
	private String genre;

	@XStreamAlias("dayOfTime")
	private String dayOfTime;

	@XStreamAlias("firstDayOfTime")
	private String firstDayOfTime;

	@XStreamAlias("lastDayOfTime")
	private String lastDayOfTime;

	@XStreamAlias("counteractDepositRest")
	private String counteractDepositRest;

	@XStreamAlias("conflictHandle")
	private String conflictHandle;

	@XStreamAlias("remark")
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDurationByDay() {
		return durationByDay;
	}

	public void setDurationByDay(String durationByDay) {
		this.durationByDay = durationByDay;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDayOfTime() {
		return dayOfTime;
	}

	public void setDayOfTime(String dayOfTime) {
		this.dayOfTime = dayOfTime;
	}

	public String getFirstDayOfTime() {
		return firstDayOfTime;
	}

	public void setFirstDayOfTime(String firstDayOfTime) {
		this.firstDayOfTime = firstDayOfTime;
	}

	public String getLastDayOfTime() {
		return lastDayOfTime;
	}

	public void setLastDayOfTime(String lastDayOfTime) {
		this.lastDayOfTime = lastDayOfTime;
	}

	public String getCounteractDepositRest() {
		return counteractDepositRest;
	}

	public void setCounteractDepositRest(String counteractDepositRest) {
		this.counteractDepositRest = counteractDepositRest;
	}

	public String getConflictHandle() {
		return conflictHandle;
	}

	public void setConflictHandle(String conflictHandle) {
		this.conflictHandle = conflictHandle;
	}

}
