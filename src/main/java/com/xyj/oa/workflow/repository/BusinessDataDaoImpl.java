package com.xyj.oa.workflow.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.workflow.entity.BusinessData;

public class BusinessDataDaoImpl implements BusinessDataDaoCustom {

	private static final String COMMON_SQL = " select distinct bd from BusinessData as bd where 1=1 ";

//	private static final String COMMON_COUNT_SQL = " select count(distinct bd.id) from BusinessData as bd where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public BusinessData findBusinessDataByWFIId(Long workFlowInstanceId) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" where bd.workFlowInstanceId = ").append(workFlowInstanceId);
		Query query = em.createQuery(sql.toString());
		List<BusinessData> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

}
