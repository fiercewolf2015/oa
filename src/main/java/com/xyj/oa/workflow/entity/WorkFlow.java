package com.xyj.oa.workflow.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

@Entity
@Table(name = "t_workflows")
public class WorkFlow extends IdEntity {

	private String name;// 流程名称

	private int version;// 版本号

	private int point;// 节点个数

	private Set<Staff> staffs = new HashSet<>();// 流程对应的员工(包含员工，职务，岗位对应的所有员工)

	private String staffIds;// 员工id

	private String staffNames;

	private String occupationIds;// 职务id

	private String occupationNames;

	private String postIds;// 岗位id

	private String postNames;

	private WorkFlowType workFlowType;

	private String createTime;

	private String updateTime;

	private String workflowInfo = "";// 流程信息 不实例化到数据库，用于前台显示

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	public WorkFlowType getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(WorkFlowType workFlowType) {
		this.workFlowType = workFlowType;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Transient
	public String getWorkflowInfo() {
		return workflowInfo;
	}

	public void setWorkflowInfo(String workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_workflow_staffs", joinColumns = { @JoinColumn(name = "wf_id") }, inverseJoinColumns = {
			@JoinColumn(name = "s_id") })
	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	public String getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}

	public String getStaffNames() {
		return staffNames;
	}

	public void setStaffNames(String staffNames) {
		this.staffNames = staffNames;
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

	public String getPostIds() {
		return postIds;
	}

	public void setPostIds(String postIds) {
		this.postIds = postIds;
	}

	public String getPostNames() {
		return postNames;
	}

	public void setPostNames(String postNames) {
		this.postNames = postNames;
	}
}
