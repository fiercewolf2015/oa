package com.xyj.oa.finance.repository;

import java.util.List;

import com.xyj.oa.finance.entity.FinanceApproval;

public interface FinanceApprovalDaoCustom {

	List<FinanceApproval> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord, int flag,Integer history);

	Long getCountWithParams(String params, int flag,Integer history);

	List<String> findYearsByDepartmentIds(String ids);

	Integer findFinanceSubjectBalance(Long departMentId, Long subjectId);

	Integer findHistoryFinance(Long faId);

}
