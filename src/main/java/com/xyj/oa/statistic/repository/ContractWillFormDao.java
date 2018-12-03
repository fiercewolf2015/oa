package com.xyj.oa.statistic.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.statistic.entity.ContractWillForm;

public interface ContractWillFormDao extends PagingAndSortingRepository<ContractWillForm, Long>, ContractWillFormDaoCustom {

}
