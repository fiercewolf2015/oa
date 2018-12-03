package com.xyj.oa.role.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.role.entity.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long>, RoleDaoCustom {

}
