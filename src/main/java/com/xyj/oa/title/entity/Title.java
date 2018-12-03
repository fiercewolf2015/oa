package com.xyj.oa.title.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.staff.entity.Staff;

/**
 * 职称
 * 
 * @author lihk
 *
 */
@Entity
@Table(name = "t_titles")
public class Title extends IdEntity {

	private String titleName;// 职称名称

	private String titleNo;// 职称代码

	private String remark;// 备注

	private Set<Staff> staffs = new HashSet<>();// 应用该职称的员工

	@JsonIgnore
	@OneToMany(mappedBy = "title")
	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getTitleNo() {
		return titleNo;
	}

	public void setTitleNo(String titleNo) {
		this.titleNo = titleNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
