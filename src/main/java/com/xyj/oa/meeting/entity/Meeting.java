package com.xyj.oa.meeting.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

@Entity
@Table(name = "t_meetings")
public class Meeting extends IdEntity {

	private String meetingTitle;// 会议简介

	private String meetingRemark;// 会议内容

	private String meetingLocation;// 会议地点

	private int meetingType;// 会议类别

	private String meetingBeginTime;// 会议开始时间

	private String meetingEndTime;// 会议结束时间

	private Staff applyStaff;// 发起人

	private Department applyDepartment;// 发起人部门

	private int meetingFlag = 0;// 0为未结束会议，1为已结束会议

	private Set<Staff> staffs = new HashSet<>();// 计划参会人

	private Set<MeetingStaffs> meetingStaffs;// 实际参会人数

	private Set<MeetingAttachments> meetingAttachments;// 会议对应文件

	private Timestamp createTime;

	private String applyStaffName;

	private String applyDeptName;

	private String meetingTypeStr;

	private String meetingBeforeAttachmentsPath;

	private String meetingAfterAttachmentsPath;

	private String meetingStaffNames;

	public String getMeetingTitle() {
		return meetingTitle;
	}

	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}

	public String getMeetingRemark() {
		return meetingRemark;
	}

	public void setMeetingRemark(String meetingRemark) {
		this.meetingRemark = meetingRemark;
	}

	public String getMeetingLocation() {
		return meetingLocation;
	}

	public void setMeetingLocation(String meetingLocation) {
		this.meetingLocation = meetingLocation;
	}

	public int getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(int meetingType) {
		this.meetingType = meetingType;
	}

	public String getMeetingBeginTime() {
		return meetingBeginTime;
	}

	public void setMeetingBeginTime(String meetingBeginTime) {
		this.meetingBeginTime = meetingBeginTime;
	}

	public String getMeetingEndTime() {
		return meetingEndTime;
	}

	public void setMeetingEndTime(String meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}

	@ManyToOne
	@JoinColumn(name = "s_id")
	public Staff getApplyStaff() {
		return applyStaff;
	}

	public void setApplyStaff(Staff applyStaff) {
		this.applyStaff = applyStaff;
	}

	@ManyToOne
	@JoinColumn(name = "d_id")
	public Department getApplyDepartment() {
		return applyDepartment;
	}

	public void setApplyDepartment(Department applyDepartment) {
		this.applyDepartment = applyDepartment;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_meeting_plan_staffs", joinColumns = { @JoinColumn(name = "m_id") }, inverseJoinColumns = {
			@JoinColumn(name = "s_id") })
	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "meeting")
	public Set<MeetingStaffs> getMeetingStaffs() {
		return meetingStaffs;
	}

	public void setMeetingStaffs(Set<MeetingStaffs> meetingStaffs) {
		this.meetingStaffs = meetingStaffs;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "meeting")
	public Set<MeetingAttachments> getMeetingAttachments() {
		return meetingAttachments;
	}

	public void setMeetingAttachments(Set<MeetingAttachments> meetingAttachments) {
		this.meetingAttachments = meetingAttachments;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Transient
	public String getApplyStaffName() {
		return applyStaffName;
	}

	public void setApplyStaffName(String applyStaffName) {
		this.applyStaffName = applyStaffName;
	}

	@Transient
	public String getApplyDeptName() {
		return applyDeptName;
	}

	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}

	@Transient
	public String getMeetingTypeStr() {
		return meetingTypeStr;
	}

	public void setMeetingTypeStr(String meetingTypeStr) {
		this.meetingTypeStr = meetingTypeStr;
	}

	@Transient
	public String getMeetingBeforeAttachmentsPath() {
		return meetingBeforeAttachmentsPath;
	}

	public void setMeetingBeforeAttachmentsPath(String meetingBeforeAttachmentsPath) {
		this.meetingBeforeAttachmentsPath = meetingBeforeAttachmentsPath;
	}

	@Transient
	public String getMeetingAfterAttachmentsPath() {
		return meetingAfterAttachmentsPath;
	}

	public void setMeetingAfterAttachmentsPath(String meetingAfterAttachmentsPath) {
		this.meetingAfterAttachmentsPath = meetingAfterAttachmentsPath;
	}

	public int getMeetingFlag() {
		return meetingFlag;
	}

	public void setMeetingFlag(int meetingFlag) {
		this.meetingFlag = meetingFlag;
	}

	@Transient
	public String getMeetingStaffNames() {
		return meetingStaffNames;
	}

	public void setMeetingStaffNames(String meetingStaffNames) {
		this.meetingStaffNames = meetingStaffNames;
	}

}
