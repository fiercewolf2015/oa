package com.xyj.oa.finance.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.finance.entity.BudgetModel;

public interface BudgetModelDao extends PagingAndSortingRepository<BudgetModel, Long>, JpaSpecificationExecutor<BudgetModel>,
		BudgetModelDaoCustom {

}
