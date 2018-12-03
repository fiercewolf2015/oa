/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyj.oa.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.xyj.oa.api.exception.ApiException;
import com.xyj.oa.api.vo.APIErrorCode;
import com.xyj.oa.api.vo.ApiResult;
import com.xyj.oa.api.vo.ChildDeptVo;
import com.xyj.oa.api.vo.ParentDeptVo;
import com.xyj.oa.api.vo.SearchStaffVo;
import com.xyj.oa.api.vo.SearchStaffVoResult;
import com.xyj.oa.api.vo.StaffVo;
import com.xyj.oa.api.vo.StaffVoResult;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.role.entity.Role;
import com.xyj.oa.role.repository.RoleDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.staff.repository.StaffDao;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private StaffDao staffDao;

	@Autowired
	private DepartMentDao departMentDao;

	@Autowired
	private ShiroManager shiroManager;

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(Long id) {
		return userDao.findById(id).orElse(null);
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	public boolean checkUserPassword(String loginName, String pwd) {
		User user = userDao.findByLoginName(loginName);
		String plainPassword = user.getPlainPassword();
		return plainPassword.equals(pwd);
	}

	public int registerUser(String staffIds, long roleId) {
		Role role = roleDao.findById(roleId).orElse(null);
		if (role == null)
			return 0;
		String[] ids = staffIds.split(",");
		Date currentDate = new Date();
		for (String staffId : ids) {
			Staff staff = staffDao.findById(Long.parseLong(staffId)).get();
			String staffNo = staff.getStaffNo();
			if (StringHelper.isEmpty(staffNo))
				continue;
			User user = userDao.findByLoginName(staffNo);
			if (user != null)
				continue;
			user = new User();
			user.setLoginName(staffNo);
			user.setName(staff.getStaffName());
			user.setRole(role);
			user.setRegisterDate(currentDate);
			user.setStaff(staff);
			user.setPlainPassword("123456");
			userDao.save(user);
		}

		return 1;
	}

	public void updateUser(User user) {
		userDao.save(user);
	}

	public int deleteUser(String userIds) {
		if (StringHelper.isEmpty(userIds))
			return 0;
		String[] split = userIds.split(",");
		Long currentUserId = shiroManager.getCurrentUserId();
		for (String id : split) {
			Long userId = Long.parseLong(id);
			if (userId == null || userId <= 0)
				return 0;
			if (userId.intValue() == currentUserId.intValue())
				return 2;
			userDao.deleteById(userId);
		}
		return 1;
	}

	public int profile(String pic, String name, String password) {
		Long id = shiroManager.getCurrentUserId();
		User user = userDao.findById(id).orElse(null);
		if (user == null)
			return 0;
		if (StringHelper.isNotEmpty(password)) {
			user.setPlainPassword(password);
		}
		user.setName(name);
		if (StringHelper.isNotEmpty(pic))
			user.setPhoto(pic);
		userDao.save(user);
		shiroManager.updateShiroUser(user);
		return 1;
	}

	public long findCountWithParams(String params) {
		return userDao.getCountWithParams(params);
	}

	public List<User> findListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return userDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public int editUserRole(Long userId, Long roleId) {
		if (userId == null || userId <= 0 || roleId == null || roleId <= 0)
			return 0;
		User user = userDao.findById(userId).orElse(null);
		if (user == null)
			return 0;
		Role role = roleDao.findById(roleId).orElse(null);
		if (role == null)
			return 0;
		user.setRole(role);
		userDao.save(user);
		return 1;
	}

	public int editUserPassword(String userIds, String plainPassword) {
		if (StringHelper.isEmpty(userIds) || StringHelper.isEmpty(plainPassword))
			return 0;
		String[] split = userIds.split(",");
		for (String id : split) {
			User user = userDao.findById(Long.parseLong(id)).orElse(null);
			if (user == null)
				return 0;
			user.setPlainPassword(plainPassword);
			userDao.save(user);
		}
		return 1;
	}

	public Staff getStaffInfo(Long userId) {
		if (userId == null || userId <= 0)
			return null;
		User user = userDao.findById(userId).orElse(null);
		if (user == null)
			return null;
		Staff staff = user.getStaff();
		return staff;
	}

	public int editStaff(String params) {
		if (StringHelper.isEmpty(params))
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String staffId = jsonObject.getString("staffId");
		if (StringHelper.isEmpty(staffId))
			return 0;
		Staff staff = staffDao.findById(Long.valueOf(staffId)).orElse(null);
		if (staff == null)
			return 0;
		String jobTime = jsonObject.getString("jobTime");
		String gender = jsonObject.getString("gender");
		String nation = jsonObject.getString("nation");
		String birthday = jsonObject.getString("birthday");
		String nativePlace = jsonObject.getString("nativePlace");
		String blood = jsonObject.getString("blood");
		String personalId = jsonObject.getString("personalId");
		String marriage = jsonObject.getString("marriage");
		String political = jsonObject.getString("political");
		String health = jsonObject.getString("health");
		String partyDate = jsonObject.getString("partyDate");
		String homeAddress = jsonObject.getString("homeAddress");
		String homePhoneNum = jsonObject.getString("homePhoneNum");
		String accountAddress = jsonObject.getString("accountAddress");
		String archivesAddress = jsonObject.getString("archivesAddress");
		String education = jsonObject.getString("education");
		String major = jsonObject.getString("major");
		String highestDegree = jsonObject.getString("highestDegree");
		String highestDegreeMajor = jsonObject.getString("highestDegreeMajor");
		String degree = jsonObject.getString("degree");
		String forensicTime = jsonObject.getString("forensicTime");
		String certificateNumber = jsonObject.getString("certificateNumber");
		String otherCertificate = jsonObject.getString("otherCertificate");
		String otherForensicTime = jsonObject.getString("otherForensicTime");
		String otherCertificateNumber = jsonObject.getString("otherCertificateNumber");
		staff.setAccountAddress(accountAddress);
		staff.setArchivesAddress(archivesAddress);
		staff.setBirthday(birthday);
		staff.setBlood(blood);
		staff.setCertificateNumber(certificateNumber);
		staff.setDegree(degree);
		staff.setEducation(education);
		staff.setForensicTime(forensicTime);
		if (StringHelper.isNotEmpty(gender))
			staff.setGender(Integer.valueOf(gender));
		staff.setHealth(health);
		staff.setHighestDegree(highestDegree);
		staff.setHighestDegreeMajor(highestDegreeMajor);
		staff.setHomeAddress(homeAddress);
		staff.setHomePhoneNum(homePhoneNum);
		staff.setJobTime(jobTime);
		staff.setMajor(major);
		if (StringHelper.isNotEmpty(marriage))
			staff.setMarriage(Integer.valueOf(marriage));
		staff.setNation(nation);
		staff.setNativePlace(nativePlace);
		staff.setOtherCertificate(otherCertificate);
		staff.setOtherCertificateNumber(otherCertificateNumber);
		staff.setPolitical(political);
		staff.setPersonalId(personalId);
		staff.setPartyDate(partyDate);
		staff.setOtherForensicTime(otherForensicTime);
		staffDao.save(staff);
		return 1;
	}

	public User getUserForApi(String params) throws ApiException {
		if (StringHelper.isEmpty(params))
			throw new ApiException(APIErrorCode.LoginNameError);
		String json = new String(Base64Utils.decodeFromString(params));
		JSONObject object = JSONObject.fromObject(json);
		String username = object.getString("username");
		String password = object.getString("password");
		if (StringHelper.isEmpty(username) || StringHelper.isEmpty(password))
			throw new ApiException(APIErrorCode.LoginNameError);
		User user = findUserByLoginName(username);
		if (user == null)
			throw new ApiException(APIErrorCode.LoginNameError);
		String plainPassword = user.getPlainPassword();
		String md5 = StringHelper.getMd5(plainPassword);
		if (md5.equals(password))
			return user;
		throw new ApiException(APIErrorCode.PassWordError);
	}

	public ApiResult getUserListForApi(String params) throws ApiException {
		if (StringHelper.isEmpty(params))
			throw new ApiException(APIErrorCode.ParamError);
		String json = new String(Base64Utils.decodeFromString(params));
		JSONObject object = JSONObject.fromObject(json);
		String userId = object.getString("userId");
		String searchName = object.getString("searchName");
		User user = userDao.findById(Long.valueOf(userId)).orElse(null);
		if (user == null)
			throw new ApiException(APIErrorCode.UserNoExist);
		ApiResult result = null;
		if (StringHelper.isNotEmpty(searchName)) {
			List<Staff> list = userDao.findByStaffNameOrDepartmentName(searchName);
			if (CollectionUtils.isEmpty(list))
				result = new SearchStaffVoResult(null, 0);
			else {
				List<SearchStaffVo> staffVos = new ArrayList<SearchStaffVo>();
				for (int i = 0; i < list.size(); i++) {
					Staff staff = list.get(i);
					SearchStaffVo staffVo = new SearchStaffVo(staff);
					staffVos.add(staffVo);
				}
				result = new SearchStaffVoResult(staffVos, 2);
			}
		} else {
			List<Department> list = departMentDao.findAllSecondDepartment();
			if (CollectionUtils.isNotEmpty(list)) {
				List<ParentDeptVo> parentDeptVos = new ArrayList<>();
				for (Department department : list) {
					Set<Department> childrenDepartMent = department.getChildren();
					ChildDeptVo[] childDeptVos = new ChildDeptVo[childrenDepartMent.size()];
					int j = 0;
					for (Department cd : childrenDepartMent) {
						Set<Staff> staffs = cd.getStaffs();
						StaffVo[] staffVos = new StaffVo[staffs.size()];
						int i = 0;
						for (Staff staff : staffs) {
							StaffVo staffVo = new StaffVo(staff);
							staffVos[i] = staffVo;
							i++;
						}
						ChildDeptVo childDeptVo = new ChildDeptVo(cd, staffVos);
						childDeptVos[j] = childDeptVo;
						j++;
					}
					ParentDeptVo parentDeptVo = new ParentDeptVo(department, childDeptVos);
					parentDeptVos.add(parentDeptVo);
				}
				result = new StaffVoResult(parentDeptVos, 1);
			} else
				result = new SearchStaffVoResult(null, 0);
		}
		return result;
	}
}
