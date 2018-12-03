package com.xyj.oa.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_workflowpointinfos")
public class WorkFlowPointInfo extends IdEntity {

	private WorkFlowInstance workFlowInstance;

	private int pointNum;

	private String pointName;

	private Long approvalStaffId;

	private Integer approvalState;// 审批状态

	private String reason;// 审批意见

	private String taskReceiveTime;// 流程接收时间

	private String taskCrossTime;// 流程通过时间

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getTaskReceiveTime() {
		return taskReceiveTime;
	}

	public void setTaskReceiveTime(String taskReceiveTime) {
		this.taskReceiveTime = taskReceiveTime;
	}

	public String getTaskCrossTime() {
		return taskCrossTime;
	}

	public void setTaskCrossTime(String taskCrossTime) {
		this.taskCrossTime = taskCrossTime;
	}

	public int getPointNum() {
		return pointNum;
	}

	public void setPointNum(int pointNum) {
		this.pointNum = pointNum;
	}

	public Integer getApprovalState() {
		return approvalState;
	}

	public void setApprovalState(Integer approvalState) {
		this.approvalState = approvalState;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "wfi_id")
	public WorkFlowInstance getWorkFlowInstance() {
		return workFlowInstance;
	}

	public void setWorkFlowInstance(WorkFlowInstance workFlowInstance) {
		this.workFlowInstance = workFlowInstance;
	}

	public Long getApprovalStaffId() {
		return approvalStaffId;
	}

	public void setApprovalStaffId(Long approvalStaffId) {
		this.approvalStaffId = approvalStaffId;
	}

}
