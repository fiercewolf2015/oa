package com.xyj.oa.workflow.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;
import com.xyj.oa.workflow.entity.WorkFlow;

import net.sf.json.JSONObject;

public class WorkFlowDaoImpl implements WorkFlowDaoCustom {
	private static final String COMMON_SQL = " select distinct wf from WorkFlow as wf ";

	private static final String COMMON_COUNT_SQL = " select count(distinct wf.id) from WorkFlow as wf ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlow> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params));
		hql.append(" order by wf.version desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long getCountWithParams(String params) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		hql.append(makeWhere(params));
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

	private StringBuilder makeWhere(String params) {
		StringBuilder hql = new StringBuilder();
		JSONObject param = TfOaUtil.fromObject(params);
		if (param == null)
			return hql;
		if (param.has("workflowType") && StringHelper.isNotEmpty(param.getString("workflowType"))) {
			String workflowType = param.getString("workflowType");
			hql.append(" inner join wf.workFlowType as wft where wft.id =").append(workflowType);
		} else {
			hql.append(" where 1=1 ");
		}
		if (param.has("workflowName") && StringHelper.isNotEmpty(param.getString("workflowName"))) {
			String workflowName = param.getString("workflowName");
			hql.append(" and wf.name like '%").append(workflowName).append("%'");
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkFlow findWorkFlowByStaffAndType(Long staffId, Long typeId) {
		StringBuilder sql = new StringBuilder("select distinct wf from WorkFlow as wf ")
				.append(" inner join wf.staffs as ws");
		sql.append(" where ws.id = ").append(staffId).append(" and wf.workFlowType.id = ").append(typeId);
		Query query = em.createQuery(sql.toString());
		List<WorkFlow> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkFlow findWorkFlowByType(Long typeId) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" where wf.workFlowType.id = ").append(typeId);
		sql.append(" order by wf.version desc ");
		Query query = em.createQuery(sql.toString());
		List<WorkFlow> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlow> findWorkFlowByStaffId(Long staffId) {
		StringBuilder sql = new StringBuilder("select distinct wf from WorkFlow as wf ")
				.append(" inner join wf.staffs as ws");
		sql.append(" where ws.id = ").append(staffId);
		sql.append(" order by wf.workFlowType.id asc");
		Query query = em.createQuery(sql.toString());
		List<WorkFlow> resultList = query.getResultList();
		return resultList;
	}

}
