package com.xyj.oa.staff.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.staff.entity.Staff;

public interface StaffDao extends PagingAndSortingRepository<Staff, Long>, JpaSpecificationExecutor<Staff>,StaffDaoCustom{

}
