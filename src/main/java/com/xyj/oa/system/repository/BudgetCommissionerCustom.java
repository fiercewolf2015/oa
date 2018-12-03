package com.xyj.oa.system.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.system.entity.BudgetCommissioner;

public interface BudgetCommissionerCustom {

	List<BudgetCommissioner> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	BudgetCommissioner findByName(String typeName);

	List<Map<String, Object>> findAllBudgetCommissioners();
}
