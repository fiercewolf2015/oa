package com.xyj.oa.log.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.log.constants.LogConstants;

@Entity
@Table(name = "t_bizaction_logs")
public class BizActionLog extends IdEntity{

	private String actionType = LogConstants.TEMPSTR;	//	操作类型
	
	private String bizType;								//	业务类型
	
	private String bizContent;							//	业务信息
	
	private String bizInfo;								//	业务元信息
	
	private Long creatorId;								//	操作者ID: user.id
	
	private String creatorName;							//	操作者名称: user.name
	
	private String createTime;						//	创建时间

	public String getActionType() {
		return actionType;
	}

	public String getBizType() {
		return bizType;
	}

	public String getBizContent() {
		return bizContent;
	}

	@JsonIgnore
	public Long getCreatorId() {
		return creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getBizInfo() {
		return bizInfo;
	}

	public void setBizInfo(String bizInfo) {
		this.bizInfo = bizInfo;
	}

}
