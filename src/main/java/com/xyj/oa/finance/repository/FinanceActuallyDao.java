package com.xyj.oa.finance.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.finance.entity.FinanceActually;

public interface FinanceActuallyDao extends PagingAndSortingRepository<FinanceActually, Long>, JpaSpecificationExecutor<FinanceActually>,
		FinanceActuallyDaoCustom {

}
