package com.xyj.oa.workflow.repository;

import java.util.List;

import com.xyj.oa.workflow.entity.WorkFlow;

public interface WorkFlowDaoCustom {

	List<WorkFlow> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	WorkFlow findWorkFlowByStaffAndType(Long staffId, Long typeId);

	WorkFlow findWorkFlowByType(Long typeId);

	List<WorkFlow> findWorkFlowByStaffId(Long staffId);

}
