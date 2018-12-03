package com.xyj.oa.department.repository;

import org.springframework.data.repository.CrudRepository;

import com.xyj.oa.department.entity.Department;

public interface DepartMentDao extends CrudRepository<Department, Long>, DepartMentDaoCustom {

}
