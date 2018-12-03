package com.xyj.oa.statistic.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.xyj.oa.statistic.entity.ContractWillForm;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

public class ContractWillFormDaoImpl implements ContractWillFormDaoCustom {

	private static final String COMMON_SQL = " select distinct cw from ContractWillForm as cw where 1=1 ";

	private static final String COMMON_COUNT_SQL = " select count(distinct cw.id) from ContractWillForm as cw where 1=1 ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractWillForm> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params));
		if (StringHelper.isNotEmpty(sidx) && StringHelper.isNotEmpty(sord))
			hql.append(" order by cw.").append(sidx).append(" ").append(sord);
		else
			hql.append(" order by cw.id desc ");
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
		if (param.has("instanceNum") && StringHelper.isNotEmpty(param.getString("instanceNum"))) {
			hql.append(" and cw.instanceNum like '%");
			hql.append(param.getString("instanceNum"));
			hql.append("%' ");
		}
		if (param.has("contractWillName") && StringHelper.isNotEmpty(param.getString("contractWillName"))) {
			hql.append(" and cw.contractName like '%");
			hql.append(param.getString("contractWillName"));
			hql.append("%' ");
		}
		if (param.has("contractWillNo") && StringHelper.isNotEmpty(param.getString("contractWillNo"))) {
			hql.append(" and cw.contractNo like '%");
			hql.append(param.getString("contractWillNo"));
			hql.append("%' ");
		}
		if (param.has("contractWillBeginDate") && StringHelper.isNotEmpty(param.getString("contractWillBeginDate"))) {
			hql.append(" and cw.contractBeginDate >= '");
			hql.append(param.getString("contractWillBeginDate"));
			hql.append("' ");
		}
		if (param.has("contractWillEndDate") && StringHelper.isNotEmpty(param.getString("contractWillEndDate"))) {
			hql.append(" and cw.contractEndDate <= '");
			hql.append(param.getString("contractWillEndDate"));
			hql.append("' ");
		}
		
		if (param.has("contractPriceTotal") && StringHelper.isNotEmpty(param.getString("contractPriceTotal"))) {
			hql.append(" and cw.contractPriceTotal = '");
			hql.append(param.getString("contractPriceTotal"));
			hql.append("' ");
		}
		if (param.has("contractTime") && StringHelper.isNotEmpty(param.getString("contractTime"))) {
			hql.append(" and cw.contractTime = '");
			hql.append(param.getString("contractTime"));
			hql.append("' ");
		}
		if (param.has("contractType") && StringHelper.isNotEmpty(param.getString("contractType"))) {
			hql.append(" and cw.contractType = '");
			hql.append(param.getString("contractType"));
			hql.append("' ");
		}
		if (param.has("paymentDate") && StringHelper.isNotEmpty(param.getString("paymentDate"))) {
			hql.append(" and cw.paymentDate = '");
			hql.append(param.getString("paymentDate"));
			hql.append("' ");
		}
		if (param.has("paymentMoney") && StringHelper.isNotEmpty(param.getString("paymentMoney"))) {
			hql.append(" and cw.paymentMoney = '");
			hql.append(param.getString("paymentMoney"));
			hql.append("' ");
		}
		if (param.has("paymentTimeSlot") && StringHelper.isNotEmpty(param.getString("paymentTimeSlot"))) {
			hql.append(" and cw.paymentTimeSlot = '");
			hql.append(param.getString("paymentTimeSlot"));
			hql.append("' ");
		}
		if (param.has("invoiceDate") && StringHelper.isNotEmpty(param.getString("invoiceDate"))) {
			hql.append(" and cw.invoiceDate = '");
			hql.append(param.getString("invoiceDate"));
			hql.append("' ");
		}
		if (param.has("flag") && StringHelper.isNotEmpty(param.getString("flag")))
			hql.append(" and cw.flag = ").append(Integer.valueOf(param.getString("flag")));
		return hql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ContractWillForm findByContractNo(String no) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" and cw.contractNo ='").append(no).append("'");
		Query query = em.createQuery(hql.toString());
		List<ContractWillForm> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractWillForm> findAllCwByParams(String params) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		JSONObject param = TfOaUtil.fromObject(params);
		if (param != null && param.has("contractWillBeginDate") && StringHelper.isNotEmpty(param.getString("contractWillBeginDate"))) {
			hql.append(" and cw.contractWillBeginDate >= '");
			hql.append(param.getString("contractWillBeginDate"));
			hql.append("' ");
		}
		if (param != null && param.has("contractWillEndDate") && StringHelper.isNotEmpty(param.getString("contractWillEndDate"))) {
			hql.append(" and cw.contractWillEndDate <= '");
			hql.append(param.getString("contractWillEndDate"));
			hql.append("' ");
		}
		Query query = em.createQuery(hql.toString());
		List<ContractWillForm> result = query.getResultList();
		if (CollectionUtils.isEmpty(result))
			return null;
		return result;
	}
}
