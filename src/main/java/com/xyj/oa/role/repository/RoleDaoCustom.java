package com.xyj.oa.role.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.role.entity.Role;

public interface RoleDaoCustom {

	List<Map<String, Object>> loadRoleTree();

	Role findByName(String name);

}
