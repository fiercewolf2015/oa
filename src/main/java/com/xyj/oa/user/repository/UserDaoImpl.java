package com.xyj.oa.user.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class UserDaoImpl implements UserDaoCustom {

	private static final String COMMON_SQL = " select distinct u from User as u ";

	private static final String COMMON_COUNT_SQL = " select count(distinct u.id) from User as u ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by u.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by u.id desc ");
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
		hql.append(" where 1=1 ");
		JSONObject param = TfOaUtil.fromObject(params);
		if (param == null)
			return hql;
		if (param.has("searchLoginName") && StringHelper.isNotEmpty(param.getString("searchLoginName"))) {
			hql.append(" and u.loginName like '%");
			hql.append(param.getString("searchLoginName"));
			hql.append("%' ");
		}
		if (param.has("searchName") && StringHelper.isNotEmpty(param.getString("searchName"))) {
			hql.append(" and u.name like '%");
			hql.append(param.getString("searchName"));
			hql.append("%' ");
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByStaffId(Long staffId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" left join u.staff as s where s.id =").append(staffId);
		Query query = em.createQuery(hql.toString());
		List<User> resultList = query.getResultList();
		if (CollectionUtils.isNotEmpty(resultList))
			return resultList.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Staff> findByStaffNameOrDepartmentName(String searchName) {
		StringBuilder hql = new StringBuilder(" select distinct u.staff from User as u ");
		if (StringHelper.isNotEmpty(searchName))
			hql.append(" where (u.staff.staffName like '%").append(searchName)
					.append("%') or u.staff.department.departmentName like '%").append(searchName).append("%' ");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isNotEmpty(resultList))
			return resultList;
		return null;
	}
}
