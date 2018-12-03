package com.xyj.oa.workflow.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.workflow.entity.WorkFlowType;

public interface WorkFlowTypeDaoCustom {

	List<WorkFlowType> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	List<Map<String, Object>> findAllWorkflowTypes();

}
