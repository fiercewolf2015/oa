package com.xyj.oa.post.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xyj.oa.post.entity.Post;

public interface PostDaoCustom {
	List<Post> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	Post findByPostName(String postName);

	Post findByPostNo(String postNo);

	Set<Post> findByPostIds(String postIds);

	List<Map<String, Object>> findAllPosts(Long departmentId);

	Post findByOldId(String oId);
}
