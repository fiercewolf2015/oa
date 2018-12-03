package com.xyj.oa.system.repository;

import java.util.List;

import com.xyj.oa.system.entity.Notice;

public interface NoticeDaoCustom {

	List<Notice> getListWithParams(String params, Long staffId, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params, Long staffId);

	Notice findByName(String title);
	
	Long getUnreadNum(Long staffId);

}
