package com.xyj.oa.system.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.system.entity.SealType;

public interface SealTypeDaoCustom {

	List<SealType> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	SealType findByName(String typeName);
	
	List<Map<String, Object>> findAllSealTypes();
}
