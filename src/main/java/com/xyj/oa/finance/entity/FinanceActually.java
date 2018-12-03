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
 * 实际预算发生对比
 */
@Entity
@Table(name = "t_financeactuallys")
public class FinanceActually extends IdEntity {

	private Staff staff;// 报销人

	private Department department;// 报销人所在部门部门

	private String actuallyMonth;// 实际发生月份

	private Set<FinanceActuallySubject> financeActuallySubjects = new HashSet<FinanceActuallySubject>();

	private Timestamp createTime;

	@ManyToOne
	@JoinColumn(name = "staff_id")
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@ManyToOne
	@JoinColumn(name = "dept_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getActuallyMonth() {
		return actuallyMonth;
	}

	public void setActuallyMonth(String actuallyMonth) {
		this.actuallyMonth = actuallyMonth;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_finance_actually_subject", joinColumns = {
			@JoinColumn(name = "fa_id") }, inverseJoinColumns = { @JoinColumn(name = "fac_id") })
	public Set<FinanceActuallySubject> getFinanceActuallySubjects() {
		return financeActuallySubjects;
	}

	public void setFinanceActuallySubjects(Set<FinanceActuallySubject> financeActuallySubjects) {
		this.financeActuallySubjects = financeActuallySubjects;
	}

}
