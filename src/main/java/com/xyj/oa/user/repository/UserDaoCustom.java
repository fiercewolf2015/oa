package com.xyj.oa.user.repository;

import java.util.List;

import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.user.entity.User;

public interface UserDaoCustom {
	List<User> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	User findByStaffId(Long staffId);

	List<Staff> findByStaffNameOrDepartmentName(String searchName);
}
