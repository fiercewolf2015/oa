package com.xyj.oa.system.repository;

import java.util.List;

import com.xyj.oa.system.entity.NoticeStaff;

public interface NoticeStaffDaoCustom {

	List<NoticeStaff> getListWithParams(String params, Long staffId, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params, Long staffId);

}
