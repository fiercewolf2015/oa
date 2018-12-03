package com.xyj.oa.log.repository;

import java.util.List;

import com.xyj.oa.log.entity.BizActionLog;

public interface BizActionLogDaoCustom {

	List<String> findFieldByHql(String hql,Object[] params);
	
	List<Object> findFieldByHql(String hql);

	List<BizActionLog> getListWithParams(String params, int pageNo, int pageSize, Long userId);

	Long getCountWithParams(String params, Long userId);
	
}
