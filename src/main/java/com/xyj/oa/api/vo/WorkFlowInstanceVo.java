package com.xyj.oa.api.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyj.oa.workflow.entity.WorkFlowInstance;

public class WorkFlowInstanceVo extends ApiResult {

	@JsonProperty("DATA")
	List<WorkFlowInstance> listWithParams = new ArrayList<WorkFlowInstance>();

	public WorkFlowInstanceVo(List<WorkFlowInstance> listWithParams, int total) {
		if (CollectionUtils.isEmpty(listWithParams))
			return;
		this.result = total;
		this.listWithParams = listWithParams;
	}

}
