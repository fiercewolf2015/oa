package com.xyj.oa.finance.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xyj.oa.auth.OaShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.finance.entity.FinanceApproval;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class FinanceApprovalDaoImpl implements FinanceApprovalDaoCustom {

	private static final String COMMON_SQL = " select distinct fa from FinanceApproval as fa left join fa.staffs as s where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct fa.id) from FinanceApproval as fa left join fa.staffs as s where 1=1 ";

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	UserDao userDao;

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceApproval> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord, int flag, Integer history) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params, flag, history));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by fa.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by fa.id desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long getCountWithParams(String params, int flag, Integer history) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		hql.append(makeWhere(params, flag, history));
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

	/**
	 * 
	 * @param params
	 * @param flag 0:预算编制 1:预算审批
	 * @return
	 */
	private StringBuilder makeWhere(String params, int flag, Integer history) {
		StringBuilder hql = new StringBuilder();
		JSONObject param = TfOaUtil.fromObject(params);
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).get();
		if (flag == 1)
			hql.append(" and (s.id in (").append(user.getStaff().getId()).append(") ").append(" or fa.financeApprovalStaff.id=")
					.append(user.getStaff().getId()).append(" )");
		else
			hql.append(" and ((fa.financeApplyStaff.id =").append(user.getStaff().getId())
					.append(" and fa.approvalFlag = 0 ) or (fa.financeApprovalStaff.id = ").append(user.getStaff().getId())
					.append(" and fa.approvalFlag = 1)) ");

		if (history == 0)
			hql.append(" and fa.flag = 0");
		else
			hql.append(" and fa.flag > 0");
		if (param == null)
			return hql;
		if (param.has("financeApprovalName") && StringHelper.isNotEmpty(param.getString("financeApprovalName"))) {
			hql.append(" and fa.financeApprovalName like '%");
			hql.append(param.getString("financeApprovalName"));
			hql.append("%' ");
		}

		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findYearsByDepartmentIds(String ids) {
		StringBuilder hql = new StringBuilder("select distinct fa.financeYear from FinanceApproval fa where fa.financeApplyDepartment.id in(");
		hql.append(ids).append(")  and fa.flag = 0 order by fa.financeYear desc");
		Query createQuery = em.createQuery(hql.toString());
		return createQuery.getResultList();
	}

	@Override
	public Integer findFinanceSubjectBalance(Long departMentId, Long subjectId) {
		StringBuilder hql = new StringBuilder(
				"select fo.subjectFinanceAll from FinanceApproval fa inner join fa.financeOrganizations as fo where fa.financeApplyDepartment.id = ")
				.append(departMentId);
		hql.append(" and fo.financeSubjects.id = ").append(subjectId);
		hql.append(" and fa.flag = 0");
		Query createQuery = em.createQuery(hql.toString());
		if (CollectionUtils.isEmpty(createQuery.getResultList()))
			return 0;
		return (Integer) createQuery.getResultList().get(0);
	}

	@Override
	public Integer findHistoryFinance(Long faId) {
		StringBuilder hql = new StringBuilder("select fa.flag from FinanceApproval as fa where 1=1");
		hql.append(" and fa.historyFinanceId = ").append(faId);
		hql.append(" order  by fa.flag desc");
		Query createQuery = em.createQuery(hql.toString());
		if (CollectionUtils.isEmpty(createQuery.getResultList()))
			return 0;
		return (Integer) createQuery.getResultList().get(0);
	}

}
