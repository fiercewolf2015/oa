package com.xyj.oa.log.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.xyj.oa.log.entity.BizActionLog;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class BizActionLogDaoImpl implements BizActionLogDaoCustom {

	private static final String COMMON_SQL = " select distinct bal from BizActionLog as bal ";

	private static final String COMMON_COUNT_SQL = " select count(distinct bal.id) from BizActionLog as bal  ";
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findFieldByHql(String hql, Object[] params) {
		for (int i = 0; i < params.length; i++) {
			hql = hql.replaceFirst("\\?", (String) params[i]);
		}
		Query query = em.createQuery(hql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findFieldByHql(String hql) {
		Query query = em.createQuery(hql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<BizActionLog> getListWithParams(String params, int pageNo, int pageSize, Long userId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params, userId));
		hql.append(" order by bal.id desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	public Long getCountWithParams(String params, Long userId) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		hql.append(makeWhere(params, userId));
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

	private StringBuilder makeWhere(String params, Long userId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" where bal.creatorId = ").append(userId);
		JSONObject param = TfOaUtil.fromObject(params);
		if (param == null)
			return hql;
		if (StringHelper.isNotEmpty(param.getString("actionType"))) {
			hql.append(" and bal.actionType like '%");
			hql.append(param.getString("actionType")).append("%' ");
		}
		
		if (StringHelper.isNotEmpty(param.getString("bizType"))) {
			hql.append(" and bal.bizType like '%")
			.append(param.getString("bizType"))
			.append("%' ");
		}
		
		if (StringHelper.isNotEmpty(param.getString("bizContent"))) {
			hql.append(" and bal.bizContent like '%")
			.append(param.getString("bizContent"))
			.append("%' ");
		}
		
		if (StringHelper.isNotEmpty(param.getString("beginCreatetime"))) {
			hql.append(" and date(bal.createTime) >= '")
			.append(param.getString("beginCreatetime"))
			.append("' ");
		}
		if (StringHelper.isNotEmpty(param.getString("endCreatetime"))) {
			hql.append(" and date(bal.createTime) <= '")
			.append(param.getString("endCreatetime"))
			.append("' ");
		}
		return hql;
	}
	
}
