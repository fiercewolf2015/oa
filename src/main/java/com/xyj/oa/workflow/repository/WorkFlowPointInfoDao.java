package com.xyj.oa.workflow.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.workflow.entity.WorkFlowPointInfo;

public interface WorkFlowPointInfoDao extends PagingAndSortingRepository<WorkFlowPointInfo, Long>, WorkFlowPointInfoDaoCustom {

}
