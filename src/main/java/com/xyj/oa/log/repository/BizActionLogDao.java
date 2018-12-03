package com.xyj.oa.log.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.log.entity.BizActionLog;

public interface BizActionLogDao extends PagingAndSortingRepository<BizActionLog, Long>,BizActionLogDaoCustom{

}
