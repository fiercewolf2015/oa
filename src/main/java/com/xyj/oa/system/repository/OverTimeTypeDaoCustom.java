package com.xyj.oa.system.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.system.entity.OverTimeType;

public interface OverTimeTypeDaoCustom {

	List<OverTimeType> getListWithParams(String params, int pageNo, int pageSize,String sidx,String sord);

	Long getCountWithParams(String params);

	OverTimeType findByName(String titleName);
	
	List<Map<String, Object>> findAllOverTimeTypes();

}
