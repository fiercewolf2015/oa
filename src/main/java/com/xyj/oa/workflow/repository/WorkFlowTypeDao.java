package com.xyj.oa.workflow.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.workflow.entity.WorkFlowType;

public interface WorkFlowTypeDao extends PagingAndSortingRepository<WorkFlowType, Long>, WorkFlowTypeDaoCustom {

}
