package com.xyj.oa.finance.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.system.entity.FinanceSubject;

/**
 * 预算编制实体
 */
@Entity
@Table(name = "t_financeorganizations")
public class FinanceOrganization extends IdEntity {

	private FinanceSubject financeSubjects;// 预算科目

	private FinanceOrganization parentFinanceOrganization;// 父

	private Integer subjectFinanceJanuary;// 一月预算金额

	private Integer subjectFinanceFebruary;// 二月预算金额

	private Integer subjectFinanceMarch;// 三月预算金额

	private Integer subjectFinanceApril;// 四月预算金额

	private Integer subjectFinanceMay;// 五月预算金额

	private Integer subjectFinanceJune;// 六月预算金额

	private Integer subjectFinanceJuly;// 七月预算金额

	private Integer subjectFinanceAugust;// 八月预算金额

	private Integer subjectFinanceSeptember;// 九月预算金额

	private Integer subjectFinanceOctober;// 十月预算金额

	private Integer subjectFinanceNovember;// 十一月预算金额

	private Integer subjectFinanceDecember;// 十二月预算金额

	private Integer subjectFinanceAll;// 全年预算金额

	private FinanceApproval financeApproval;// 对应预算审批实体Id

	private Integer ifChild = 0;// 是否是最底层科目

	public FinanceOrganization() {

	}

	@ManyToOne
	@JoinColumn(name = "sub_id")
	public FinanceSubject getFinanceSubjects() {
		return financeSubjects;
	}

	public void setFinanceSubjects(FinanceSubject financeSubjects) {
		this.financeSubjects = financeSubjects;
	}

	@ManyToOne
	@JoinColumn(name = "app_id")
	public FinanceApproval getFinanceApproval() {
		return financeApproval;
	}

	public void setFinanceApproval(FinanceApproval financeApproval) {
		this.financeApproval = financeApproval;
	}

	public Integer getSubjectFinanceJanuary() {
		return subjectFinanceJanuary;
	}

	public void setSubjectFinanceJanuary(Integer subjectFinanceJanuary) {
		this.subjectFinanceJanuary = subjectFinanceJanuary;
	}

	public Integer getSubjectFinanceFebruary() {
		return subjectFinanceFebruary;
	}

	public void setSubjectFinanceFebruary(Integer subjectFinanceFebruary) {
		this.subjectFinanceFebruary = subjectFinanceFebruary;
	}

	public Integer getSubjectFinanceMarch() {
		return subjectFinanceMarch;
	}

	public void setSubjectFinanceMarch(Integer subjectFinanceMarch) {
		this.subjectFinanceMarch = subjectFinanceMarch;
	}

	public Integer getSubjectFinanceApril() {
		return subjectFinanceApril;
	}

	public void setSubjectFinanceApril(Integer subjectFinanceApril) {
		this.subjectFinanceApril = subjectFinanceApril;
	}

	public Integer getSubjectFinanceMay() {
		return subjectFinanceMay;
	}

	public void setSubjectFinanceMay(Integer subjectFinanceMay) {
		this.subjectFinanceMay = subjectFinanceMay;
	}

	public Integer getSubjectFinanceJune() {
		return subjectFinanceJune;
	}

	public void setSubjectFinanceJune(Integer subjectFinanceJune) {
		this.subjectFinanceJune = subjectFinanceJune;
	}

	public Integer getSubjectFinanceJuly() {
		return subjectFinanceJuly;
	}

	public void setSubjectFinanceJuly(Integer subjectFinanceJuly) {
		this.subjectFinanceJuly = subjectFinanceJuly;
	}

	public Integer getSubjectFinanceAugust() {
		return subjectFinanceAugust;
	}

	public void setSubjectFinanceAugust(Integer subjectFinanceAugust) {
		this.subjectFinanceAugust = subjectFinanceAugust;
	}

	public Integer getSubjectFinanceSeptember() {
		return subjectFinanceSeptember;
	}

	public void setSubjectFinanceSeptember(Integer subjectFinanceSeptember) {
		this.subjectFinanceSeptember = subjectFinanceSeptember;
	}

	public Integer getSubjectFinanceOctober() {
		return subjectFinanceOctober;
	}

	public void setSubjectFinanceOctober(Integer subjectFinanceOctober) {
		this.subjectFinanceOctober = subjectFinanceOctober;
	}

	public Integer getSubjectFinanceNovember() {
		return subjectFinanceNovember;
	}

	public void setSubjectFinanceNovember(Integer subjectFinanceNovember) {
		this.subjectFinanceNovember = subjectFinanceNovember;
	}

	public Integer getSubjectFinanceDecember() {
		return subjectFinanceDecember;
	}

	public void setSubjectFinanceDecember(Integer subjectFinanceDecember) {
		this.subjectFinanceDecember = subjectFinanceDecember;
	}

	public Integer getSubjectFinanceAll() {
		return subjectFinanceAll;
	}

	public void setSubjectFinanceAll(Integer subjectFinanceAll) {
		this.subjectFinanceAll = subjectFinanceAll;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public FinanceOrganization getParentFinanceOrganization() {
		return parentFinanceOrganization;
	}

	public void setParentFinanceOrganization(FinanceOrganization parentFinanceOrganization) {
		this.parentFinanceOrganization = parentFinanceOrganization;
	}

	public Integer getIfChild() {
		return ifChild;
	}

	public void setIfChild(Integer ifChild) {
		this.ifChild = ifChild;
	}

}
