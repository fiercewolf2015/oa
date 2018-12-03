package com.xyj.oa.finance.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.xyj.oa.finance.entity.FinanceOrganization;

public class FinanceOrganizationDaoImpl implements FinanceOrganizationDaoCustom {

	private static final String COMMON_SQL = " select distinct fo from FinanceOrganization as fo ";

	private static final String COMMON_COUNT_SQL = " select count(distinct fo.id) from FinanceOrganization as fo where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceOrganization> findFinaceOrGanizationP(Long appId, String subjectIds) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append("where fo.financeApproval.id =").append(appId);
		if (subjectIds != null)
			hql.append(" and fo.financeSubjects.id in ( ").append(subjectIds).append(") ");
		hql.append("order by fo.id");
		Query query = em.createQuery(hql.toString());
		return (List<FinanceOrganization>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceOrganization> findFinaceOrGanizationChild(Long pId, Long appId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append("where fo.parentFinanceOrganization.id =").append(pId);
		if (appId != null)
			hql.append(" and fo.financeApproval.id = ").append(appId);
		hql.append(" order by fo.id");
		Query query = em.createQuery(hql.toString());
		return (List<FinanceOrganization>) query.getResultList();
	}

	/**
	 * level=2 时,表示那第一层会计科目,ids是部门id;level>2时,ids是父会计科目id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceOrganization> findDifferentLevelByDepartmentIdAndYear(String Ids, String year, int level) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		if (level == 2)
			hql.append(" where fo.financeApproval.financeApplyDepartment.id in(").append(Ids).append(")");
		else
			hql.append(" where fo.parentFinanceOrganization.id =").append(Ids);
		hql.append(" and fo.financeApproval.financeYear ='").append(year).append("'");
		hql.append(" and fo.financeSubjects.level = ").append(level);
		Query createQuery = em.createQuery(hql.toString());
		return (List<FinanceOrganization>) createQuery.getResultList();
	}

}
