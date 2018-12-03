package com.xyj.oa.title.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.title.entity.Title;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class TitleDaoImpl implements TitleDaoCustom {
	private static final String COMMON_SQL = " select distinct t from Title as t where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct t.id) from Title as t where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Title> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by t.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by t.id desc ");
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
		if (param.has("titleName") && StringHelper.isNotEmpty(param.getString("titleName"))) {
			hql.append(" and t.titleName like '%");
			hql.append(param.getString("titleName"));
			hql.append("%' ");
		}
		if (param.has("titleNo") && StringHelper.isNotEmpty(param.getString("titleNo"))) {
			hql.append(" and t.titleNo like '%");
			hql.append(param.getString("titleNo"));
			hql.append("%' ");
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Title findByTitleName(String titleName) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and t.titleName ='").append(titleName).append("'");
		Query query = em.createQuery(hql.toString());
		List<Title> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Title findByTitleNo(String titleNo) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and t.titleNo ='").append(titleNo).append("'");
		Query query = em.createQuery(hql.toString());
		List<Title> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAllTitles() {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		Query query = em.createQuery(hql.toString());
		List<Title> titleList = query.getResultList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < titleList.size(); i++) {
			Title title = titleList.get(i);
			String name = title.getTitleName();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", title.getId());
			map.put("text", name);
			list.add(map);
		}
		return list;
	}
}
