package com.xyj.oa.system.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.post.entity.Post;

@Entity
@Table(name = "t_financesubjects")
public class FinanceSubject extends IdEntity {

	private String name;

	private int level;

	private FinanceSubject parentFinanceSubject;

	private Set<FinanceSubject> childFinancSubjects = new HashSet<FinanceSubject>();

	private Integer type;// 1:收入 2:成本 3:利润

	private Set<Post> posts = new HashSet<>();// 科目对应的岗位

	private String remark;

	private Timestamp createTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public FinanceSubject getParentFinanceSubject() {
		return parentFinanceSubject;
	}

	public void setParentFinanceSubject(FinanceSubject parentFinanceSubject) {
		this.parentFinanceSubject = parentFinanceSubject;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "parentFinanceSubject")
	public Set<FinanceSubject> getChildFinancSubjects() {
		return childFinancSubjects;
	}

	public void setChildFinancSubjects(Set<FinanceSubject> childFinancSubjects) {
		this.childFinancSubjects = childFinancSubjects;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_subject_posts", joinColumns = { @JoinColumn(name = "su_id") }, inverseJoinColumns = {
			@JoinColumn(name = "post_id") })
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

}
