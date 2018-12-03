package com.xyj.oa.system.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.system.entity.LeaveType;

public interface LeaveTypeDaoCustom {

	List<LeaveType> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	LeaveType findByName(String titleName);
	
	List<Map<String, Object>> findAllLeaveTypes();

}
