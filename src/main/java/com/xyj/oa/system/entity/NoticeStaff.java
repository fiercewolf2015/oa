package com.xyj.oa.system.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

@Entity
@Table(name = "t_notice_staffs")
public class NoticeStaff extends IdEntity {

	private Notice notice;

	private Staff staff;

	private int unread = 0;// 0为未读 1未已读

	public int getUnread() {
		return unread;
	}

	public void setUnread(int unread) {
		this.unread = unread;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "notice_id")
	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "staff_id")
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

}
