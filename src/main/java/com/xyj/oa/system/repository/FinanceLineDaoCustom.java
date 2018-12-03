package com.xyj.oa.system.repository;

import java.util.List;

import com.xyj.oa.system.entity.FinanceLine;

public interface FinanceLineDaoCustom {

	List<FinanceLine> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	FinanceLine getFinanceLine();

}
