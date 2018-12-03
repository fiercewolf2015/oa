package com.xyj.oa.post.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.post.entity.Post;

public interface PostDao extends PagingAndSortingRepository<Post, Long>, JpaSpecificationExecutor<Post>, PostDaoCustom {

}
