package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.OverTimeType;

public interface OverTimeTypeDao extends PagingAndSortingRepository<OverTimeType, Long>, OverTimeTypeDaoCustom {

}
