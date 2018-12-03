package com.xyj.oa.system.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.system.entity.FinanceSubject;

public interface FinanceSubjectDaoCustom {

	List<Map<String, Object>> loadFinanceSubjectTree();

	List<Map<String, Object>> loadLevelFinanceSubjectTree(Long pId, int level);

	FinanceSubject findByName(String name);

	List<FinanceSubject> findByIds(String ids);

	List<FinanceSubject> findByPostIds(String postIds);

	List<FinanceSubject> findByParentId(Long pid);

}
