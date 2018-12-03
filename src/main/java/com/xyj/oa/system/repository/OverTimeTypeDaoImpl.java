package com.xyj.oa.system.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.system.entity.OverTimeType;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class OverTimeTypeDaoImpl implements OverTimeTypeDaoCustom {
	private static final String COMMON_SQL = " select distinct ot from OverTimeType as ot where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct ot.id) from OverTimeType as ot where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<OverTimeType> getListWithParams(String params, int pageNo, int pageSize,String sidx,String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by ot.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by ot.id desc ");
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
		if (param.has("overtimeTypeName") && StringHelper.isNotEmpty(param.getString("overtimeTypeName"))) {
			hql.append(" and ot.name like '%");
			hql.append(param.getString("overtimeTypeName"));
			hql.append("%' ");
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OverTimeType findByName(String name) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and ot.name ='").append(name).append("'");
		Query query = em.createQuery(hql.toString());
		List<OverTimeType> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAllOverTimeTypes() {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		Query query = em.createQuery(hql.toString());
		List<OverTimeType> overTimeTypesList = query.getResultList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < overTimeTypesList.size(); i++) {
			OverTimeType overTimeType = overTimeTypesList.get(i);
			String name = overTimeType.getName();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", overTimeType.getId());
			map.put("text", name);
			list.add(map);
		}
		return list;
	}

}
