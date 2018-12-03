package com.xyj.oa.title.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.title.entity.Title;

public interface TitleDao extends PagingAndSortingRepository<Title, Long>, TitleDaoCustom {

}
