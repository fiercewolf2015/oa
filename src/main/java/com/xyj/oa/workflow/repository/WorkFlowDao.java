package com.xyj.oa.workflow.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.workflow.entity.WorkFlow;

public interface WorkFlowDao extends PagingAndSortingRepository<WorkFlow, Long>, WorkFlowDaoCustom {

}
