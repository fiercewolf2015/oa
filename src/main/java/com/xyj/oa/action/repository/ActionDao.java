package com.xyj.oa.action.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.action.entity.Action;

public interface ActionDao extends PagingAndSortingRepository<Action, Long>, ActionDaoCustom {

}
