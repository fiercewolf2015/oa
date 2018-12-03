package com.xyj.oa.staff.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class StaffDaoImpl implements StaffDaoCustom {

	private static final String COMMON_SQL = " select distinct s from Staff as s ";

	private static final String COMMON_COUNT_SQL = " select count(distinct s.id) from Staff as s ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Staff> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by s.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by s.id desc ");
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
		if (param.has("searchStaffName") && StringHelper.isNotEmpty(param.getString("searchStaffName"))) {
			hql.append(" and s.staffName like '%");
			hql.append(param.getString("searchStaffName"));
			hql.append("%' ");
		}
		if (param.has("searchStaffNo") && StringHelper.isNotEmpty(param.getString("searchStaffNo"))) {
			hql.append(" and s.staffNo like '%");
			hql.append(param.getString("searchStaffNo"));
			hql.append("%' ");
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findCurMonthMaxStaffNo(String jobMonth) {
		if (StringHelper.isEmpty(jobMonth))
			return 0;
		jobMonth = jobMonth.substring(2, jobMonth.length());
		StringBuilder sql = new StringBuilder(" select max(staff_no) from t_staffs as s where s.staff_no like '%");
		sql.append(jobMonth).append("%'");
		Query query = em.createNativeQuery(sql.toString());
		List<String> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList) || StringHelper.isEmpty(resultList.get(0)))
			return Integer.valueOf(jobMonth + "001");
		else
			return Integer.valueOf(resultList.get(0)) + 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Staff findByNo(String no) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where  s.staffNo = '").append(no).append("'");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Staff> findStaffByIdsOrPostIdsOrOccupationIds(String staffIds, String postIds, String occupationIds, String ids) {
		if (StringHelper.isEmpty(staffIds) && StringHelper.isEmpty(postIds) && StringHelper.isEmpty(occupationIds))
			return null;
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		if (StringHelper.isNotEmpty(postIds))
			hql.append(" inner join s.posts as p ");
		hql.append(" where 1=1 ");
		if (StringHelper.isNotEmpty(staffIds))
			hql.append(" and s.id in(").append(staffIds).append(") ");
		if (StringHelper.isNotEmpty(postIds)) {
			if (StringHelper.isEmpty(staffIds))
				hql.append(" and ");
			else
				hql.append(" or ");
			hql.append(" p.id in(").append(postIds).append(") ");
		}
		if (StringHelper.isNotEmpty(occupationIds)) {
			if (StringHelper.isEmpty(staffIds) && StringHelper.isEmpty(postIds))
				hql.append(" and ");
			else
				hql.append(" or ");
			hql.append(" s.occupation.id in(").append(occupationIds).append(") ");
		}
		if (StringHelper.isNotEmpty(ids))
			hql.append(" and s.department.id  in (").append(ids).append(") ");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		Set<Staff> result = new HashSet<Staff>(resultList);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Staff findStaffByDeptAndPost(String deptName, String postName) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" inner join s.posts as p where s.department.departmentName = '").append(deptName).append("'");
		hql.append(" and p.postName = '").append(postName).append("' ");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Staff findStaffBydeptIdsAndPost(String deptIds, String postName) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" inner join s.posts as p where s.department.id in (").append(deptIds);
		hql.append(" ) and p.postName = '").append(postName).append("' ");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Staff> findStaffByPost(String postName) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" inner join s.posts as p where p.postName = '").append(postName).append("'");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return new HashSet<Staff>(resultList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Staff> findStaffByDept(String deptName, String postName) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" inner join s.posts as p where s.department.departmentName = '").append(deptName).append("'");
		hql.append(" and p.postName = '").append(postName).append("' ");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return new HashSet<Staff>(resultList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Staff findStaffByPostAndOcc(String postName, String occName) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" inner join s.posts as p where s.occupation.occupationName = '").append(occName).append("'");
		hql.append(" and p.postName = '").append(postName).append("' ");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Staff findByOldId(String oId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where s.oldId ='").append(oId).append("'");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Staff findByNameAndNo(String name, String staffNo) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where s.staffName ='").append(name).append("'");
		hql.append(" and s.staffNo ='").append(staffNo).append("'");
		Query query = em.createQuery(hql.toString());
		List<Staff> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}
}
