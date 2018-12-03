package com.xyj.oa.finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.finance.entity.BudgetModel;
import com.xyj.oa.finance.repository.BudgetModelDao;
import com.xyj.oa.system.entity.FinanceSubject;
import com.xyj.oa.system.repository.FinanceSubjectDao;
import com.xyj.oa.util.StringHelper;

@Component
@Transactional
public class BudgetModelService {

	@Autowired
	private BudgetModelDao budgetModelDao;

	@Autowired
	private FinanceSubjectDao financeSubjectDao;

	public Long findBudgetModelCountWithParams(String params) {
		return budgetModelDao.getCountWithParams(params);
	}

	public List<BudgetModel> findBudgetModelListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		List<BudgetModel> listWithParams = budgetModelDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
		List<Long> ids = null;
		for (BudgetModel budgetModel : listWithParams) {
			ids = new ArrayList<>();
			Set<FinanceSubject> financeSubjects = budgetModel.getFinanceSubjects();
			for (FinanceSubject financeSubject : financeSubjects) {
				ids.add(financeSubject.getId());
			}
			budgetModel.setSubIds(StringHelper.join(ids, ","));
		}
		return listWithParams;
	}

	public int saveBudgetModel(String name, String remark, String subIds, String id) {
		int result = 0;
		if (StringHelper.isEmpty(name) || StringHelper.isEmpty(subIds))
			return result;
		BudgetModel entity = new BudgetModel();
		if (StringHelper.isNotEmpty(id)) {
			entity = budgetModelDao.findById(Long.valueOf(id)).get();
			entity.getFinanceSubjects().clear();
		}
		entity.setName(name);
		entity.setRemark(remark);
		List<FinanceSubject> findByIds = financeSubjectDao.findByIds(subIds);
		entity.getFinanceSubjects().addAll(findByIds);
		budgetModelDao.save(entity);
		return 1;
	}

	public int deleteModel(Long id) {
		int result = 0;
		if (id == null || id <= 0)
			return result;
		BudgetModel entity = budgetModelDao.findById(id).orElse(null);
		if (entity == null)
			return result;
		//TODO:判断是否有使用该模板的预算编制,如果有,不能删除 返回-1
		budgetModelDao.delete(entity);
		return 1;
	}

	public List<Map<String, Object>> getAllSubject(Long id) {
		List<Map<String, Object>> result = null;
		if (id == null || id <= 0)
			return result;
		return budgetModelDao.findAllFinanceSubjectByModelId(id);
	}

}
