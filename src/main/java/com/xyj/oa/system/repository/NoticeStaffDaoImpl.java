package com.xyj.oa.system.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.xyj.oa.system.entity.NoticeStaff;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class NoticeStaffDaoImpl implements NoticeStaffDaoCustom {

	private static final String COMMON_SQL = " select distinct n from NoticeStaff as n  where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct n.id) from NoticeStaff as n  where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<NoticeStaff> getListWithParams(String params, Long stafffId, int pageNo, int pageSize, String sidx, String sord) {
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
		hql.append(" and n.staff.id = ").append(staffId);
		if (param == null)
			return hql;
		if (param.has("noticeName") && StringHelper.isNotEmpty(param.getString("noticeName"))) {
			hql.append(" and n.notice.title like '%");
			hql.append(param.getString("noticeName"));
			hql.append("%' ");
		}
		if (param.has("unRead") && StringHelper.isNotEmpty(param.getString("unRead")))
			hql.append(" and n.unread = ").append(param.getString("unRead"));
		return hql;
	}

}
