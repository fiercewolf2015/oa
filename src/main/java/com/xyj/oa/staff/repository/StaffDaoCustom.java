package com.xyj.oa.staff.repository;

import java.util.List;
import java.util.Set;

import com.xyj.oa.staff.entity.Staff;

public interface StaffDaoCustom {
	List<Staff> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	int findCurMonthMaxStaffNo(String staffNo);

	Set<Staff> findStaffByIdsOrPostIdsOrOccupationIds(String staffIds, String postIds, String occupationIds, String ids);

	Staff findByNo(String no);

	Staff findStaffByDeptAndPost(String deptName, String postName);

	Staff findStaffBydeptIdsAndPost(String deptIds, String postName);

	Set<Staff> findStaffByPost(String postName);

	Set<Staff> findStaffByDept(String deptName, String postName);

	Staff findStaffByPostAndOcc(String postName, String occName);

	Staff findByOldId(String oId);
	
	Staff findByNameAndNo(String name,String staffNo);
}
