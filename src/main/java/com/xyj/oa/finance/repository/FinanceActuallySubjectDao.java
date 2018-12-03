package com.xyj.oa.finance.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.finance.entity.FinanceActuallySubject;

public interface FinanceActuallySubjectDao extends PagingAndSortingRepository<FinanceActuallySubject, Long>, JpaSpecificationExecutor<FinanceActuallySubject>,
		FinanceActuallySubjectDaoCustom {

}
