package com.xyj.oa.system.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.system.entity.FinanceLine;
import com.xyj.oa.util.StringHelper;

public class FinanceLineDaoImpl implements FinanceLineDaoCustom {

	private static final String COMMON_SQL = " select distinct fl from FinanceLine as fl where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct fl.id) from FinanceLine as fl where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceLine> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by fl.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by fl.id desc ");
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
	public FinanceLine getFinanceLine() {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		Query query = em.createQuery(hql.toString());
		List<FinanceLine> resultList = query.getResultList();
		if(CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

}
