package com.xyj.oa.finance.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.auth.ShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.department.service.DepartMentService;
import com.xyj.oa.finance.entity.FinanceApproval;
import com.xyj.oa.finance.entity.FinanceOrganization;
import com.xyj.oa.finance.repository.FinanceApprovalDao;
import com.xyj.oa.finance.repository.FinanceOrganizationDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.staff.repository.StaffDao;
import com.xyj.oa.system.entity.FinanceSubject;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

/**
 * 预算审批报表service
 * @author kevin
 *
 */
@Component
@Transactional
public class FinanceApprovalService {

	private final static String ONEPOSTNAME = "项目经理";

	private final static String TWOPOSTNAME = "收入专员";

	private final static String THREEPOSTNAME = "工程专员";

	private final static String FOURPOSTNAME = "物业专员";

	private final static String FIVEPOSTNAME = "财务专员";

	private final static String SIXPOSTNAME = "采购专员";

	private final static String SEVENPOSTNAME = "车管专员";

	private final static String EIGHTPOSTNAME = "分管项目副总";

	private final static String NINEPOSTNAME = "分公司经理";

	private final static String TENPOSTNAME = "人力资源部";

	private final static String ELEVENPOSTNAME = "计划财务部";

	private final static String TWELVEPOSTNAME = "副总经理";

	private final static String THIRTEENPOSTNAME = "总经理";

	private final static String FOURTEENPOSTNAME = "分公司财务经理";

	private final static String POINT = ",";

	@Autowired
	private FinanceApprovalDao financeApprovalDao;

	@Autowired
	private FinanceOrganizationDao financeOrganizationDao;

	@Autowired
	private DepartMentService departMentService;

	@Autowired
	private StaffDao staffDao;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private DepartMentDao departMentDao;

	public Long findBudgetApprovalCountWithParams(String params) {
		return financeApprovalDao.getCountWithParams(params, 1, 0);
	}

