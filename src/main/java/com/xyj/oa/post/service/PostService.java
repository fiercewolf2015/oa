package com.xyj.oa.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.post.repository.PostDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

@Component
@Transactional
public class PostService {

	private static final String SEPRATOR = ",";

	@Autowired
	private PostDao postDao;

	@Autowired
	private DepartMentDao departMentDao;

	public long findCountWithParams(String params) {
		return postDao.getCountWithParams(params);
	}

	public List<Post> findListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		List<Post> listWithParams = postDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
		for (Post post : listWithParams) {
			Set<Department> departments = post.getDepartments();
			List<String> deptNames = new ArrayList<>();
			List<Long> deptIds = new ArrayList<>();
			for (Department department : departments) {
				if (department == null)
					continue;
				deptNames.add(department.getDepartmentName());
				deptIds.add(department.getId());
			}
			post.setDeptNames(StringHelper.join(deptNames, SEPRATOR));
			post.setDeptIds(StringHelper.join(deptIds, SEPRATOR));
		}
		return listWithParams;
	}

	public int savePost(String params, String selDeptIds) {
		if (StringHelper.isEmpty(params))
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String postName = jsonObject.getString("addPostName");
		String postNo = jsonObject.getString("addPostNo");
		Post findByPostName = postDao.findByPostName(postName);
		if (findByPostName != null)
			return 2;
		Post findByPostNo = postDao.findByPostNo(postNo);
		if (findByPostNo != null)
			return 3;
		Post post = new Post();
		post.setPostName(postName);
		post.setPostNo(postNo);
		post.setRemarks(jsonObject.getString("addRemarks"));
		post.setPostDescribe(jsonObject.getString("addPostDescribe"));
		if (StringHelper.isNotEmpty(selDeptIds)) {
			Set<Department> findByDeptIds = departMentDao.findByDeptIds(selDeptIds);
			post.setDepartments(findByDeptIds);
		}
		postDao.save(post);
		return 1;
	}

	public int editPost(String params, Long postId, String selDeptIds) {
		if (StringHelper.isEmpty(params) || postId == null)
			return 0;
		Optional<Post> pOptional = postDao.findById(postId);
		if (!pOptional.isPresent())
			return 0;
		Post findOne = pOptional.get();
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String postName = jsonObject.getString("editPostName");
		String postNo = jsonObject.getString("editPostNo");
		Post findByPostName = postDao.findByPostName(postName);
		if (findByPostName != null && findByPostName.getId().longValue() != postId.longValue())
			return 2;
		Post findByPostNo = postDao.findByPostNo(postNo);
		if (findByPostNo != null && findByPostNo.getId().longValue() != postId.longValue())
			return 3;
		findOne.setPostName(postName);
		findOne.setPostNo(postNo);
		findOne.setPostDescribe(jsonObject.getString("editPostDescribe"));
		findOne.setRemarks(jsonObject.getString("editRemarks"));
		findOne.getDepartments().clear();
		if (StringHelper.isNotEmpty(selDeptIds)) {
			Set<Department> findByDeptIds = departMentDao.findByDeptIds(selDeptIds);
			findOne.setDepartments(findByDeptIds);
		}
		return 1;
	}

	public String deletePosts(String postIds) {
		String result = "删除失败";
		String[] postIdArr = postIds.split(SEPRATOR);
		StringBuilder notDelWithStaff = new StringBuilder();
		StringBuilder notDelWithDept = new StringBuilder();
		int delCnt = 0;
		for (String postId : postIdArr) {
			Optional<Post> pOptional = postDao.findById(Long.valueOf(postId));
			if (!pOptional.isPresent())
				continue;
			Post post = pOptional.get();
			Set<Staff> staffs = post.getStaffs();
			Set<Department> departments = post.getDepartments();
			if (CollectionUtils.isNotEmpty(staffs) || CollectionUtils.isNotEmpty(departments)) {
				if (CollectionUtils.isNotEmpty(staffs))
					notDelWithStaff.append(post.getPostName()).append(SEPRATOR);
				if (CollectionUtils.isNotEmpty(departments))
					notDelWithDept.append(post.getPostName()).append(SEPRATOR);
				continue;
			}
			post.getStaffs().clear();
			post.getDepartments().clear();
			postDao.delete(post);
			delCnt++;
		}
		if (notDelWithStaff.toString().length() > 0 || notDelWithDept.toString().length() > 0) {
			result = "";
			if (notDelWithStaff.toString().length() > 0)
				result += notDelWithStaff.toString().substring(0, notDelWithStaff.toString().length() - 1)
						+ "已绑定员工，不能删除;";
			if (notDelWithDept.toString().length() > 0)
				result += notDelWithDept.toString().substring(0, notDelWithDept.toString().length() - 1)
						+ "已绑定部门，不能删除;";
			if (delCnt > 0)
				result += "其余岗位删除成功";
		} else
			result = "删除成功";
		return result;
	}

	public List<Map<String, Object>> getAllPosts(Long departmentId) {
		return postDao.findAllPosts(departmentId);
	}

}
