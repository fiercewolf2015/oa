package com.xyj.oa.workflow.repository;

import com.xyj.oa.workflow.entity.BusinessData;

public interface BusinessDataDaoCustom {

	BusinessData findBusinessDataByWFIId(Long workFlowInstanceId);

}
