package com.xyj.oa.department.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.post.repository.PostDao;
import com.xyj.oa.staff.entity.Staff;

import net.sf.json.JSONObject;

@Component
@Transactional()
public class DepartMentService {

	@Autowired
	private DepartMentDao departMentDao;

	@Autowired
	private PostDao postDao;

	public List<Map<String, Object>> loadDepartmentTree() {
		return departMentDao.loadDepartmentTree();
	}

	public List<Map<String, Object>> loadSecondDepartmentTree() {
		return departMentDao.loadSecondDepartmentTree();
	}

	public List<Map<String, Object>> loadThreeDepartmentTree(Long pId) {
		return departMentDao.loadThreeDepartmentTree(pId);
	}

	public int addDepartment(String params, Long pId) {
		if (StringHelper.isEmpty(params) || pId <= 0)
			return 0;
		JSONObject jsonObject = JSONObject.fromObject(params);
		String departmentName = jsonObject.getString("departmentName");
		String departmentNo = jsonObject.getString("departmentNo");
		String departmentDescribe = jsonObject.getString("departmentDescribe");
		String remarks = jsonObject.getString("remarks");
		String post_ids = jsonObject.getString("post_ids");
		if (StringHelper.isEmpty(departmentName) || StringHelper.isEmpty(departmentNo))
			return 0;
		Integer level = 1;
		Department parent = null;
		if (pId != null) {
			Optional<Department> optional = departMentDao.findById(pId);
			if(optional.isPresent())
				parent = optional.get();
		}
		List<Department> list = findByNameOrNo(departmentName, departmentNo);
		if (CollectionUtils.isNotEmpty(list))
			return 2;
		Department department = new Department();
		department.setDepartmentDescribe(departmentDescribe);
		department.setDepartmentName(departmentName);
		department.setDepartmentNo(departmentNo);
		department.setRemarks(remarks);
		if (StringHelper.isNotEmpty(post_ids)) {
			Set<Post> posts = postDao.findByPostIds(post_ids);
			Set<Post> dp = department.getPosts();
			dp.clear();
			dp.addAll(posts);
		}
		if (parent != null) {
			level = parent.getDepartmentLevel() + 1;
			department.setParentDepartment(parent);
		}
		department.setDepartmentLevel(level);
		departMentDao.save(department);

		return 1;
	}

	public List<Department> findByNameOrNo(String name, String no) {
		return departMentDao.findByNameOrNo(name, no);
	}

	public List<Department> findByNameOrNoWithId(String name, String no, Long id) {
		return departMentDao.findByNameOrNoWithId(name, no, id);
	}

	public List<Department> findByParentId(Long pId) {
		return departMentDao.findByParentId(pId);
	}

	public int delDepartment(Long id) {
		if (id == null || id <= 0)
			return 0;
		Optional<Department> optional = departMentDao.findById(id);
		if(!optional.isPresent())
			return 0;
		Department parent = optional.get();
		List<Department> list = new ArrayList<Department>();
		iterationDepartmentForChild(parent, list);
		for (Department department : list) {
			Set<Staff> staffs = department.getStaffs();
			Set<Post> posts = department.getPosts();
			if (CollectionUtils.isNotEmpty(staffs))
				return 2;
			if (CollectionUtils.isNotEmpty(posts))
				return 3;
		}
		for (Department department : list) {
			departMentDao.delete(department);
		}
		return 1;
	}

	public void iterationDepartmentForChild(Department department, List<Department> list) {
		if (!list.contains(department))
			list.add(department);
		Set<Department> children = department.getChildren();
		for (Department child : children)
			iterationDepartmentForChild(child, list);
	}

	public void iterationDepartmentIdForChild(Department department, List<Long> list) {
		if (!list.contains(department.getId()))
			list.add(department.getId());
		Set<Department> children = department.getChildren();
		for (Department child : children)
			iterationDepartmentIdForChild(child, list);
	}

	public int editDepartment(String params, Long id) {
		if (StringHelper.isEmpty(params) || id == null || id <= 0)
			return 0;
		JSONObject jsonObject = JSONObject.fromObject(params);
		String departmentName = jsonObject.getString("departmentName");
		String departmentNo = jsonObject.getString("departmentNo");
		String departmentDescribe = jsonObject.getString("departmentDescribe");
		String remarks = jsonObject.getString("remarks");
		String post_ids = jsonObject.getString("post_ids");
		if (StringHelper.isEmpty(departmentName) || StringHelper.isEmpty(departmentNo))
			return 0;
		Optional<Department> optional = departMentDao.findById(id);
		if(!optional.isPresent())
			return 0;
		Department department = optional.get();
		List<Department> list = findByNameOrNoWithId(departmentName, departmentNo, id);
		if (CollectionUtils.isNotEmpty(list))
			return 2;

		department.setDepartmentDescribe(departmentDescribe);
		department.setDepartmentName(departmentName);
		department.setDepartmentNo(departmentNo);
		department.setRemarks(remarks);
		Set<Post> dp = department.getPosts();
		dp.clear();
		if (StringHelper.isNotEmpty(post_ids)) {
			Set<Post> posts = postDao.findByPostIds(post_ids);
			dp.addAll(posts);
		}
		departMentDao.save(department);

		return 1;
	}

	public String getDepartmentNameById(Long dId) {
		if (dId == null || dId <= 0)
			return "";
		Optional<Department> optional = departMentDao.findById(dId);
		if(!optional.isPresent())
			return "";
		return optional.get().getDepartmentName();
	}

}
