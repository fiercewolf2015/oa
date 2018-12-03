package com.xyj.oa.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyj.oa.staff.entity.Staff;

public class SearchStaffVo {

	@JsonProperty("id")
	private String staffId;

	@JsonProperty("name")
	private String staffName;

	@JsonProperty("mobile")
	private String mobilePhone;

	@JsonProperty("fixedPhone")
	private String fixedPhone;

	@JsonProperty("email")
	private String email;

	@JsonProperty("deptName")
	private String deptName;

	public SearchStaffVo() {

	}

	public SearchStaffVo(Staff staff) {
		this.staffId = String.valueOf(staff.getId());
		this.staffName = staff.getStaffName();
		this.mobilePhone = staff.getMobilePhone();
		this.fixedPhone = staff.getFixedPhone();
		this.email = staff.getEmail();
		this.deptName = staff.getDepartment().getDepartmentName();
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFixedPhone() {
		return fixedPhone;
	}

	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
