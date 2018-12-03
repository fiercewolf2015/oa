package com.xyj.oa.finance.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.system.entity.FinanceSubject;

/**
 *预算编制模板 
 */
@Entity
@Table(name = "t_budgetmodels")
public class BudgetModel extends IdEntity{
	
	private String name;
	
	private String remark;
	
	private Set<FinanceSubject> financeSubjects = new HashSet<FinanceSubject>();
	
	private String subIds;//不实例化到数据库

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

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_budgetmodel_subject", joinColumns = { @JoinColumn(name = "model_id") }, inverseJoinColumns = { @JoinColumn(name = "sub_id") })
	public Set<FinanceSubject> getFinanceSubjects() {
		return financeSubjects;
	}

	public void setFinanceSubjects(Set<FinanceSubject> financeSubjects) {
		this.financeSubjects = financeSubjects;
	}

	@Transient
	public String getSubIds() {
		return subIds;
	}

	public void setSubIds(String subIds) {
		this.subIds = subIds;
	}
	
}
