package com.xyj.oa.statistic.repository;

import java.util.List;

import com.xyj.oa.statistic.entity.ContractWillForm;

public interface ContractWillFormDaoCustom {

	List<ContractWillForm> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	ContractWillForm findByContractNo(String no);
	
	List<ContractWillForm> findAllCwByParams(String params);

}
