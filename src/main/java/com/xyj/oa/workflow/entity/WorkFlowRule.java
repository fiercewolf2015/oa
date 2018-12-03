package com.xyj.oa.workflow.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_workflowrules")
public class WorkFlowRule extends IdEntity {

	private WorkFlow workFlow;

	private int orderNumber;

	private String pointName;

	private String occupationIds;

	private String occupationNames;

	private String postIds;

	private String postNames;

	private String staffIds;

	private String staffNames;

	private Timestamp createTime;

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "workFlow_id")
	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPostIds() {
		return postIds;
	}

	public void setPostIds(String postIds) {
		this.postIds = postIds;
	}

	public String getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}

	public String getPostNames() {
		return postNames;
	}

	public void setPostNames(String postNames) {
		this.postNames = postNames;
	}

	public String getStaffNames() {
		return staffNames;
	}

	public void setStaffNames(String staffNames) {
		this.staffNames = staffNames;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getOccupationIds() {
		return occupationIds;
	}

	public void setOccupationIds(String occupationIds) {
		this.occupationIds = occupationIds;
	}

	public String getOccupationNames() {
		return occupationNames;
	}

	public void setOccupationNames(String occupationNames) {
		this.occupationNames = occupationNames;
	}

}
