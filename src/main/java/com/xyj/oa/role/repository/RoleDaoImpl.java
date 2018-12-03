package com.xyj.oa.role.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.util.CollectionUtils;

import com.xyj.oa.role.entity.Role;

public class RoleDaoImpl implements RoleDaoCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadRoleTree() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select name, id, 'parent' as pId,description from t_roles ");
		Query query = em.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return new ArrayList<Map<String, Object>>();
		Map<String, Object> parent = new HashMap<String, Object>();
		parent.put("name", "角色");
		parent.put("id", "parent");
		parent.put("pId", null);
		parent.put("description", "");
		resultList.add(0, parent);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Role findByName(String name) {
		StringBuilder sql = new StringBuilder(" select r from Role as r ");
		sql.append(" where r.name ='").append(name).append("' ");
		Query query = em.createQuery(sql.toString());
		List<Role> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}
}
