package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.LeaveType;

public interface LeaveTypeDao extends PagingAndSortingRepository<LeaveType, Long>, LeaveTypeDaoCustom {

}
