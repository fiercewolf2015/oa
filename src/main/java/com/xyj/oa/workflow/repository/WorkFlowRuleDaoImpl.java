package com.xyj.oa.workflow.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.workflow.entity.WorkFlowRule;

public class WorkFlowRuleDaoImpl implements WorkFlowRuleDaoCustom {
	private static final String COMMON_SQL = " select distinct wfr from WorkFlowRule as wfr where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct wfr.id) from WorkFlowRule as wfr where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowRule> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" order by wfr.id desc ");
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
	public List<WorkFlowRule> findWorkFlowRuleByWorkFlowId(Long workFlowId) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfr.workFlow.id = ").append(workFlowId);
		sql.append(" and orderNumber != 0");
		sql.append(" order by wfr.orderNumber asc");
		Query query = em.createQuery(sql.toString());
		List<WorkFlowRule> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkFlowRule findfirstRuleByWFId(Long workFlowId) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfr.workFlow.id = ").append(workFlowId);
		sql.append(" order by wfr.orderNumber asc");
		Query query = em.createQuery(sql.toString());
		List<WorkFlowRule> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkFlowRule findApplyRuleByWFId(Long workFlowId) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfr.workFlow.id = ").append(workFlowId);
		sql.append(" order by wfr.orderNumber asc");
		Query query = em.createQuery(sql.toString());
		List<WorkFlowRule> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkFlowRule findWorkFlowRuleByWFAndNum(Long wfId, int pointNum) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfr.workFlow.id = ").append(wfId);
		sql.append(" and wfr.orderNumber = ").append(pointNum);
		Query query = em.createQuery(sql.toString());
		List<WorkFlowRule> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@Override
	public Long findAllWorkFlowRuleNum(Long wfId) {
		StringBuilder sql = new StringBuilder(COMMON_COUNT_SQL);
		sql.append(" and wfr.workFlow.id = ").append(wfId);
		Query query = em.createQuery(sql.toString());
		return (Long) query.getSingleResult();
	}

}
