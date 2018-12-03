package com.xyj.oa.workflow.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.xyj.oa.workflow.entity.WorkFlowType;

public class WorkFlowTypeDaoImpl implements WorkFlowTypeDaoCustom {
	private static final String COMMON_SQL = " select distinct wft from WorkFlowType as wft where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct wft.id) from WorkFlowType as wft where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowType> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" order by wft.id desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long getCountWithParams(String params) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAllWorkflowTypes() {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		Query query = em.createQuery(hql.toString());
		List<WorkFlowType> workFlowTypesList = query.getResultList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < workFlowTypesList.size(); i++) {
			WorkFlowType workFlowType = workFlowTypesList.get(i);
			String name = workFlowType.getName();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", workFlowType.getId());
			map.put("text", name);
			list.add(map);
		}
		return list;
	}

}
