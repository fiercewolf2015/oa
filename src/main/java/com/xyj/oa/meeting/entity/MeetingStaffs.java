package com.xyj.oa.meeting.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

@Entity
@Table(name = "t_meeting_staffs")
public class MeetingStaffs extends IdEntity {

	private Meeting meeting;

	private Staff staff;

	private String meetingLoginTime;// 参会人签到时间

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "m_id")
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "s_id")
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getMeetingLoginTime() {
		return meetingLoginTime;
	}

	public void setMeetingLoginTime(String meetingLoginTime) {
		this.meetingLoginTime = meetingLoginTime;
	}

}
