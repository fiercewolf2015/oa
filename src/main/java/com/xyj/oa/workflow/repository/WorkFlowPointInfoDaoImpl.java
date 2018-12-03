package com.xyj.oa.workflow.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.workflow.entity.WorkFlowPointInfo;

public class WorkFlowPointInfoDaoImpl implements WorkFlowPointInfoDaoCustom {
	private static final String COMMON_SQL = " select distinct wfp from WorkFlowPointInfo as wfp where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct wfp.id) from WorkFlowPointInfo as wfp where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowPointInfo> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" order by wfp.id desc ");
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
	public WorkFlowPointInfo findWorkFlowPointInfoByWF(Long wfId) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfp.workFlowInstance.id = ").append(wfId);
		sql.append(" order by wfp.pointNum asc");
		Query query = em.createQuery(sql.toString());
		List<WorkFlowPointInfo> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkFlowPointInfo findWorkFlowPointInfoByWFAndNum(Long wfId, int pointNum) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfp.workFlowInstance.id = ").append(wfId);
		sql.append(" and wfp.pointNum = ").append(pointNum);
		Query query = em.createQuery(sql.toString());
		List<WorkFlowPointInfo> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowPointInfo> findAllWorkFlowPointInfo(Long wfId) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfp.workFlowInstance.id = ").append(wfId);
		sql.append(" order by wfp.id desc");
		Query query = em.createQuery(sql.toString());
		List<WorkFlowPointInfo> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList;
	}

	@Override
	public Long findAllWorkFlowPointInfoNum(Long wfId) {
		StringBuilder sql = new StringBuilder(COMMON_COUNT_SQL);
		sql.append(" and wfp.workFlowInstance.id = ").append(wfId);
		Query query = em.createQuery(sql.toString());
		return (Long) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findCheckIdsByStaffId(Long staffId) {
		StringBuilder sql = new StringBuilder("select distinct wfp.workFlowInstance.id from WorkFlowPointInfo as wfp where 1=1");
		sql.append(" and wfp.approvalStaffId = ").append(staffId);
		Query query = em.createQuery(sql.toString());
		List<Long> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkFlowPointInfo findWorkFlowPointInfoByWfIdAndStaffId(Long wfId, Long staffId) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfp.workFlowInstance.id = ").append(wfId);
		sql.append(" and wfp.approvalStaffId = ").append(staffId);
		Query query = em.createQuery(sql.toString());
		List<WorkFlowPointInfo> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}
}
