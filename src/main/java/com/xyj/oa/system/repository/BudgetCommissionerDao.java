package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.BudgetCommissioner;

public interface BudgetCommissionerDao extends PagingAndSortingRepository<BudgetCommissioner, Long>,BudgetCommissionerCustom{

}
