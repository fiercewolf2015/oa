package com.xyj.oa.finance.repository;

import java.util.List;

import com.xyj.oa.finance.entity.FinanceOrganization;

public interface FinanceOrganizationDaoCustom {

	List<FinanceOrganization> findFinaceOrGanizationP(Long appId, String subjectIds);

	List<FinanceOrganization> findFinaceOrGanizationChild(Long pId, Long appId);

	List<FinanceOrganization> findDifferentLevelByDepartmentIdAndYear(String departmentIds, String year, int level);

}
