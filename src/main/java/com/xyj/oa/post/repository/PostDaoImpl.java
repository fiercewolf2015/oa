package com.xyj.oa.post.repository;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class PostDaoImpl implements PostDaoCustom {

	private static final String COMMON_SQL = " select distinct p from Post as p ";

	private static final String COMMON_COUNT_SQL = " select count(distinct p.id) from Post as p ";

	@Autowired
	private DepartMentDao departMentDao;

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by p.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by p.id desc ");
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
		List<Long> deptIds = new ArrayList<>();
		if (param.has("deptName") && StringHelper.isNotEmpty(param.getString("deptName"))) {
			String deptName = param.getString("deptName");
			deptIds = departMentDao.findDepartmentIdsByName(deptName);
			if (CollectionUtils.isNotEmpty(deptIds))
				hql.append(" inner join p.departments as dept where dept.id in(")
						.append(StringHelper.join(deptIds, ",")).append(")");
		}
		if (CollectionUtils.isEmpty(deptIds))
			hql.append(" where 1=1");
		if (param.has("postName") && StringHelper.isNotEmpty(param.getString("postName"))) {
			hql.append(" and p.postName like '%");
			hql.append(param.getString("postName"));
			hql.append("%' ");
		}
		if (param.has("postNo") && StringHelper.isNotEmpty(param.getString("postNo"))) {
			hql.append(" and p.postNo like '%");
			hql.append(param.getString("postNo"));
			hql.append("%' ");
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Post findByPostName(String postName) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where p.postName ='").append(postName).append("'");
		Query query = em.createQuery(hql.toString());
		List<Post> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Post findByPostNo(String postNo) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where p.postNo ='").append(postNo).append("'");
		Query query = em.createQuery(hql.toString());
		List<Post> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Post> findByPostIds(String postIds) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where p.id in(").append(postIds).append(")");
		Query query = em.createQuery(hql.toString());
		List<Post> resultList = query.getResultList();
		Set<Post> result = new HashSet<Post>(resultList);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAllPosts(Long departmentId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		if (departmentId != null)
			hql.append(" inner join p.departments as dept where dept.id = ").append(departmentId);
		else
			hql.append(" where 1=1");
		Query query = em.createQuery(hql.toString());
		List<Post> postList = query.getResultList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < postList.size(); i++) {
			Post post = postList.get(i);
			String name = post.getPostName();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", post.getId());
			map.put("text", name);
			list.add(map);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Post findByOldId(String oId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where p.oldId ='").append(oId).append("'");
		Query query = em.createQuery(hql.toString());
		List<Post> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

}
