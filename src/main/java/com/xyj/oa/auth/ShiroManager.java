package com.xyj.oa.auth;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import com.xyj.oa.auth.OaShiroRealm.ShiroUser;
import com.xyj.oa.role.entity.Role;
import com.xyj.oa.user.entity.User;

/**
 * shiro manager 获取当前登陆者的信息
 * 
 */
@Component
public class ShiroManager {

	/**
	 * 获取当前登陆者
	 * 
	 * @return
	 */
	public ShiroUser getCurrentUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}

	public void updateShiroUser(User user) {
		ShiroUser currentUser = getCurrentUser();
		currentUser.setId(user.getId());
		currentUser.setLoginName(user.getLoginName());
		currentUser.setName(user.getName());
		currentUser.setPhoto(user.getPhoto());
		Role role = user.getRole();
		Long roleId = null;
		String roleName = null;
		if (role != null) {
			roleId = role.getId();
			roleName = role.getName();
		}
		currentUser.setRoleId(roleId);
		currentUser.setRoleName(roleName);

	}

	/**
	 * 获取当前登陆者的id.
	 */
	public Long getCurrentUserId() {
		return getCurrentUser().id;
	}

	/**
	 * 获取当前登陆者的name.
	 */
	public String getCurrentUserName() {
		return getCurrentUser().name;
	}

	/**
	 * 获取当前登陆者的roleId.
	 */
	public long getCurrentRoleId() {
		return getCurrentUser().roleId;
	}

	/**
	 * 获取当前登陆者的roleName.
	 */
	public String getCurrentRoleName() {
		return getCurrentUser().roleName;
	}

}
