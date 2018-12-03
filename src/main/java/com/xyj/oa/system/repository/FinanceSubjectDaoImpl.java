package com.xyj.oa.system.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.xyj.oa.system.entity.FinanceSubject;

public class FinanceSubjectDaoImpl implements FinanceSubjectDaoCustom {

	private static final String COMMON_SQL = " select distinct fs from FinanceSubject as fs where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public FinanceSubject findByName(String name) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and fs.name ='").append(name).append("'");
		Query query = em.createQuery(hql.toString());
		List<FinanceSubject> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadFinanceSubjectTree() {
		StringBuilder sql = new StringBuilder("select fs.name as name, fs.id, fs.parent_id as pId , fs.level, fs.remark,fs.type ");
		sql.append(" from t_financesubjects as fs ");
		Query query = em.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return new ArrayList<Map<String, Object>>();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadLevelFinanceSubjectTree(Long pId, int level) {
		StringBuilder sql = new StringBuilder("select fs.name as name, fs.id, fs.parent_id as pId , fs.level, fs.remark,fs.type ");
		sql.append(" from t_financesubjects as fs  where fs.level = ").append(level);
		if (pId != null)
			sql.append(" and fs.parent_id = ").append(pId);
		Query query = em.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return new ArrayList<Map<String, Object>>();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceSubject> findByIds(String ids) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and fs.id in(").append(ids).append(")");
		Query query = em.createQuery(hql.toString());
		List<FinanceSubject> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceSubject> findByPostIds(String postIds) {
		StringBuilder hql = new StringBuilder("select distinct fs from FinanceSubject as fs inner join fs.posts as p where 1=1");
		hql.append(" and p.id in(").append(postIds).append(")");
		Query query = em.createQuery(hql.toString());
		List<FinanceSubject> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceSubject> findByParentId(Long pid) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and fs.parentFinanceSubject =  ").append(pid);
		Query query = em.createQuery(hql.toString());
		List<FinanceSubject> result = query.getResultList();
		return result;
	}

}
