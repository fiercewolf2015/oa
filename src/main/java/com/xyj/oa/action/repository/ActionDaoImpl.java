package com.xyj.oa.action.repository;

import java.math.BigInteger;
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

import com.xyj.oa.action.entity.Action;

public class ActionDaoImpl implements ActionDaoCustom{
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadPermissionTree() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select name, id, 'parent' as pId from t_actions ");
		Query query = em.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return new ArrayList<Map<String, Object>>();
		Map<String, Object> parent = new HashMap<String, Object>();
		parent.put("name", "选择全部");
		parent.put("id", "parent");
		parent.put("pId", null);
		resultList.add(0, parent);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigInteger> searchPermissionByRoleId(long roleId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select action_id from t_role_actions where role_id =").append(roleId);
		Query query = em.createNativeQuery(sql.toString());
		List<BigInteger> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Action findByCodeName(String codeName) {
		StringBuilder sql = new StringBuilder(" select a from Action as a ");
		sql.append(" where a.codeName ='").append(codeName).append("' ");
		Query query = em.createQuery(sql.toString());
		List<Action> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}
}
