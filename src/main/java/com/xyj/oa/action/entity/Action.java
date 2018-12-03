package com.xyj.oa.action.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_actions")
public class Action extends IdEntity{
	
	private String name;

	private String codeName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
}
