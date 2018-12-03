package com.xyj.oa.staff.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.occupation.entity.Occupation;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.title.entity.Title;
import com.xyj.oa.workflow.entity.WorkFlow;

@Entity
@Table(name = "t_staffs")
public class Staff extends IdEntity {

	private String oldId;// 金蝶系统中的员工id

	private String staffName;// 姓名

	private String staffNo;// 员工编号 规则:入职年月加三位数

	private String personalId;// 身份证号

	private Integer gender;// 性別 1:男 2:女

	private String nation;// 民族

	private String birthday;// 生日

	private String nativePlace;// 籍贯

	private Integer marriage;// 婚姻状况

	private String political;// 政治面目

	private String blood;// 血型

	private String health;// 健康状况

	private String partyDate;// 入党时间

	private String education;// 原学历

	private String major;// 原所学专业

	private String highestDegree;// 最高学历

	private String highestDegreeMajor;// 最高学历专业

	private String degree;// 学位

	private String forensicTime;// 取证时间

	private String certificateNumber;// 证件编号

	private String otherCertificate;// 其他证书名称

	private String otherForensicTime;// 其他取证时间

	private String otherCertificateNumber;// 其他证件编号

	private String jobTime;// 參加工作时间

	private String enterpriseTime;// 进入本企业时间

	private String homeAddress;// 家庭住址

	private String homePhoneNum;// 家庭电话

	private String accountAddress;// 户口所在地

	private String archivesAddress;// 档案所在地

	private String jobProject;// 工作所属项目

	private Occupation occupation;// 职务

	private Title title;// 职称职级

	private Department department;// 部门

	private String mobilePhone;

	private String fixedPhone;

	private String email;

	private Set<Post> posts = new HashSet<>();// 员工对应的岗位

	private String postNames;// 员工所对应的岗位名称,供前台显示,不持久化到数据库

	private String postIds;// 员工所对应的岗位id,供前台显示,不持久化到数据库

	private Set<WorkFlow> workFlow = new HashSet<>();

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public Integer getMarriage() {
		return marriage;
	}

	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	public String getHighestDegreeMajor() {
		return highestDegreeMajor;
	}

	public void setHighestDegreeMajor(String highestDegreeMajor) {
		this.highestDegreeMajor = highestDegreeMajor;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getForensicTime() {
		return forensicTime;
	}

	public void setForensicTime(String forensicTime) {
		this.forensicTime = forensicTime;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getOtherCertificate() {
		return otherCertificate;
	}

	public void setOtherCertificate(String otherCertificate) {
		this.otherCertificate = otherCertificate;
	}

	public String getOtherForensicTime() {
		return otherForensicTime;
	}

	public void setOtherForensicTime(String otherForensicTime) {
		this.otherForensicTime = otherForensicTime;
	}

	public String getOtherCertificateNumber() {
		return otherCertificateNumber;
	}

	public void setOtherCertificateNumber(String otherCertificateNumber) {
		this.otherCertificateNumber = otherCertificateNumber;
	}

	public String getJobTime() {
		return jobTime;
	}

	public void setJobTime(String jobTime) {
		this.jobTime = jobTime;
	}

	public String getEnterpriseTime() {
		return enterpriseTime;
	}

	public void setEnterpriseTime(String enterpriseTime) {
		this.enterpriseTime = enterpriseTime;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomePhoneNum() {
		return homePhoneNum;
	}

	public void setHomePhoneNum(String homePhoneNum) {
		this.homePhoneNum = homePhoneNum;
	}

	public String getAccountAddress() {
		return accountAddress;
	}

	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	public String getArchivesAddress() {
		return archivesAddress;
	}

	public void setArchivesAddress(String archivesAddress) {
		this.archivesAddress = archivesAddress;
	}

	public String getJobProject() {
		return jobProject;
	}

	public void setJobProject(String jobProject) {
		this.jobProject = jobProject;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "title_id")
	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "occupation_id")
	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public String getPartyDate() {
		return partyDate;
	}

	public void setPartyDate(String partyDate) {
		this.partyDate = partyDate;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_staff_posts", joinColumns = { @JoinColumn(name = "staff_id") }, inverseJoinColumns = {
			@JoinColumn(name = "post_id") })
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	@Transient
	public String getPostNames() {
		return postNames;
	}

	public void setPostNames(String postNames) {
		this.postNames = postNames;
	}

	@Transient
	public String getPostIds() {
		return postIds;
	}

	public void setPostIds(String postIds) {
		this.postIds = postIds;
	}

	public String getBlood() {
		return blood;
	}

	public String getHighestDegree() {
		return highestDegree;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_workflow_staffs", joinColumns = { @JoinColumn(name = "s_id") }, inverseJoinColumns = {
			@JoinColumn(name = "wf_id") })
	public Set<WorkFlow> getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(Set<WorkFlow> workFlow) {
		this.workFlow = workFlow;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFixedPhone() {
		return fixedPhone;
	}

	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

}