	public List<FinanceApproval> findBudgetApprovalListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return financeApprovalDao.getListWithParams(params, pageNo, pageSize, sidx, sord, 1, 0);
	}

	//申请预算开始
	public int saveApprovalFinance(Long faId) {
		if (faId == null)
			return 0;
		FinanceApproval financeApproval = financeApprovalDao.findById(faId).orElse(null);
		if (financeApproval == null)
			return 0;
		List<Long> allChild = new ArrayList<>();
		if (financeApproval.getApprovalFlag() == 6 || financeApproval.getApprovalFlag() == 7 || financeApproval.getApprovalFlag() == 8
				|| financeApproval.getApprovalFlag() == 9) {
			if (financeApproval.getApprovalFlag() == 6) {
				Department department = departMentDao.findByNameOrNo("人力资源部", null).get(0);
				allChild.add(department.getId());
			} else if (financeApproval.getApprovalFlag() == 7) {
				Department department = departMentDao.findByNameOrNo("计划财务部", null).get(0);
				allChild.add(department.getId());
			} else if (financeApproval.getApprovalFlag() == 8 || financeApproval.getApprovalFlag() == 9) {
				Department department = departMentDao.findByNameOrNo("公司领导", null).get(0);
				allChild.add(department.getId());
			}
		} else {
			Department department = financeApproval.getFinanceApplyDepartment();
			//		Department parentDepartment = department.getParentDepartment();
			//		departMentService.iterationDepartmentIdForChild(parentDepartment, allChild);
			//		if (financeApproval.getApprovalFlag() > 5) {
			//			Department parentDepartment2 = parentDepartment.getParentDepartment();
			//			departMentService.iterationDepartmentIdForChild(parentDepartment2, allChild);
			//		}
			allChild.add(department.getId());
			if (financeApproval.getApprovalFlag() == 2 || financeApproval.getApprovalFlag() == 3 || financeApproval.getApprovalFlag() == 4
					|| financeApproval.getApprovalFlag() == 5) {
				Department parentDepartment = department.getParentDepartment();
				allChild.add(parentDepartment.getId());
			}
		}
		Staff staff = null;
		Set<Staff> secondStaffs = null;
		StringBuilder staffIds = new StringBuilder();
		//		Staff applyStaff = null;
		int approvalFlag = 0;
		Staff secondStaff = null;
		if (financeApproval.getApprovalFlag() == 2) {
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			Long userd = shiroUser.getId();
			User user = userDao.findById(userd).get();
			secondStaff = user.getStaff();
			approvalFlag = 2;
		}
		if (financeApproval.getApprovalFlag() == 0) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), ONEPOSTNAME);
			approvalFlag = 1;
			//			applyStaff = staff;
		} else if (financeApproval.getApprovalFlag() == 1) {
			Staff twostaff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), TWOPOSTNAME);
			Staff threestaff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), THREEPOSTNAME);
			Staff fourstaff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), FOURPOSTNAME);
			Staff fivestaff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), FIVEPOSTNAME);
			Staff sixstaff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), SIXPOSTNAME);
			Staff sevenstaff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), SEVENPOSTNAME);
			if (twostaff == null || threestaff == null || fourstaff == null || fivestaff == null || sixstaff == null || sevenstaff == null)
				return -1;
			secondStaffs = new HashSet<>();
			secondStaffs.add(twostaff);
			secondStaffs.add(threestaff);
			secondStaffs.add(fourstaff);
			secondStaffs.add(fivestaff);
			secondStaffs.add(sixstaff);
			secondStaffs.add(sevenstaff);
			staffIds.append(twostaff.getId()).append(POINT).append(threestaff.getId()).append(POINT).append(fourstaff.getId()).append(POINT)
					.append(fivestaff.getId()).append(POINT).append(sixstaff.getId()).append(POINT).append(sevenstaff.getId());
			approvalFlag = 2;
		} else if (financeApproval.getApprovalFlag() == 2 && CollectionUtils.isEmpty(financeApproval.getStaffs())) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), EIGHTPOSTNAME);
			approvalFlag = 3;
		} else if (financeApproval.getApprovalFlag() == 3) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), NINEPOSTNAME);
			approvalFlag = 4;
		} else if (financeApproval.getApprovalFlag() == 4) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), FOURTEENPOSTNAME);
			approvalFlag = 5;
		} else if (financeApproval.getApprovalFlag() == 5) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), NINEPOSTNAME);
			approvalFlag = 6;
		} else if (financeApproval.getApprovalFlag() == 6) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), TENPOSTNAME);
			processFinanceHistory(financeApproval);
			approvalFlag = 7;
		} else if (financeApproval.getApprovalFlag() == 7) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), ELEVENPOSTNAME);
			approvalFlag = 8;
		} else if (financeApproval.getApprovalFlag() == 8) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), TWELVEPOSTNAME);
			approvalFlag = 9;
		} else if (financeApproval.getApprovalFlag() == 9) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), THIRTEENPOSTNAME);
			approvalFlag = 100;
		}
		if (staff == null && StringHelper.isEmpty(staffIds.toString()) && approvalFlag != 2 && approvalFlag != 100)
			return -1;
		if (CollectionUtils.isNotEmpty(secondStaffs))
			financeApproval.getStaffs().addAll(secondStaffs);
		else if (secondStaff != null)
			financeApproval.getStaffs().remove(secondStaff);
		if (CollectionUtils.isEmpty(financeApproval.getStaffs()) && approvalFlag == 2) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), EIGHTPOSTNAME);
			approvalFlag = 3;
		}
		financeApproval.setFinanceApprovalStaff(staff);
		financeApproval.setFinanceApprovalStaffIds(staffIds.toString());
		if (staff != null)
			financeApproval.setFinanceApprovalDepartment(staff.getDepartment());
		financeApproval.setApprovalFlag(approvalFlag);
		financeApprovalDao.save(financeApproval);
		return 1;
	}

	//驳回预算申请
	public int saveApprovalFinance(Long faId, String rejectReason) {
		if (faId == null)
			return 0;
		FinanceApproval financeApproval = financeApprovalDao.findById(faId).orElse(null);
		if (financeApproval == null)
			return 0;
		Department department = financeApproval.getFinanceApplyDepartment();
		List<Long> allChild = new ArrayList<>();
		allChild.add(department.getId());
		Staff staff = null;
		if (financeApproval.getApprovalFlag() == 9 || financeApproval.getApprovalFlag() == 100) {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), NINEPOSTNAME);
			financeApproval.setApprovalFlag(6);
		} else {
			staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), ONEPOSTNAME);
			financeApproval.setApprovalFlag(1);
		}
		financeApproval.setFinanceApprovalStaff(staff);
		financeApproval.setFinanceApprovalDepartment(staff.getDepartment());
		financeApproval.getStaffs().clear();
		financeApproval.setRejectReason(rejectReason);
		financeApprovalDao.save(financeApproval);
		return 1;
	}

	private void processFinanceHistory(FinanceApproval approval) {
		Integer flag = financeApprovalDao.findHistoryFinance(approval.getId());
		FinanceApproval newHisApproval = new FinanceApproval();
		newHisApproval.setAllApril(approval.getAllApril());
		newHisApproval.setAllAugust(approval.getAllAugust());
		newHisApproval.setAllDecember(approval.getAllDecember());
		newHisApproval.setAllFebruary(approval.getAllFebruary());
		newHisApproval.setAllJanuary(approval.getAllJanuary());
		newHisApproval.setAllJuly(approval.getAllJuly());
		newHisApproval.setAllJune(approval.getAllJune());
		newHisApproval.setAllMarch(approval.getAllMarch());
		newHisApproval.setAllMay(approval.getAllMay());
		newHisApproval.setAllNovember(approval.getAllNovember());
		newHisApproval.setAllOctober(approval.getAllOctober());
		newHisApproval.setAllSeptember(approval.getAllSeptember());
		newHisApproval.setApprovalFlag(0);
		newHisApproval.setCreateTime(TfOaUtil.getCurTime());
		Department department = approval.getFinanceApplyDepartment();
		Department parentDepartment = department.getParentDepartment();
		List<Long> allChild = new ArrayList<>();
		departMentService.iterationDepartmentIdForChild(parentDepartment, allChild);
		Staff staff = staffDao.findStaffBydeptIdsAndPost(StringHelper.join(allChild, ","), ONEPOSTNAME);
		newHisApproval.setFinanceApprovalName(approval.getFinanceApprovalName());
		newHisApproval.setFinanceApplyStaff(staff);
		newHisApproval.setFinanceApplyDepartment(staff.getDepartment());
		newHisApproval.setFinanceApprovalStaff(staff);
		newHisApproval.setFinanceApprovalDepartment(staff.getDepartment());
		newHisApproval.setFinanceApprovalStaffIds(approval.getFinanceApprovalStaffIds());
		if (CollectionUtils.isNotEmpty(approval.getStaffs()))
			newHisApproval.getStaffs().addAll(approval.getStaffs());
		newHisApproval.setFinanceYear(approval.getFinanceYear());
		newHisApproval.setFlag(flag + 1);
		newHisApproval.setHistoryFinanceId(approval.getId());
		newHisApproval.setRejectReason(approval.getRejectReason());
		Set<FinanceOrganization> hfoSet = new HashSet<>();
		financeApprovalDao.save(newHisApproval);
		Map<Long, FinanceOrganization> parentMap = new HashMap<>();
		List<FinanceOrganization> findFinaceOrGanizationP = financeOrganizationDao.findFinaceOrGanizationP(approval.getId(), null);
		for (FinanceOrganization financeOrganization : findFinaceOrGanizationP) {
			FinanceOrganization hfo = new FinanceOrganization();
			hfo.setFinanceApproval(newHisApproval);
			hfo.setFinanceSubjects(financeOrganization.getFinanceSubjects());
			hfo.setIfChild(financeOrganization.getIfChild());
			if (financeOrganization.getParentFinanceOrganization() == null) {
				FinanceSubject financeSubjects = financeOrganization.getFinanceSubjects();
				parentMap.put(financeSubjects.getId(), hfo);
				hfo.setParentFinanceOrganization(null);
			} else {
				FinanceOrganization parentFinanceOrganization = financeOrganization.getParentFinanceOrganization();
				Long id = parentFinanceOrganization.getFinanceSubjects().getId();
				FinanceOrganization financeOrganization2 = parentMap.get(id);
				hfo.setParentFinanceOrganization(financeOrganization2);
			}
			hfo.setSubjectFinanceAll(financeOrganization.getSubjectFinanceAll());
			hfo.setSubjectFinanceApril(financeOrganization.getSubjectFinanceApril());
			hfo.setSubjectFinanceAugust(financeOrganization.getSubjectFinanceAugust());
			hfo.setSubjectFinanceDecember(financeOrganization.getSubjectFinanceDecember());
			hfo.setSubjectFinanceFebruary(financeOrganization.getSubjectFinanceFebruary());
			hfo.setSubjectFinanceJanuary(financeOrganization.getSubjectFinanceJanuary());
			hfo.setSubjectFinanceJuly(financeOrganization.getSubjectFinanceJuly());
			hfo.setSubjectFinanceJune(financeOrganization.getSubjectFinanceJune());
			hfo.setSubjectFinanceMarch(financeOrganization.getSubjectFinanceMarch());
			hfo.setSubjectFinanceMay(financeOrganization.getSubjectFinanceMay());
			hfo.setSubjectFinanceNovember(financeOrganization.getSubjectFinanceNovember());
			hfo.setSubjectFinanceOctober(financeOrganization.getSubjectFinanceOctober());
			hfo.setSubjectFinanceSeptember(financeOrganization.getSubjectFinanceSeptember());
			hfo.setFinanceApproval(newHisApproval);
			financeOrganizationDao.save(hfo);

			hfoSet.add(hfo);
		}
		newHisApproval.getFinanceOrganizations().addAll(hfoSet);
	}

	/**
	 * 获取分公司每年预算综合
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getListForSimple(String params) {
		Map<String, Object> result = TfOaUtil.createPageInfoMap(1, 20, 0, null);
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return result;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).orElse(null);
		if (user == null)
			return result;
		Staff staff = user.getStaff();
		if (staff == null)
			return result;
		Department department = staff.getDepartment();
		if (department == null)
			return result;
		List<Long> departmentIdsList = new ArrayList<>();//所有的子部门id
		departMentService.iterationDepartmentIdForChild(department, departmentIdsList);
		String departmentIdsStr = StringHelper.join(departmentIdsList, ",");
		List<String> years = financeApprovalDao.findYearsByDepartmentIds(departmentIdsStr);
		if (CollectionUtils.isEmpty(years))
			return result;
		List<Map<String, Object>> temList = new ArrayList<>();
		Map<String, Object> oneYearMap = null;
		int i = 1;
		DecimalFormat decimalFormat = new DecimalFormat(".0000");
		for (String year : years) {
			oneYearMap = new HashMap<String, Object>();
			List<FinanceOrganization> allOrganizationsOneYear = financeOrganizationDao.findDifferentLevelByDepartmentIdAndYear(departmentIdsStr, year, 2);
			oneYearMap.put("name", department.getDepartmentName());
			oneYearMap.put("id", i);
			oneYearMap.put("departmentId", department.getId());
			i++;
			oneYearMap.put("year", year);
			Long january = 0L, february = 0L, march = 0L, april = 0L, may = 0L, june = 0L, july = 0L, august = 0L, september = 0L, october = 0L, november = 0L,
					december = 0L, all = 0L;
			for (FinanceOrganization fo : allOrganizationsOneYear) {
				january += fo.getSubjectFinanceJanuary();
				february += fo.getSubjectFinanceFebruary();
				march += fo.getSubjectFinanceMarch();
				april += fo.getSubjectFinanceApril();
				may += fo.getSubjectFinanceMay();
				june += fo.getSubjectFinanceJune();
				july += fo.getSubjectFinanceJuly();
				august += fo.getSubjectFinanceAugust();
				september += fo.getSubjectFinanceSeptember();
				october += fo.getSubjectFinanceOctober();
				november += fo.getSubjectFinanceNovember();
				december += fo.getSubjectFinanceDecember();
				all += fo.getSubjectFinanceAll();
			}
			oneYearMap.put("january", january / 10000.0);
			oneYearMap.put("february", february / 10000.0);
			oneYearMap.put("march", march / 10000.0);
			oneYearMap.put("april", april / 10000.0);
			oneYearMap.put("may", may / 10000.0);
			oneYearMap.put("june", june / 10000.0);
			oneYearMap.put("july", july / 10000.0);
			oneYearMap.put("august", august / 10000.0);
			oneYearMap.put("september", september / 10000.0);
			oneYearMap.put("october", october / 10000.0);
			oneYearMap.put("november", november / 10000.0);
			oneYearMap.put("december", december / 10000.0);
			oneYearMap.put("all", all / 10000.0);
			temList.add(oneYearMap);
		}
		return TfOaUtil.createPageInfoMap(1, 1000, years.size(), temList);
	}

	/**
	 * 获取分公司某一年所有项目预算
	 * @param parentDepartmentId
	 * @param year
	 * @return
	 */
	public Map<String, Object> getAllDepartment(Long parentDepartmentId, String year) {
		Map<String, Object> result = new HashMap<>();
		if (parentDepartmentId == null || parentDepartmentId <= 0)
			return result;
		Department department = departMentDao.findById(parentDepartmentId).orElse(null);
		if (department == null)
			return result;
		List<Long> departmentIdsList = new ArrayList<>();//所有的子部门id
		departMentService.iterationDepartmentIdForChild(department, departmentIdsList);
		departmentIdsList.remove(parentDepartmentId);
		if (CollectionUtils.isEmpty(departmentIdsList))
			return result;
		List<Map<String, Object>> temList = new ArrayList<>();
		Map<String, Object> oneYearMap = null;
		int total = departmentIdsList.size();
		for (Long departmentId : departmentIdsList) {
			Department findOne = departMentDao.findById(departmentId).orElse(null);
			if (findOne == null) {
				total--;
				continue;
			}
			oneYearMap = new HashMap<String, Object>();
			List<FinanceOrganization> allOrganizationsOneYear = financeOrganizationDao.findDifferentLevelByDepartmentIdAndYear(departmentId.toString(), year,
					2);
			if (CollectionUtils.isEmpty(allOrganizationsOneYear)) {
				total--;
				continue;
			}
			oneYearMap.put("name", findOne.getDepartmentName());
			oneYearMap.put("id", findOne.getId());
			oneYearMap.put("year", year);
			Long january = 0L, february = 0L, march = 0L, april = 0L, may = 0L, june = 0L, july = 0L, august = 0L, september = 0L, october = 0L, november = 0L,
					december = 0L, all = 0L;
			for (FinanceOrganization fo : allOrganizationsOneYear) {
				january += fo.getSubjectFinanceJanuary();
				february += fo.getSubjectFinanceFebruary();
				march += fo.getSubjectFinanceMarch();
				april += fo.getSubjectFinanceApril();
				may += fo.getSubjectFinanceMay();
				june += fo.getSubjectFinanceJune();
				july += fo.getSubjectFinanceJuly();
				august += fo.getSubjectFinanceAugust();
				september += fo.getSubjectFinanceSeptember();
				october += fo.getSubjectFinanceOctober();
				november += fo.getSubjectFinanceNovember();
				december += fo.getSubjectFinanceDecember();
				all += fo.getSubjectFinanceAll();
			}
			oneYearMap.put("january", january);
			oneYearMap.put("february", february);
			oneYearMap.put("march", march);
			oneYearMap.put("april", april);
			oneYearMap.put("may", may);
			oneYearMap.put("june", june);
			oneYearMap.put("july", july);
			oneYearMap.put("august", august);
			oneYearMap.put("september", september);
			oneYearMap.put("october", october);
			oneYearMap.put("november", november);
			oneYearMap.put("december", december);
			oneYearMap.put("all", all);
			temList.add(oneYearMap);
		}
		return TfOaUtil.createPageInfoMap(1, 1000, total, temList);
	}

	public Map<String, Object> getDifferentLevel(Long id, String year, int level) {
		Map<String, Object> result = new HashMap<>();
		if (id == null || id <= 0)
			return result;
		List<FinanceOrganization> allOrganizationsOneYear = financeOrganizationDao.findDifferentLevelByDepartmentIdAndYear(id.toString(), year, level);
		if (CollectionUtils.isEmpty(allOrganizationsOneYear))
			return result;
		Map<String, Object> oneYearMap = null;
		List<Map<String, Object>> temList = new ArrayList<>();
		for (FinanceOrganization fo : allOrganizationsOneYear) {
			oneYearMap = new HashMap<>();
			oneYearMap.put("name", fo.getFinanceSubjects().getName() + "(" + (level - 1) + ")");
			oneYearMap.put("id", fo.getId());
			oneYearMap.put("year", year);
			oneYearMap.put("january", fo.getSubjectFinanceJanuary());
			oneYearMap.put("february", fo.getSubjectFinanceFebruary());
			oneYearMap.put("march", fo.getSubjectFinanceMarch());
			oneYearMap.put("april", fo.getSubjectFinanceApril());
			oneYearMap.put("may", fo.getSubjectFinanceMay());
			oneYearMap.put("june", fo.getSubjectFinanceJune());
			oneYearMap.put("july", fo.getSubjectFinanceJuly());
			oneYearMap.put("august", fo.getSubjectFinanceAugust());
			oneYearMap.put("september", fo.getSubjectFinanceSeptember());
			oneYearMap.put("october", fo.getSubjectFinanceOctober());
			oneYearMap.put("november", fo.getSubjectFinanceNovember());
			oneYearMap.put("december", fo.getSubjectFinanceDecember());
			oneYearMap.put("all", fo.getSubjectFinanceAll());
			oneYearMap.put("ifChild", fo.getIfChild());
			temList.add(oneYearMap);
		}
		return TfOaUtil.createPageInfoMap(1, 1000, allOrganizationsOneYear.size(), temList);
	}

}
