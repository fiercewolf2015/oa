package com.xyj.oa.finance.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.finance.entity.FinanceApproval;

public interface FinanceApprovalDao extends PagingAndSortingRepository<FinanceApproval, Long>, JpaSpecificationExecutor<FinanceApproval>,
		FinanceApprovalDaoCustom {

}
