package com.xyj.oa.api.attendance.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OvertimesEntity {

	@XStreamAlias("name")
	private String name;//员工姓名。必填项，为空则忽略此加班单

	@XStreamAlias("mobile")
	private String mobile;//员工手机号。必填项（也可选填工号），手机或工号不能同时为空，为空则忽略此加班单

	@XStreamAlias("jobNo")
	private String jobNo;//员工工号。必填项（也可选填手机），手机或工号不能同时为空，为空则忽略此加班单

	@XStreamAlias("type")
	private String type;//加班类别，必须为移动考勤平台中存在的加班类别，如：平时加班、节假日加班、休息日加班。必填项，为空则默认为平时加班

	@XStreamAlias("beginDate")
	private String beginDate;//加班开始日期，格式为yyyy-mm-dd，如2012-10-08。必填项，为空或格式不正确则忽略此加班单

	@XStreamAlias("endDate")
	private String endDate;//加班结束日期，格式为yyyy-mm-dd，如2012-01-03。必填项，为空或格式不正确则忽略此加班单

	@XStreamAlias("beginTime")
	private String beginTime;//加班开始时间，格式为hh:mm，如09:30。必填项，为空或格式不正确则忽略此加班单

	@XStreamAlias("endTime")
	private String endTime;//加班结束时间，格式为hh:mm，如18：00。必填项，为空或格式不正确则忽略此加班单。

	@XStreamAlias("duration")
	private String duration;//加班时长，单位为小时，取小数点1位。

	@XStreamAlias("durationByDay")
	private String durationByDay;//加班时长核算天数，单位为天，取小数点1位。

	@XStreamAlias("countDepositRest")
	private String countDepositRest;//此加班时长是否自动计入存休。1：自动计入存休；0：不计入存休。如果不填写则不计入存休

	@XStreamAlias("validDate")
	private String validDate;//有效期 1-12,24表示多少个月，-1：本年度；-99：永久有效

	@XStreamAlias("conflictHandle")
	private String conflictHandle;//加班单冲突处理方式。1：以此加班单覆盖冲突的加班单；2：忽略此加班单。如果不填写则默认覆盖

	@XStreamAlias("remark")
	private String remark;//备注。

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCountDepositRest() {
		return countDepositRest;
	}

	public void setCountDepositRest(String countDepositRest) {
		this.countDepositRest = countDepositRest;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getConflictHandle() {
		return conflictHandle;
	}

	public void setConflictHandle(String conflictHandle) {
		this.conflictHandle = conflictHandle;
	}
}
