package com.xyj.oa.system.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.system.entity.Notice;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class NoticeDaoImpl implements NoticeDaoCustom {

	private static final String COMMON_SQL = " select distinct n from Notice as n  inner join n.noticeStaffs as ns where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct n.id) from Notice as n  inner join n.noticeStaffs as ns where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Notice> getListWithParams(String params, Long stafffId, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params, stafffId));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by n.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by n.id desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long getCountWithParams(String params, Long stafffId) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		hql.append(makeWhere(params, stafffId));
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

	private StringBuilder makeWhere(String params, Long staffId) {
		StringBuilder hql = new StringBuilder();
		JSONObject param = TfOaUtil.fromObject(params);
		if (staffId != null)
			hql.append(" and n.promotStaff.id = ").append(staffId);
		if (param == null)
			return hql;
		if (param.has("noticeName") && StringHelper.isNotEmpty(param.getString("noticeName"))) {
			hql.append(" and n.title like '%");
			hql.append(param.getString("noticeName"));
			hql.append("%' ");
		}
		if (param.has("staffId") && StringHelper.isNotEmpty(param.getString("staffId")))
			hql.append(" and ns.staff.id = ").append(param.getString("staffId"));
		if (param.has("unRead") && StringHelper.isNotEmpty(param.getString("unRead")))
			hql.append(" and ns.unread = ").append(param.getString("unRead"));
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Notice findByName(String title) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and n.title ='").append(title).append("'");
		Query query = em.createQuery(hql.toString());
		List<Notice> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		else
			return result.get(0);
	}

	@Override
	public Long getUnreadNum(Long staffId) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		hql.append(" and ns.staff.id = ").append(staffId);
		hql.append(" and ns.unread = ").append(0);
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

}
