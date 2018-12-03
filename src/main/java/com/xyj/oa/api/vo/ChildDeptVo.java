package com.xyj.oa.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyj.oa.department.entity.Department;

public class ChildDeptVo {

	@JsonProperty("id")
	private String departMentId;

	@JsonProperty("name")
	private String departmentName;

	@JsonProperty("staff")
	private StaffVo[] staffVos;

	public ChildDeptVo() {

	}

	public ChildDeptVo(Department department, StaffVo[] staffVos) {
		this.departMentId = String.valueOf(department.getId());
		this.departmentName = department.getDepartmentName();
		this.staffVos = staffVos;
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

	public StaffVo[] getStaffVos() {
		return staffVos;
	}

	public void setStaffVos(StaffVo[] staffVos) {
		this.staffVos = staffVos;
	}

}
