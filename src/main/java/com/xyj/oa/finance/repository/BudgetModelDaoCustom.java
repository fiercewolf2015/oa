package com.xyj.oa.finance.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.finance.entity.BudgetModel;

public interface BudgetModelDaoCustom {

	List<BudgetModel> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	BudgetModel findByName(String name);

	List<Map<String, Object>> findAllFinanceSubjectByModelId(Long modelId);

	BudgetModel findBySubId(Long subId);

}
