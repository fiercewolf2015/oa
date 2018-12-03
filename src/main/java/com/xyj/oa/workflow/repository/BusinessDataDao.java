package com.xyj.oa.workflow.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.workflow.entity.BusinessData;

public interface BusinessDataDao extends PagingAndSortingRepository<BusinessData, Long>, BusinessDataDaoCustom {

}
