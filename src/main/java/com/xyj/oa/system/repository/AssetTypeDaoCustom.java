package com.xyj.oa.system.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.system.entity.AssetType;

public interface AssetTypeDaoCustom {

	List<AssetType> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	AssetType findByName(String typeName);

	List<Map<String, Object>> findAllAssetTypes();
}
