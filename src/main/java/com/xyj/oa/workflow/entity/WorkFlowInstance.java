package com.xyj.oa.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_workflowinstances")
public class WorkFlowInstance extends IdEntity {

	public static final int POINTSTATE_PROCESSING = 0;

	public static final int POINTSTATE_COMPLETE = 1;

	public static final int POINTSTATE_DELETE = 2;

	private WorkFlow workFlow;

	private String instanceNum;

	private String pointAssignee;// 流程经办人

	private String pointApproveTime;// 流程审批完成时间

	private String pointReceiveTime;// 流程接收时间

	private String pointCreateTime;// 流程创建时间

	private String applyUser;// 流程申请人

	private String applyUserNo;// 申请人编号

	private String pointReason;// 当前节点意见

	private String pointName;// 当前节点

	private int pointNum;// 当前节点序号

	private Long staffId;// 申请人staffId

	private Long subStaffId;// 代请人 staffId

	private Long managerStaffId;// 经办人staffId

	private String applyUserDeptName;// 申请人部门名称

	private BusinessData businessData;

	private int ifreject = 0;// 是否驳回 1为驳回

	private int isComplete = POINTSTATE_PROCESSING;// 流程是否已结束 0为未结束，1为已结束

	private String attachmentPath;// 附件路径

	private String attachmentName;// 附件名称

	private float reserve1 = 0;

	private String reserve2;

	private String reserve3;

	private String reserve4;

	private String reserve5;

	private String reserve6;

	private String reserve7;

	private String reserve8;// shrformid

	private String reserve9;

	private String reserve10;// 暂时保存财务票据流程中间随机审批人id

	private String reserve11;

	private String reserve12;

	private String reserve13;// 分公司名称

	private String reserve14;// 项目名称

	private String reserve15;

	private String reserve16;

	private String reserve17;

	private String reserve18;

	private String reserve19;

	private String reserve20;

	public String getPointAssignee() {
		return pointAssignee;
	}

	public void setPointAssignee(String pointAssignee) {
		this.pointAssignee = pointAssignee;
	}

	public String getPointApproveTime() {
		return pointApproveTime;
	}

	public void setPointApproveTime(String pointApproveTime) {
		this.pointApproveTime = pointApproveTime;
	}

	public String getPointReceiveTime() {
		return pointReceiveTime;
	}

	public void setPointReceiveTime(String pointReceiveTime) {
		this.pointReceiveTime = pointReceiveTime;
	}

	public String getPointCreateTime() {
		return pointCreateTime;
	}

	public void setPointCreateTime(String pointCreateTime) {
		this.pointCreateTime = pointCreateTime;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUserNo() {
		return applyUserNo;
	}

	public void setApplyUserNo(String applyUserNo) {
		this.applyUserNo = applyUserNo;
	}

	public String getPointReason() {
		return pointReason;
	}

	public void setPointReason(String pointReason) {
		this.pointReason = pointReason;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Long getSubStaffId() {
		return subStaffId;
	}

	public void setSubStaffId(Long subStaffId) {
		this.subStaffId = subStaffId;
	}

	public Long getManagerStaffId() {
		return managerStaffId;
	}

	public void setManagerStaffId(Long managerStaffId) {
		this.managerStaffId = managerStaffId;
	}

	public String getApplyUserDeptName() {
		return applyUserDeptName;
	}

	public void setApplyUserDeptName(String applyUserDeptName) {
		this.applyUserDeptName = applyUserDeptName;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buss_id")
	public BusinessData getBusinessData() {
		return businessData;
	}

	public void setBusinessData(BusinessData businessData) {
		this.businessData = businessData;
	}

	public int getIfreject() {
		return ifreject;
	}

	public void setIfreject(int ifreject) {
		this.ifreject = ifreject;
	}

	public int getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(int isComplete) {
		this.isComplete = isComplete;
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

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	public String getReserve5() {
		return reserve5;
	}

	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}

	public String getReserve6() {
		return reserve6;
	}

	public void setReserve6(String reserve6) {
		this.reserve6 = reserve6;
	}

	public String getReserve7() {
		return reserve7;
	}

	public void setReserve7(String reserve7) {
		this.reserve7 = reserve7;
	}

	public String getReserve8() {
		return reserve8;
	}

	public void setReserve8(String reserve8) {
		this.reserve8 = reserve8;
	}

	public String getReserve9() {
		return reserve9;
	}

	public void setReserve9(String reserve9) {
		this.reserve9 = reserve9;
	}

	public String getReserve10() {
		return reserve10;
	}

	public void setReserve10(String reserve10) {
		this.reserve10 = reserve10;
	}

	public String getReserve11() {
		return reserve11;
	}

	public void setReserve11(String reserve11) {
		this.reserve11 = reserve11;
	}

	public String getReserve12() {
		return reserve12;
	}

	public void setReserve12(String reserve12) {
		this.reserve12 = reserve12;
	}

	public String getReserve13() {
		return reserve13;
	}

	public void setReserve13(String reserve13) {
		this.reserve13 = reserve13;
	}

	public String getReserve14() {
		return reserve14;
	}

	public void setReserve14(String reserve14) {
		this.reserve14 = reserve14;
	}

	public String getReserve15() {
		return reserve15;
	}

	public void setReserve15(String reserve15) {
		this.reserve15 = reserve15;
	}

	public String getReserve16() {
		return reserve16;
	}

	public void setReserve16(String reserve16) {
		this.reserve16 = reserve16;
	}

	public String getReserve17() {
		return reserve17;
	}

	public void setReserve17(String reserve17) {
		this.reserve17 = reserve17;
	}

	public String getReserve18() {
		return reserve18;
	}

	public void setReserve18(String reserve18) {
		this.reserve18 = reserve18;
	}

	public String getReserve19() {
		return reserve19;
	}

	public void setReserve19(String reserve19) {
		this.reserve19 = reserve19;
	}

	public String getReserve20() {
		return reserve20;
	}

	public void setReserve20(String reserve20) {
		this.reserve20 = reserve20;
	}

	public int getPointNum() {
		return pointNum;
	}

	public void setPointNum(int pointNum) {
		this.pointNum = pointNum;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "wf_id")
	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public float getReserve1() {
		return reserve1;
	}

	public void setReserve1(Float reserve1) {
		this.reserve1 = reserve1;
	}

	public String getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(String instanceNum) {
		this.instanceNum = instanceNum;
	}

}
