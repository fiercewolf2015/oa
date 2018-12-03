package com.xyj.oa.occupation.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

/**
 * 职务
 * 
 * @author lihk
 *
 */
@Entity
@Table(name = "t_occupations")
public class Occupation extends IdEntity {

	private String occupationName;// 职务名称

	private String occupationNo;// 职务代码

	private String remark;// 备注

	private Integer occupationLevel;// 职务层级

	private Occupation parentOccupation;// 上级职务

	private Set<Staff> staffs = new HashSet<>();// 应用该职位的员工

	private Set<Occupation> children = new HashSet<>();// 子职务

	public String getOccupationName() {
		return occupationName;
	}

	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}

	public String getOccupationNo() {
		return occupationNo;
	}

	public void setOccupationNo(String occupationNo) {
		this.occupationNo = occupationNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "occupation")
	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	public Integer getOccupationLevel() {
		return occupationLevel;
	}

	public void setOccupationLevel(Integer occupationLevel) {
		this.occupationLevel = occupationLevel;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Occupation getParentOccupation() {
		return parentOccupation;
	}

	public void setParentOccupation(Occupation parentOccupation) {
		this.parentOccupation = parentOccupation;
	}

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "parentOccupation")
	public Set<Occupation> getChildren() {
		return children;
	}

	public void setChildren(Set<Occupation> children) {
		this.children = children;
	}

}
