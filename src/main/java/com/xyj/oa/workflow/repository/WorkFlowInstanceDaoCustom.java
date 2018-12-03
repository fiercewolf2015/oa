package com.xyj.oa.workflow.repository;

import java.util.List;

import com.xyj.oa.workflow.entity.WorkFlowInstance;

public interface WorkFlowInstanceDaoCustom {

	List<WorkFlowInstance> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord,
			Long staffId, String checkIds, Integer searchType);

	Long getCountWithParams(String params, Long staffId, String checkIds, Integer searchType);

	Long findInstanceByWfId(Long wfId);

	double findAllOvertimeByStaffId(Long staffId, int workFlowType);

	String findInstanceNumByWorkFlowTypeId(Long wftId);

	List<WorkFlowInstance> findWFIByWorkType();

	WorkFlowInstance findByReserve2(String contractNo);

}
