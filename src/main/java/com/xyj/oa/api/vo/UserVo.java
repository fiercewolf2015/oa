package com.xyj.oa.api.vo;

import com.xyj.oa.user.entity.User;

public class UserVo extends ApiResult {

	private long userId;

	private String name;

	public UserVo(User user) {
		if (user == null)
			return;
		this.result = 1;
		this.userId = user.getId();
		this.name = user.getName();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
