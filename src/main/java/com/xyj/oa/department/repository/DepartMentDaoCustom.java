package com.xyj.oa.department.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xyj.oa.department.entity.Department;

public interface DepartMentDaoCustom {

	List<Map<String, Object>> loadDepartmentTree();

	List<Department> findByNameOrNo(String name, String no);

	List<Department> findByParentId(Long pId);

	List<Department> findByNameOrNoWithId(String name, String no, Long id);

	List<Long> findDepartmentIdsByName(String deptName);

	Set<Department> findByDeptIds(String deptIds);

	List<Department> findAllSecondDepartment();

	List<Map<String, Object>> loadSecondDepartmentTree();

	List<Map<String, Object>> loadThreeDepartmentTree(Long pId);

	Department findByOldId(String oId);

}
