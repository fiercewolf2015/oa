package com.xyj.oa.meeting.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_meeting_attachments")
public class MeetingAttachments extends IdEntity {

	private Meeting meeting;

	private String beforeMeetingAttachmentPath;// 会议前附件路径

	private String beforeMeetingAttachmentName;// 会议前附件名称

	private String afterMeetingAttachmentPath;// 会议后附件路径

	private String afterMeetingAttachmentName;// 会议后附件名称

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "m_id")
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public String getBeforeMeetingAttachmentPath() {
		return beforeMeetingAttachmentPath;
	}

	public void setBeforeMeetingAttachmentPath(String beforeMeetingAttachmentPath) {
		this.beforeMeetingAttachmentPath = beforeMeetingAttachmentPath;
	}

	public String getBeforeMeetingAttachmentName() {
		return beforeMeetingAttachmentName;
	}

	public void setBeforeMeetingAttachmentName(String beforeMeetingAttachmentName) {
		this.beforeMeetingAttachmentName = beforeMeetingAttachmentName;
	}

	public String getAfterMeetingAttachmentPath() {
		return afterMeetingAttachmentPath;
	}

	public void setAfterMeetingAttachmentPath(String afterMeetingAttachmentPath) {
		this.afterMeetingAttachmentPath = afterMeetingAttachmentPath;
	}

	public String getAfterMeetingAttachmentName() {
		return afterMeetingAttachmentName;
	}

	public void setAfterMeetingAttachmentName(String afterMeetingAttachmentName) {
		this.afterMeetingAttachmentName = afterMeetingAttachmentName;
	}

}
