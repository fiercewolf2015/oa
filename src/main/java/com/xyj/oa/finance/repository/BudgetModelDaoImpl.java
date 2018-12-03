package com.xyj.oa.finance.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.xyj.oa.finance.entity.BudgetModel;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class BudgetModelDaoImpl implements BudgetModelDaoCustom {

	private static final String COMMON_SQL = " select distinct bm from BudgetModel as bm where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct bm.id) from BudgetModel as bm where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetModel> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by bm.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by bm.id desc ");
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
		if (param.has("modelName") && StringHelper.isNotEmpty(param.getString("modelName"))) {
			hql.append(" and bm.name like '%");
			hql.append(param.getString("modelName"));
			hql.append("%' ");
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BudgetModel findByName(String name) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and bm.name= '").append(name).append("'");
		Query query = em.createQuery(hql.toString());
		List<BudgetModel> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAllFinanceSubjectByModelId(Long modelId) {
		StringBuilder sql = new StringBuilder("select f.name as name, f.id, f.parent_id as pId , f.level, f.remark ");
		sql.append(" from t_budgetmodel_subject as bs left join t_financesubjects as f on bs.sub_id = f.id where bs.model_id=").append(modelId);
		Query query = em.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return new ArrayList<Map<String, Object>>();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BudgetModel findBySubId(Long subId) {
		StringBuilder hql = new StringBuilder(" select distinct bm from BudgetModel as bm inner join bm.financeSubjects as fs where  ");
		hql.append(" fs.id = ").append(subId);
		Query query = em.createQuery(hql.toString());
		List<BudgetModel> resultList = query.getResultList();
		if (CollectionUtils.isEmpty(resultList))
			return null;
		return resultList.get(0);
	}

}
