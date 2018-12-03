package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.FinanceLine;

public interface FinanceLineDao extends PagingAndSortingRepository<FinanceLine, Long>, FinanceLineDaoCustom {

}
