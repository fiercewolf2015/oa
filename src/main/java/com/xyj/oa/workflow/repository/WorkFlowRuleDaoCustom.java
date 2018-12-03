package com.xyj.oa.workflow.repository;

import java.util.List;

import com.xyj.oa.workflow.entity.WorkFlowRule;

public interface WorkFlowRuleDaoCustom {

	List<WorkFlowRule> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	List<WorkFlowRule> findWorkFlowRuleByWorkFlowId(Long workFlowId);

	WorkFlowRule findfirstRuleByWFId(Long workFlowId);

	WorkFlowRule findWorkFlowRuleByWFAndNum(Long wfId, int pointNum);

	Long findAllWorkFlowRuleNum(Long wfId);

	WorkFlowRule findApplyRuleByWFId(Long workFlowId);

}
