package com.xyj.oa.role.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.action.entity.Action;
import com.xyj.oa.action.repository.ActionDao;
import com.xyj.oa.role.entity.Role;
import com.xyj.oa.role.repository.RoleDao;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.util.StringHelper;

@Component
@Transactional
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ActionDao actionDao;

	public Role findOne(Long roleId) {
		return roleDao.findById(roleId).orElse(null);
	}

	public List<Map<String, Object>> loadRoleTree() {
		return roleDao.loadRoleTree();
	}

	public String getRoleNameById(long roleId) {
		Optional<Role> role = roleDao.findById(roleId);
		if (role.isPresent())
			return role.get().getName();
		return null;
	}

	public int addPermissionToRole(Long roleId, String permissionIds) {
		if (roleId == null || roleId == 0)
			return 0;
		Optional<Role> rOptional = roleDao.findById(roleId);
		if (!rOptional.isPresent())
			return 0;
		Action action = null;
		Role role = rOptional.get();
		Set<Action> actions = role.getActions();
		actions.clear();
		if (StringHelper.isEmpty(permissionIds))
			role.setActions(actions);
		else {
			String[] ids = permissionIds.split(",");
			for (String id : ids) {
				if (id.equals("parent"))
					continue;
				action = actionDao.findById(Long.parseLong(id)).get();
				actions.add(action);
			}
			role.setActions(actions);
		}
		roleDao.save(role);
		return 1;
	}

	public int addRole(Long roleId, String roleName, String roleDescription) {
		if (StringHelper.isEmpty(roleName))
			return 0;
		Role role = null;
		Role oldRole = roleDao.findByName(roleName);
		if (roleId == null && oldRole != null)
			return 2;
		else {
			role = roleDao.findById(roleId).get();
			if (oldRole != null && !role.getId().equals(oldRole.getId()))
				return 2;
		}
		role.setName(roleName);
		role.setDescription(roleDescription);
		roleDao.save(role);
		return 1;
	}

	public int deleteRole(Long roleId) {
		if (roleId == null || roleId <= 0)
			return 0;
		Optional<Role> findById = roleDao.findById(roleId);
		if (!findById.isPresent())
			return 0;
		Set<User> users = findById.get().getUsers();
		if (CollectionUtils.isNotEmpty(users))
			return 2;
		roleDao.deleteById(roleId);
		return 1;
	}
	
}
