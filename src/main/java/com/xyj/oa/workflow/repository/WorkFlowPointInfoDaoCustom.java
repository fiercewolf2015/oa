package com.xyj.oa.workflow.repository;

import java.util.List;

import com.xyj.oa.workflow.entity.WorkFlowPointInfo;

public interface WorkFlowPointInfoDaoCustom {

	List<WorkFlowPointInfo> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	WorkFlowPointInfo findWorkFlowPointInfoByWF(Long wfId);

	WorkFlowPointInfo findWorkFlowPointInfoByWFAndNum(Long wfId, int pointNum);

	List<WorkFlowPointInfo> findAllWorkFlowPointInfo(Long wfId);

	Long findAllWorkFlowPointInfoNum(Long wfId);

	List<Long> findCheckIdsByStaffId(Long staffId);

	WorkFlowPointInfo findWorkFlowPointInfoByWfIdAndStaffId(Long wfId, Long staffId);

}
