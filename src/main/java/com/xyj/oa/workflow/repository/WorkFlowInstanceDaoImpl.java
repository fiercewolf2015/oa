package com.xyj.oa.workflow.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;
import com.xyj.oa.workflow.entity.WorkFlowInstance;

import net.sf.json.JSONObject;

public class WorkFlowInstanceDaoImpl implements WorkFlowInstanceDaoCustom {
	private static final String COMMON_SQL = " select distinct wfi from WorkFlowInstance as wfi where 1=1";

	private static final String COMMON_COUNT_SQL = " select count(distinct wfi.id) from WorkFlowInstance as wfi where 1=1";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowInstance> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord,
			Long staffId, String checkIds, Integer searchType) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params, staffId, checkIds, searchType));
		hql.append(" order by wfi.pointReceiveTime desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long getCountWithParams(String params, Long staffId, String checkIds, Integer searchType) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		hql.append(makeWhere(params, staffId, checkIds, searchType));
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

	private StringBuilder makeWhere(String params, Long staffId, String checkIds, Integer searchType) {
		StringBuilder hql = new StringBuilder();
		if (searchType == 1) {
			hql.append(" and wfi.isComplete =0");
			hql.append(" and (wfi.managerStaffId = ").append(staffId);
			hql.append(" or wfi.reserve11 like '%").append(staffId).append(",%' )");
		} else if (searchType == 2) {
			hql.append(" and wfi.isComplete =0");
			hql.append(" and wfi.id in (").append(checkIds).append(") ");
		} else if (searchType == 3) {
			hql.append(" and (wfi.isComplete = 1 or wfi.isComplete = 2) ");
			hql.append(" and wfi.id in (").append(checkIds).append(") ");
		} else {
			hql.append(" and wfi.isComplete =0");
		}
		JSONObject param = TfOaUtil.fromObject(params);
		if (param == null)
			return hql;
		// 统一搜索
		if (param.has("companyForSearch") && StringHelper.isNotEmpty(param.getString("companyForSearch"))) {
			String company = param.getString("companyForSearch");
			hql.append(" and wfi.reserve13 like '%").append(company).append("%' ");
		}
		if (param.has("project") && StringHelper.isNotEmpty(param.getString("project"))) {
			String project = param.getString("project");
			hql.append(" and wfi.reserve14 like '%").append(project).append("%' ");
		}
		if (param.has("applyStaff") && StringHelper.isNotEmpty(param.getString("applyStaff"))) {
			String applyStaff = param.getString("applyStaff");
			hql.append(" and wfi.applyUser like '%").append(applyStaff).append("%' ");
		}
		if (param.has("applyStaffDept") && StringHelper.isNotEmpty(param.getString("applyStaffDept"))) {
			String applyStaffDept = param.getString("applyStaffDept");
			hql.append(" and wfi.applyUserDeptName like '%").append(applyStaffDept).append("%' ");
		}
		if (param.has("applyDateStart") && StringHelper.isNotEmpty(param.getString("applyDateStart"))) {
			String applyDateStart = param.getString("applyDateStart");
			hql.append(" and wfi.reserve20 >= '").append(applyDateStart).append("' ");
		}
		if (param.has("applyDateEnd") && StringHelper.isNotEmpty(param.getString("applyDateEnd"))) {
			String applyDateEnd = param.getString("applyDateEnd");
			hql.append(" and wfi.reserve20 <='").append(applyDateEnd).append("' ");
		}
		if (param.has("workflowType") && StringHelper.isNotEmpty(param.getString("workflowType"))) {
			String workflowType = param.getString("workflowType");
			hql.append(" and wfi.workFlow.workFlowType.id =").append(workflowType);
		}
		return hql;
	}

	@Override
	public Long findInstanceByWfId(Long wfId) {
		StringBuilder sql = new StringBuilder(COMMON_COUNT_SQL);
		sql.append(" and wfi.workFlow.id = ").append(wfId);
		Query query = em.createQuery(sql.toString());
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public double findAllOvertimeByStaffId(Long staffId, int workFlowType) {
		StringBuilder sql = new StringBuilder("select sum(wfi.reserve1) from WorkFlowInstance wfi");
		sql.append(" where wfi.staffId = ").append(staffId);
		sql.append(" and wfi.workFlow.workFlowType.id = ").append(workFlowType);
		sql.append(" and wfi.reserve2 >= '").append(TfOaUtil.getFirstDay()).append(" '");
		sql.append(" and wfi.reserve3 <= '").append(TfOaUtil.getLastDay()).append("' ");
		Query query = em.createQuery(sql.toString());
		List<Double> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList) || resultList.get(0) == null)
			return 0;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findInstanceNumByWorkFlowTypeId(Long wftId) {
		StringBuilder sql = new StringBuilder("select wfi.instanceNum from WorkFlowInstance wfi");
		sql.append(" where wfi.workFlow.workFlowType.id = ").append(wftId);
		sql.append(" order by wfi.pointCreateTime desc ");
		Query query = em.createQuery(sql.toString());
		List<String> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return "";
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowInstance> findWFIByWorkType() {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfi.workFlow.workFlowType in(1,2)").append(" and wfi.isComplete = 1");
		Query query = em.createQuery(sql.toString());
		List<WorkFlowInstance> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkFlowInstance findByReserve2(String contractNo) {
		StringBuilder sql = new StringBuilder(COMMON_SQL);
		sql.append(" and wfi.reserve2 = '").append(contractNo).append("' ");
		sql.append(" and wfi.isComplete = 1");
		Query query = em.createQuery(sql.toString());
		List<WorkFlowInstance> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

}
