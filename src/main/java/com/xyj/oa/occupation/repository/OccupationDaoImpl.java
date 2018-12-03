package com.xyj.oa.occupation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.xyj.oa.occupation.entity.Occupation;

public class OccupationDaoImpl implements OccupationDaoCustom {

	private static final String COMMON_SQL = " select distinct o from Occupation as o where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Occupation findByOccupationName(String occupationName) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and o.occupationName ='").append(occupationName).append("'");
		Query query = em.createQuery(hql.toString());
		List<Occupation> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Occupation findByOccupationNo(String occupationNo) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and o.occupationNo ='").append(occupationNo).append("'");
		Query query = em.createQuery(hql.toString());
		List<Occupation> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadOccupationTree() {
		StringBuilder sql = new StringBuilder(
				"select o.occupation_name as name, o.id, o.parent_id as pId , o.occupation_no, o.occupation_level, o.remark ");
		sql.append(" from t_occupations as o ");
		Query query = em.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return new ArrayList<Map<String, Object>>();
		return resultList;
	}

}
