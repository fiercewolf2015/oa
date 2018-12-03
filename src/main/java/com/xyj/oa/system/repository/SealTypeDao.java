package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.SealType;

public interface SealTypeDao extends PagingAndSortingRepository<SealType, Long>,SealTypeDaoCustom{

}
