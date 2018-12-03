package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.FinanceSubject;

public interface FinanceSubjectDao extends PagingAndSortingRepository<FinanceSubject, Long>, FinanceSubjectDaoCustom {

}
