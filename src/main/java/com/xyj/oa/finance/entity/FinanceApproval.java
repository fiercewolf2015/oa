package com.xyj.oa.finance.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

/**
 * 预算审批实体
 */
@Entity
@Table(name = "t_financeapprovals")
public class FinanceApproval extends IdEntity {

	private String financeApprovalName;// 预算编制名称

	private Set<FinanceOrganization> financeOrganizations = new HashSet<FinanceOrganization>();// 对应审批预算编制单

	private String financeYear;

	private Staff financeApprovalStaff;// 审批人

	private String financeApprovalStaffIds;// 第二节点多人审批点

	private Set<Staff> staffs;// 第二节点多人审批点对应关系

	private Department financeApprovalDepartment;// 预算编制人员所属部门

	private Staff financeApplyStaff;// 预算申请人

	private Department financeApplyDepartment;// 预算申请人部门

	private Integer allJanuary;// 一月所有科目预算总和

	private Integer allFebruary;// 二月所有科目预算总和

	private Integer allMarch;// 三月所有科目预算总和

	private Integer allApril;// 四月所有科目预算总和

	private Integer allMay;// 五月所有科目预算总和

	private Integer allJune;// 六月所有科目预算总和

	private Integer allJuly;// 七月所有科目预算总和

	private Integer allAugust;// 八月所有科目预算总和

	private Integer allSeptember;// 九月所有科目预算总和

	private Integer allOctober;// 十月所有科目预算总和

	private Integer allNovember;// 十一月所有科目预算总和

	private Integer allDecember;// 十二月所有科目预算总和

	private Integer approvalFlag;// 审批状态0为未申请 1为第一阶段审批 2为第二阶段审批以此类推，100为完成审批终板

	private String rejectReason;// 驳回意见

	private Integer flag = 0;// 0为原版 历史版本flag++

	private Long historyFinanceId;// 原版编制id

	private Timestamp createTime;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_finance_organization_approval", joinColumns = {
			@JoinColumn(name = "fapp_id") }, inverseJoinColumns = { @JoinColumn(name = "for_id") })
	public Set<FinanceOrganization> getFinanceOrganizations() {
		return financeOrganizations;
	}

	public void setFinanceOrganizations(Set<FinanceOrganization> financeOrganizations) {
		this.financeOrganizations = financeOrganizations;
	}

	@ManyToOne
	@JoinColumn(name = "approvalstaff_id")
	public Staff getFinanceApprovalStaff() {
		return financeApprovalStaff;
	}

	public void setFinanceApprovalStaff(Staff financeApprovalStaff) {
		this.financeApprovalStaff = financeApprovalStaff;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getFinanceApprovalName() {
		return financeApprovalName;
	}

	public void setFinanceApprovalName(String financeApprovalName) {
		this.financeApprovalName = financeApprovalName;
	}

	@ManyToOne
	@JoinColumn(name = "approvaldept_id")
	public Department getFinanceApprovalDepartment() {
		return financeApprovalDepartment;
	}

	public void setFinanceApprovalDepartment(Department financeApprovalDepartment) {
		this.financeApprovalDepartment = financeApprovalDepartment;
	}

	@ManyToOne
	@JoinColumn(name = "applystaff_id")
	public Staff getFinanceApplyStaff() {
		return financeApplyStaff;
	}

	public void setFinanceApplyStaff(Staff financeApplyStaff) {
		this.financeApplyStaff = financeApplyStaff;
	}

	@ManyToOne
	@JoinColumn(name = "applydept_id")
	public Department getFinanceApplyDepartment() {
		return financeApplyDepartment;
	}

	public void setFinanceApplyDepartment(Department financeApplyDepartment) {
		this.financeApplyDepartment = financeApplyDepartment;
	}

	public Integer getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(Integer approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public String getFinanceYear() {
		return financeYear;
	}

	public void setFinanceYear(String financeYear) {
		this.financeYear = financeYear;
	}

	public Integer getAllJanuary() {
		return allJanuary;
	}

	public void setAllJanuary(Integer allJanuary) {
		this.allJanuary = allJanuary;
	}

	public Integer getAllFebruary() {
		return allFebruary;
	}

	public void setAllFebruary(Integer allFebruary) {
		this.allFebruary = allFebruary;
	}

	public Integer getAllMarch() {
		return allMarch;
	}

	public void setAllMarch(Integer allMarch) {
		this.allMarch = allMarch;
	}

	public Integer getAllApril() {
		return allApril;
	}

	public void setAllApril(Integer allApril) {
		this.allApril = allApril;
	}

	public Integer getAllMay() {
		return allMay;
	}

	public void setAllMay(Integer allMay) {
		this.allMay = allMay;
	}

	public Integer getAllJune() {
		return allJune;
	}

	public void setAllJune(Integer allJune) {
		this.allJune = allJune;
	}

	public Integer getAllJuly() {
		return allJuly;
	}

	public void setAllJuly(Integer allJuly) {
		this.allJuly = allJuly;
	}

	public Integer getAllAugust() {
		return allAugust;
	}

	public void setAllAugust(Integer allAugust) {
		this.allAugust = allAugust;
	}

	public Integer getAllSeptember() {
		return allSeptember;
	}

	public void setAllSeptember(Integer allSeptember) {
		this.allSeptember = allSeptember;
	}

	public Integer getAllOctober() {
		return allOctober;
	}

	public void setAllOctober(Integer allOctober) {
		this.allOctober = allOctober;
	}

	public Integer getAllNovember() {
		return allNovember;
	}

	public void setAllNovember(Integer allNovember) {
		this.allNovember = allNovember;
	}

	public Integer getAllDecember() {
		return allDecember;
	}

	public void setAllDecember(Integer allDecember) {
		this.allDecember = allDecember;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getFinanceApprovalStaffIds() {
		return financeApprovalStaffIds;
	}

	public void setFinanceApprovalStaffIds(String financeApprovalStaffIds) {
		this.financeApprovalStaffIds = financeApprovalStaffIds;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getHistoryFinanceId() {
		return historyFinanceId;
	}

	public void setHistoryFinanceId(Long historyFinanceId) {
		this.historyFinanceId = historyFinanceId;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_financeapproval_staff", joinColumns = { @JoinColumn(name = "fapp_id") }, inverseJoinColumns = {
			@JoinColumn(name = "staff_id") })
	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

}
