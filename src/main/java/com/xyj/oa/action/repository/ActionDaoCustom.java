package com.xyj.oa.action.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.xyj.oa.action.entity.Action;

public interface ActionDaoCustom {

	List<Map<String, Object>> loadPermissionTree();

	List<BigInteger> searchPermissionByRoleId(long roleId);

	Action findByCodeName(String codeName);

}
