package com.xyj.oa.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyj.oa.department.entity.Department;

public class ParentDeptVo {

	@JsonProperty("id")
	private String departMentId;

	@JsonProperty("name")
	private String departmentName;

	//	@JsonProperty("staff")
	//	private StaffVo staffVo;

	@JsonProperty("child")
	private ChildDeptVo[] childDeptVos;

	public ParentDeptVo() {

	}

	public ParentDeptVo(Department department, ChildDeptVo[] childDeptVos) {
		this.departMentId = String.valueOf(department.getId());
		this.departmentName = department.getDepartmentName();
		this.childDeptVos = childDeptVos;
	}

	public String getDepartMentId() {
		return departMentId;
	}

	public void setDepartMentId(String departMentId) {
		this.departMentId = departMentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public ChildDeptVo[] getChildDeptVos() {
		return childDeptVos;
	}

	public void setChildDeptVos(ChildDeptVo[] childDeptVos) {
		this.childDeptVos = childDeptVos;
	}

	//	public StaffVo getStaffVo() {
	//		return staffVo;
	//	}
	//
	//	public void setStaffVo(StaffVo staffVo) {
	//		this.staffVo = staffVo;
	//	}

}
