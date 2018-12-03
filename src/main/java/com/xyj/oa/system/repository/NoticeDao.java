package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.Notice;

public interface NoticeDao extends PagingAndSortingRepository<Notice, Long>,NoticeDaoCustom{

}
