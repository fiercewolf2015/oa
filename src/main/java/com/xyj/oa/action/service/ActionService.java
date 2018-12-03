package com.xyj.oa.action.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.xyj.oa.action.entity.Action;
import com.xyj.oa.action.repository.ActionDao;

@Component
@Transactional
public class ActionService {

	@Autowired
	private ActionDao actionDao;

	public List<Map<String, Object>> loadPermissionTree() {
		return actionDao.loadPermissionTree();
	}

	public List<BigInteger> searchPermissionByRoleId(long roleId) {
		return actionDao.searchPermissionByRoleId(roleId);
	}

	public int addPermission(String name, String codeName) {
		if (StringUtils.isEmpty(name))
			return 0;
		Action action = actionDao.findByCodeName(codeName);
		if (action != null)
			return 2;
		action = new Action();
		action.setName(name);
		action.setCodeName(codeName);
		actionDao.save(action);
		return 1;
	}

	public Action findById(long id) {
		Optional<Action> findById = actionDao.findById(id);
		if(findById.isPresent()) {
			return findById.get();
		}
		return null;
	}
}
