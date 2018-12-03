package com.xyj.oa.workflow.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.workflow.entity.WorkFlowRule;

public interface WorkFlowRuleDao extends PagingAndSortingRepository<WorkFlowRule, Long>, WorkFlowRuleDaoCustom {

}
