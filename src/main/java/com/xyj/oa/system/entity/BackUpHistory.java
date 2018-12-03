package com.xyj.oa.system.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_backuphistorys")
public class BackUpHistory extends IdEntity {

	private Long userId;

	private Timestamp createTime;

	private int backupFlag = 0;// 0为正在备份，1为备份完成

	private String downloadPath;// 存储路径

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getBackupFlag() {
		return backupFlag;
	}

	public void setBackupFlag(int backupFlag) {
		this.backupFlag = backupFlag;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

}
