package com.xyj.oa.workflow.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_businessdatas")
public class BusinessData extends IdEntity {

	private String dataInfo;// 保存所有业务数据，json格式

	private Long workFlowInstanceId;

	private WorkFlowType workFlowType;

	private Timestamp createTime;

	public String getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	public WorkFlowType getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(WorkFlowType workFlowType) {
		this.workFlowType = workFlowType;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getWorkFlowInstanceId() {
		return workFlowInstanceId;
	}

	public void setWorkFlowInstanceId(Long workFlowInstanceId) {
		this.workFlowInstanceId = workFlowInstanceId;
	}

}
