package com.xyj.oa.department.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.xyj.oa.department.entity.Department;

public class DepartMentDaoImpl implements DepartMentDaoCustom {

	private static final String COMMON_SQL = "select distinct d from Department as d ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadDepartmentTree() {
		StringBuilder sql = new StringBuilder(
				"select d.department_name as name, d.id, d.parent_id as pId , d.department_no, d.department_describe, d.department_level, d.remarks, GROUP_CONCAT(distinct p.post_name) as post_name, GROUP_CONCAT(distinct p.id) as post_ids");
		sql.append(" from t_departments as d left join t_post_departments as pd on d.id=pd.department_id ");
		sql.append(" left join t_posts as p on p.id=pd.post_id group by d.id");
		Query query = em.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return new ArrayList<Map<String, Object>>();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadSecondDepartmentTree() {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where d.departmentLevel = 2");
		Query query = em.createQuery(hql.toString());
		List<Department> departmentList = query.getResultList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < departmentList.size(); i++) {
			Department department = departmentList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", department.getId());
			map.put("text", department.getDepartmentName());
			list.add(map);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadThreeDepartmentTree(Long pid) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where d.departmentLevel = 3 and d.parentDepartment.id = ").append(pid);
		Query query = em.createQuery(hql.toString());
		List<Department> departmentList = query.getResultList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < departmentList.size(); i++) {
			Department department = departmentList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", department.getId());
			map.put("text", department.getDepartmentName());
			list.add(map);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findByNameOrNo(String name, String no) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append("where d.departmentName = '").append(name).append("' ");
		hql.append(" or d.departmentNo ='").append(no).append("' ");
		Query query = em.createQuery(hql.toString());
		List<Department> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findByParentId(Long pId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where  d.parentDepartment.id=").append(pId);
		Query query = em.createQuery(hql.toString());
		List<Department> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findByNameOrNoWithId(String name, String no, Long id) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where (d.departmentName = '").append(name).append("' ");
		hql.append(" or d.departmentNo ='").append(no).append("') and d.id <> ").append(id);
		Query query = em.createQuery(hql.toString());
		List<Department> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findDepartmentIdsByName(String deptName) {
		StringBuilder hql = new StringBuilder("select distinct d.id from Department as d");
		hql.append(" where d.departmentName like '%").append(deptName).append("%' ");
		Query query = em.createQuery(hql.toString());
		List<Long> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Department> findByDeptIds(String deptIds) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where  d.id in(").append(deptIds).append(")");
		Query query = em.createQuery(hql.toString());
		List<Department> resultList = query.getResultList();
		Set<Department> result = new HashSet<Department>(resultList);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findAllSecondDepartment() {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where d.departmentLevel = 2 ");
		Query query = em.createQuery(hql.toString());
		List<Department> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Department findByOldId(String oId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where d.oldId ='").append(oId).append("'");
		Query query = em.createQuery(hql.toString());
		List<Department> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}
}
