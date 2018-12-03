package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.NoticeStaff;

public interface NoticeStaffDao extends PagingAndSortingRepository<NoticeStaff, Long>,NoticeStaffDaoCustom{

}
