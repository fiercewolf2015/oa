package com.xyj.oa.workflow.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.workflow.entity.WorkFlowInstance;

public interface WorkFlowInstanceDao extends PagingAndSortingRepository<WorkFlowInstance, Long> ,WorkFlowInstanceDaoCustom{

}
