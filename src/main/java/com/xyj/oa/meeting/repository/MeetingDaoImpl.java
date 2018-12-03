package com.xyj.oa.meeting.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.xyj.oa.meeting.entity.Meeting;
import com.xyj.oa.util.DateUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class MeetingDaoImpl implements MeetingDaoCustom {

	private static final String COMMON_SQL = " select distinct m from Meeting as m inner join m.staffs as ms where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct m.id) from Meeting as m  inner join m.staffs as ms where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Meeting> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord, Long staffId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params, staffId));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by m.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by m.id desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long getCountWithParams(String params, Long staffId) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		hql.append(makeWhere(params, staffId));
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

	private StringBuilder makeWhere(String params, Long staffId) {
		StringBuilder hql = new StringBuilder();
		JSONObject param = TfOaUtil.fromObject(params);
		if (param == null)
			return hql;
		if (param.has("searchType") && StringHelper.isNotEmpty(param.getString("searchType")) && param.getString("searchType").equals("1"))
			hql.append(" and m.meetingFlag = 1 ");
		if (param.has("searchType") && StringHelper.isNotEmpty(param.getString("searchType")) && param.getString("searchType").equals("0"))
			hql.append(" and m.meetingFlag = 0 ");
		if (param.has("searchType") && StringHelper.isNotEmpty(param.getString("searchType")) && param.getString("searchType").equals("2"))
			hql.append(" and m.meetingFlag = 0 ").append(" and ms.id in (").append(staffId).append(") ");
		if (param.has("applyStaffName") && StringHelper.isNotEmpty(param.getString("applyStaffName"))) {
			hql.append(" and m.applyStaff.staffName like '%");
			hql.append(param.getString("applyStaffName"));
			hql.append("%' ");
		}
		if (param.has("meetingTitle") && StringHelper.isNotEmpty(param.getString("meetingTitle")))
			hql.append(" and m.meetingTitle like '%").append(param.getString("meetingTitle")).append("%'");
		if (param.has("applyDeptName") && StringHelper.isNotEmpty(param.getString("applyDeptName")))
			hql.append(" and m.applyDepartment.departmentName like '%").append(param.getString("applyDeptName")).append("%'");
		if (param.has("meetingType") && StringHelper.isNotEmpty(param.getString("meetingType")) && !param.getString("meetingType").equals("-1"))
			hql.append(" and m.meetingType = ").append(param.getString("meetingType"));
		if (param.has("meetingBeginTime") && StringHelper.isNotEmpty(param.getString("meetingBeginTime")))
			hql.append(" and m.meetingBeginTime >= '").append(param.getString("meetingBeginTime")).append("' ");
		if (param.has("meetingEndTime") && StringHelper.isNotEmpty(param.getString("meetingEndTime")))
			hql.append(" and m.meetingEndTime <= '").append(param.getString("meetingEndTime")).append("' ");
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meeting> findAllMeetingByIds(String meetingIds) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and m.id in (").append(meetingIds).append(") ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meeting> findAllEndMeeting() {
		String now = DateUtil.formatTimestampToStringByFmt(DateUtil.getNowTimestamp(), DateUtil.NORMAL_TIME_FORMAT);
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and m.meetingEndTime < '").append(now).append("' ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}

}
