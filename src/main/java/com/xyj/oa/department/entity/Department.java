package com.xyj.oa.department.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.staff.entity.Staff;

@Entity
@Table(name = "t_departments")
public class Department extends IdEntity {

	private String oldId;// 金蝶系统中的id

	private String departmentName;// 部门名称

	private String departmentNo;// 部门代码

	private String departmentDescribe;// 部门描述

	private Integer departmentLevel;// 部门层级

	private Department parentDepartment;// 上级部门

	private String remarks;// 备注

	private Set<Staff> staffs = new HashSet<>();// 该部门对应的员工

	private Set<Post> posts = new HashSet<>();// 该部门对应的岗位

	private Set<Department> children = new HashSet<Department>();

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Department getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "department")
	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getDepartmentDescribe() {
		return departmentDescribe;
	}

	public void setDepartmentDescribe(String departmentDescribe) {
		this.departmentDescribe = departmentDescribe;
	}

	public Integer getDepartmentLevel() {
		return departmentLevel;
	}

	public void setDepartmentLevel(Integer departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_post_departments", joinColumns = { @JoinColumn(name = "department_id") }, inverseJoinColumns = {
			@JoinColumn(name = "post_id") })
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "parentDepartment")
	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

}
