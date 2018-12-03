package com.xyj.oa.finance.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.finance.entity.FinanceOrganization;

public interface FinanceOrganizationDao extends PagingAndSortingRepository<FinanceOrganization, Long>, JpaSpecificationExecutor<FinanceOrganization>,
		FinanceOrganizationDaoCustom {

}
