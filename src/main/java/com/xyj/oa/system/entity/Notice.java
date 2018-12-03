package com.xyj.oa.system.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

@Entity
@Table(name = "t_notices")
public class Notice extends IdEntity {

	private Staff promotStaff;// 发起人

	private String title;// 消息标题

	private String message;// 消息内容

	private String attachmentPath;// 附件路径

	private String attachmentName;// 附件名称

	private Set<NoticeStaff> noticeStaffs;

	private Timestamp createTime;

	private String createTimeStr;

	@ManyToOne
	@JoinColumn(name = "s_id")
	public Staff getPromotStaff() {
		return promotStaff;
	}

	public void setPromotStaff(Staff promotStaff) {
		this.promotStaff = promotStaff;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "notice")
	public Set<NoticeStaff> getNoticeStaffs() {
		return noticeStaffs;
	}

	public void setNoticeStaffs(Set<NoticeStaff> noticeStaffs) {
		this.noticeStaffs = noticeStaffs;
	}

	@Transient
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

}
