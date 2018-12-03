package com.xyj.oa.role.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.action.entity.Action;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.user.entity.User;

@Entity
@Table(name = "t_roles")
public class Role extends IdEntity {

	private String name;// 角色名

	private String description;// 角色描述

	private Set<Action> actions = new HashSet<>();// 角色对应权限

	private Set<User> users = new HashSet<User>();

	public String getName() {
		return name;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "role")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_role_actions", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "action_id") })
	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}
}
