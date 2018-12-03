package com.xyj.oa.post.entity;

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
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

/**
 * 岗位
 * 
 * @author lihk
 *
 */
@Entity
@Table(name = "t_posts")
public class Post extends IdEntity {

	private String oldId;// 金蝶系统中的id

	private String postName;// 岗位名称

	private String postNo;// 岗位代码

	private String postDescribe;// 岗位描述

	private String remarks;// 备注

	private Set<Staff> staffs = new HashSet<>();// 该岗位对应的员工

	private Set<Department> departments = new HashSet<>();// 该岗位对应的部门

	private String deptNames;// 该岗位对应的部门名称，不实例化到数据库，供前台适用;

	private String deptIds;// 该岗位对应的部门id,不实例化到数据库，供前台适用;

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getPostDescribe() {
		return postDescribe;
	}

	public void setPostDescribe(String postDescribe) {
		this.postDescribe = postDescribe;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_staff_posts", joinColumns = { @JoinColumn(name = "post_id") }, inverseJoinColumns = {
			@JoinColumn(name = "staff_id") })
	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_post_departments", joinColumns = { @JoinColumn(name = "post_id") }, inverseJoinColumns = {
			@JoinColumn(name = "department_id") })
	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	@Transient
	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	@Transient
	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

}
