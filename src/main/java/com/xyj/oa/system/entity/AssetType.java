package com.xyj.oa.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

/**
 * 资产业务类型
 */
@Entity
@Table(name = "t_assettypes")
public class AssetType extends IdEntity {

	private String name;

	private String remark;

	private String updateTime;

	private String createTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
