package com.xyj.oa.workflow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.auth.ShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.post.repository.PostDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.staff.repository.StaffDao;
import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.user.service.AccountService;
import com.xyj.oa.util.DateUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;
import com.xyj.oa.webservice.GetStaffPostChangeWebService;
import com.xyj.oa.workflow.entity.BusinessData;
import com.xyj.oa.workflow.entity.WorkFlow;
import com.xyj.oa.workflow.entity.WorkFlowInstance;
import com.xyj.oa.workflow.entity.WorkFlowPointInfo;
import com.xyj.oa.workflow.entity.WorkFlowRule;
import com.xyj.oa.workflow.entity.WorkFlowType;
import com.xyj.oa.workflow.repository.BusinessDataDao;
import com.xyj.oa.workflow.repository.WorkFlowDao;
import com.xyj.oa.workflow.repository.WorkFlowInstanceDao;
import com.xyj.oa.workflow.repository.WorkFlowPointInfoDao;
import com.xyj.oa.workflow.repository.WorkFlowRuleDao;
import com.xyj.oa.workflow.repository.WorkFlowTypeDao;

import net.sf.json.JSONObject;

@Component
@Transactional
public class WorkFlowService {

	private static final String POINT = "#####";

	private static final String POSTNAME = "部门经理";

	public static final Map<Integer, String> POSTMAP = new HashMap<>();

	public static final Map<Integer, String> POSTMAP1 = new HashMap<>();

	public static final String FILEPOSTNAME = "综合部经理";

	public static final String FILEPOSTNAME1 = "收发文经办";

	public static final String FILEPOSTNAME2 = "本部各部门经理";

	public static final String FILEPOSTNAME3 = "公司领导";

	public static final String FILEPOSTNAME4 = "各分总";

	public static final String ENDINSTANCE = "流程结束";

	public static final String OCCNAME = "副总经理";

	@Autowired
	private WorkFlowDao workFlowDao;

	@Autowired
	private WorkFlowRuleDao workFlowRuleDao;

	@Autowired
	private WorkFlowTypeDao workFlowTypeDao;

	@Autowired
	private WorkFlowPointInfoDao workFlowPointInfoDao;

	@Autowired
	private WorkFlowInstanceDao workFlowInstanceDao;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BusinessDataDao businessDataDao;

	@Autowired
	private StaffDao staffDao;

	@Autowired
	private DepartMentDao departMentDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private SystemService systemService;

	@Autowired
	private GetStaffPostChangeWebService getStaffPostChangeWebService;

	@Autowired
	private AccountService accountService;

	static {
		POSTMAP.put(1, FILEPOSTNAME);
		POSTMAP.put(2, FILEPOSTNAME1);
		POSTMAP.put(5, FILEPOSTNAME1);

		POSTMAP1.put(1, FILEPOSTNAME);
		POSTMAP1.put(2, FILEPOSTNAME1);
		POSTMAP1.put(9, FILEPOSTNAME1);
	}

	//启动流程
	public int startInstance(String params, Long workflowId, Long subStaffId, String fileName, String filePath, Long managerStaffId, String shrFormId,
			Staff applyStaff) {
		if (StringHelper.isEmpty(params) || workflowId == null)
			return 0;
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null && applyStaff == null)
			return 0;
		Staff staff = null;
		if (applyStaff != null) {
			staff = applyStaff;
		} else {
			Long userd = shiroUser.getId();
			Optional<User> uOptional = userDao.findById(userd);
			if(!uOptional.isPresent())
				return 0;
			User user = uOptional.get();
			staff = user.getStaff();
		}
		if (staff == null)
			return 0;
		WorkFlow workFlow = workFlowDao.findById(workflowId).orElse(null);
		if (workFlow == null)
			return 0;
		Staff managerStaff = staffDao.findById(managerStaffId).orElse(null);
		if (managerStaff == null)
			return 0;
		WorkFlowRule workFlowRule = workFlowRuleDao.findfirstRuleByWFId(workFlow.getId());
		WorkFlowType workFlowType = workFlow.getWorkFlowType();
		if (workFlowType == null)
			return 0;
		Long workFlowTypeId = workFlowType.getId();
		BusinessData data = new BusinessData();
		data.setDataInfo(params);
		data.setCreateTime(TfOaUtil.getCurTime());
		data.setWorkFlowType(workFlowType);
		businessDataDao.save(data);
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String instanceNo = returnFormNum(workFlowTypeId);
		WorkFlowInstance workInstance = new WorkFlowInstance();
		workInstance.setWorkFlow(workFlow);
		workInstance.setInstanceNum(instanceNo);
		workInstance.setApplyUser(staff.getStaffName());
		workInstance.setApplyUserDeptName(staff.getDepartment().getDepartmentName());
		workInstance.setApplyUserNo(staff.getStaffNo());
		workInstance.setAttachmentName(StringHelper.isEmpty(fileName) ? null : fileName.substring(2, fileName.length()));
		workInstance.setAttachmentPath(filePath);
		workInstance.setBusinessData(data);
		workInstance.setIfreject(0);
		workInstance.setIsComplete(0);
		workInstance.setPointAssignee(managerStaff.getStaffName());
		workInstance.setManagerStaffId(managerStaff.getId());
		workInstance.setPointCreateTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workInstance.setPointName(workFlowRule.getPointName());
		workInstance.setPointNum(workFlowRule.getOrderNumber());
		workInstance.setStaffId(staff.getId());
		workInstance.setSubStaffId(subStaffId);
		workInstance.setPointReason("");
		String reason = "";
		String company = "";
		String deptName = "";
		if (jsonObject.has("company")) {
			String cid = jsonObject.getString("company");
			if (!StringHelper.isEmpty(cid) && isNumeric(cid)) {
				Department department = departMentDao.findById(Long.valueOf(cid)).orElse(null);
				if (department != null)
					company = department.getDepartmentName();
			} else
				company = cid;
		}
		if (jsonObject.has("deptName")) {
			String did = jsonObject.getString("deptName");
			if (!StringHelper.isEmpty(did) && isNumeric(did)) {
				Department department = departMentDao.findById(Long.valueOf(did)).orElse(null);
				if (department != null)
					deptName = department.getDepartmentName();
			} else
				deptName = did;
		}
		if (jsonObject.has("reason"))
			reason = jsonObject.getString("reason");
		if (workFlowTypeId == 1) {
			String totalOverTime = jsonObject.getString("totalTime");
			String overTimeSectionFrom = jsonObject.getString("overtimeSectionFrom");
			String overTimeSectionTo = jsonObject.getString("overtimeSectionTo");
			workInstance.setReserve1(Float.parseFloat(totalOverTime));
			workInstance.setReserve2(overTimeSectionFrom.substring(0, 9));
			workInstance.setReserve3(overTimeSectionTo.substring(0, 9));
		}
		if (workFlowTypeId == 8) {
			String contractNo = jsonObject.getString("contractNo1") + "-" + jsonObject.getString("contractNo2") + "-" + jsonObject.getString("contractNo3")
					+ "-" + jsonObject.getString("contractNo4");
			workInstance.setReserve2(contractNo);
		}
		workInstance.setReserve20(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_DATE_FORMAT));
		workInstance.setPointReason(reason);
		workInstance.setReserve13(company);
		workInstance.setReserve14(deptName);
		if (shrFormId != null)
			workInstance.setReserve8(shrFormId);
		workFlowInstanceDao.save(workInstance);
		WorkFlowPointInfo pointInfo = new WorkFlowPointInfo();
		pointInfo.setApprovalState(-1);
		pointInfo.setPointName("申请人");
		pointInfo.setPointNum(0);
		pointInfo.setApprovalStaffId(staff.getId());
		pointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		pointInfo.setTaskReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		pointInfo.setWorkFlowInstance(workInstance);
		workFlowPointInfoDao.save(pointInfo);
		data.setWorkFlowInstanceId(workInstance.getId());
		businessDataDao.save(data);
		return 1;
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	//启动文件阅办流程
	public int startFileInstance(String params, Long workflowId, Long subStaffId, String fileName, String filePath, Long managerStaffId) {
		if (StringHelper.isEmpty(params) || workflowId == null)
			return 0;
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return 0;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).orElse(null);
		if (user == null)
			return 0;
		Staff staff = user.getStaff();
		if (staff == null)
			return 0;
		WorkFlow workFlow = workFlowDao.findById(workflowId).orElse(null);
		if (workFlow == null)
			return 0;
		Staff managerStaff = staffDao.findById(managerStaffId).orElse(null);
		if (managerStaff == null)
			return 0;
		WorkFlowType workFlowType = workFlow.getWorkFlowType();
		if (workFlowType == null)
			return 0;
		BusinessData data = new BusinessData();
		data.setDataInfo(params);
		data.setCreateTime(TfOaUtil.getCurTime());
		data.setWorkFlowType(workFlowType);
		businessDataDao.save(data);
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String instanceNo = returnFormNum(workFlowType.getId());
		WorkFlowInstance workInstance = new WorkFlowInstance();
		workInstance.setWorkFlow(workFlow);
		workInstance.setInstanceNum(instanceNo);
		workInstance.setApplyUser(staff.getStaffName());
		workInstance.setApplyUserDeptName(staff.getDepartment().getDepartmentName());
		workInstance.setApplyUserNo(staff.getStaffNo());
		workInstance.setAttachmentName(StringHelper.isEmpty(fileName) ? null : fileName.substring(2, fileName.length()));
		workInstance.setAttachmentPath(filePath);
		workInstance.setBusinessData(data);
		workInstance.setIfreject(0);
		workInstance.setIsComplete(0);
		workInstance.setPointAssignee(managerStaff.getStaffName());
		workInstance.setManagerStaffId(managerStaff.getId());
		workInstance.setPointCreateTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workInstance.setPointName(FILEPOSTNAME);
		workInstance.setPointNum(1);
		workInstance.setStaffId(staff.getId());
		workInstance.setSubStaffId(subStaffId);
		workInstance.setPointReason("");
		String reason = "";
		String company = "";
		String deptName = "";
		if (jsonObject.has("company")) {
			String cid = jsonObject.getString("company");
			Department department = departMentDao.findById(Long.valueOf(cid)).orElse(null);
			if (department != null)
				company = department.getDepartmentName();
		}
		if (jsonObject.has("deptName"))
			deptName = jsonObject.getString("deptName");
		if (jsonObject.has("reason"))
			reason = jsonObject.getString("reason");
		workInstance.setReserve20(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_DATE_FORMAT));
		workInstance.setPointReason(reason);
		workInstance.setReserve13(company);
		workInstance.setReserve14(deptName);
		workFlowInstanceDao.save(workInstance);
		WorkFlowPointInfo pointInfo = new WorkFlowPointInfo();
		pointInfo.setApprovalState(-1);
		pointInfo.setPointName("申请人");
		pointInfo.setPointNum(0);
		pointInfo.setApprovalStaffId(staff.getId());
		pointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		pointInfo.setTaskReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		pointInfo.setWorkFlowInstance(workInstance);
		workFlowPointInfoDao.save(pointInfo);
		data.setWorkFlowInstanceId(workInstance.getId());
		businessDataDao.save(data);
		return 1;
	}

	public List<Map<String, Object>> returnProcessRule(Long subStaffId, Long workflowId, Long departmentId) {
		WorkFlow workFlow = workFlowDao.findById(workflowId).orElse(null);
		if (workFlow == null)
			return null;
		Set<Staff> managerStaffs = null;
		if (workFlow.getWorkFlowType().getId() == 3) {
			managerStaffs = processFileProcessRule(workflowId);
			if (managerStaffs == null)
				managerStaffs = new HashSet<>();
		} else {
			WorkFlowRule workFlowRule = workFlowRuleDao.findfirstRuleByWFId(workFlow.getId());
			if (workFlowRule == null)
				return null;
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			if (shiroUser == null)
				return null;
			Long userd = shiroUser.getId();
			User user = userDao.findById(userd).orElse(null);
			if (user == null)
				return null;
			Staff staff = user.getStaff();
			if (staff == null)
				return null;
			Department department = null;
			if (departmentId == null || departmentId < 0)
				department = staff.getDepartment();
			else
				department = departMentDao.findById(departmentId).orElse(null);
			String staffIds = workFlowRule.getStaffIds();
			String occupationIds = workFlowRule.getOccupationIds();
			String postIds = workFlowRule.getPostIds();
			List<Long> deptIds = new ArrayList<>();
			if (StringHelper.isEmpty(staffIds)) {
				findSecondDeptChildIds(findSecondDepartMent(department, null), deptIds);
				managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, StringHelper.join(deptIds, ","));
			} else
				managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, null);
		}
		List<Map<String, Object>> returnList = new ArrayList<>();
		for (Staff s : managerStaffs) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("value", s.getId());
			maps.put("text", s.getStaffName());
			returnList.add(maps);
		}
		return returnList;
	}

	public List<Map<String, Object>> returnFinanceProcessRule(Long subStaffId, Long workflowId, Long departmentId) {
		WorkFlow workFlow = workFlowDao.findById(workflowId).orElse(null);
		if (workFlow == null)
			return null;
		WorkFlowRule workFlowRule = workFlowRuleDao.findfirstRuleByWFId(workFlow.getId());
		if (workFlowRule == null)
			return null;
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return null;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).orElse(null);
		if (user == null)
			return null;
		Staff staff = user.getStaff();
		if (staff == null)
			return null;
		String staffIds = workFlowRule.getStaffIds();
		String occupationIds = workFlowRule.getOccupationIds();
		String postIds = workFlowRule.getPostIds();
		Department department = null;
		if (departmentId == null || departmentId < 0)
			department = staff.getDepartment();
		else
			department = departMentDao.findById(departmentId).orElse(null);
		Set<Staff> managerStaffs = null;
		List<Long> deptIds = new ArrayList<>();
		if (StringHelper.isEmpty(staffIds)) {
			findSecondDeptChildIds(findSecondDepartMent(department, null), deptIds);
			managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, StringHelper.join(deptIds, ","));
		} else
			managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, null);
		List<Map<String, Object>> returnList = new ArrayList<>();
		for (Staff s : managerStaffs) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("value", s.getId());
			maps.put("text", s.getStaffName());
			returnList.add(maps);
		}
		return returnList;
	}

	public Set<Staff> processFileProcessRule(Long workFlowId) {
		return staffDao.findStaffByPost(FILEPOSTNAME);
	}

	public Department findSecondDepartMent(Department department, Department result) {
		if (result != null)
			return result;
		Integer departmentLevel = department.getDepartmentLevel();
		if (departmentLevel == 2) {
			result = department;
			return result;
		} else
			result = findSecondDepartMent(department.getParentDepartment(), result);
		return result;
	}

	public void findSecondDeptChildIds(Department d, List<Long> ids) {
		ids.add(d.getId());
		for (Department dc : d.getChildren()) {
			findSecondDeptChildIds(dc, ids);
		}
	}

	//返回当前自然月加班时间
	private double getAllOvertime(Long subStaffId) {
		Staff staff = null;
		if (subStaffId != null) {
			staff = staffDao.findById(subStaffId).get();
		} else {
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			Long userd = shiroUser.getId();
			User user = userDao.findById(userd).get();
			staff = user.getStaff();
		}
		return workFlowInstanceDao.findAllOvertimeByStaffId(staff.getId(), 1);
	}

	public String[] returnSystemInfo(Long workflowId) {
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).get();
		Staff staff = user.getStaff();
		if (staff == null)
			return null;
		if (workflowId == null || workflowId <= 0)
			return null;
		WorkFlow workFlow = workFlowDao.findById(workflowId).orElse(null);
		if (workFlow == null)
			return null;
		String[] returnValue = new String[9];
		//		String formNum = returnFormNum(workFlow.getWorkFlowType().getId());
		double allOvertime = getAllOvertime(null);
		Department department = staff.getDepartment();
		String departMentNameA = getDepartMentName(department, 3);
		String departMentNameB = getDepartMentName(department, 2);
		String pointName = "";
		if (workFlow.getWorkFlowType().getId() == 3)
			pointName = "综合部经理";
		else {
			WorkFlowRule flowRule = workFlowRuleDao.findfirstRuleByWFId(workFlow.getId());
			pointName = flowRule.getPointName();
		}
		returnValue[0] = "";
		returnValue[1] = DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_DATE_FORMAT);
		returnValue[2] = staff.getStaffName();
		returnValue[3] = staff.getStaffNo();
		returnValue[4] = staff.getDepartment().getDepartmentName();
		returnValue[5] = allOvertime + "";
		returnValue[6] = pointName;
		returnValue[7] = departMentNameA;
		returnValue[8] = departMentNameB;
		return returnValue;
	}

	public synchronized String returnFormNum(Long workTypeId) {
		WorkFlowType workFlowType = workFlowTypeDao.findById(workTypeId).get();
		String num = workFlowInstanceDao.findInstanceNumByWorkFlowTypeId(workTypeId);
		Integer numInt = null;
		if (StringHelper.isEmpty(num))
			numInt = 1;
		else
			numInt = Integer.valueOf(num.substring(num.lastIndexOf("-"), num.length()).substring(1)) + 1;
		String newNum = workFlowType.getRemark() + numInt;
		return newNum;
	}

	private String getDepartMentName(Department department, int level) {
		if (department.getDepartmentLevel() <= 2)
			return department.getDepartmentName();
		if (department.getDepartmentLevel() == level)
			return department.getDepartmentName();
		getDepartMentName(department.getParentDepartment(), level);
		return null;
	}

	//通过流程ID返回节点信息
	public String[] returnWorkFlowPointInfo(Long wfId) {
		WorkFlow workFlow = workFlowDao.findById(wfId).orElse(null);
		if (workFlow == null)
			return null;
		List<WorkFlowRule> rules = workFlowRuleDao.findWorkFlowRuleByWorkFlowId(wfId);
		String[] returnValue = new String[rules.size()];
		for (int i = 0; i < rules.size(); i++) {
			StringBuilder sb = new StringBuilder();
			WorkFlowRule workFlowRule = rules.get(i);
			sb.append(POINT).append(workFlowRule.getOrderNumber()).append(POINT).append(workFlowRule.getPointName());
			sb.append(POINT).append(workFlowRule.getOccupationIds() == null ? "" : workFlowRule.getOccupationIds()).append(POINT)
					.append(workFlowRule.getOccupationNames() == null ? "" : workFlowRule.getOccupationNames());
			sb.append(POINT).append(workFlowRule.getPostIds() == null ? "" : workFlowRule.getPostIds()).append(POINT)
					.append(workFlowRule.getPostNames() == null ? "" : workFlowRule.getPostNames());
			sb.append(POINT).append(workFlowRule.getStaffIds() == null ? "" : workFlowRule.getStaffIds()).append(POINT)
					.append(workFlowRule.getStaffNames() == null ? "" : workFlowRule.getStaffNames());
			returnValue[i] = sb.substring(5).toString();
		}
		return returnValue;
	}

	//审批通过
	public int crossInstance(String crossReason, Long managerStaffId, Long wfId, String params, String fileName, String filePath) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfId).get();
		WorkFlow workFlow = workFlowInstance.getWorkFlow();
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser != null) {
			Long userd = shiroUser.getId();
			Staff staff = accountService.getStaffInfo(userd);
			if (staff != null && !staff.getId().equals(workFlowInstance.getManagerStaffId()))
				return 1;
		}
		if (workFlow.getWorkFlowType().getId() == 3) {
			int result = crossFileInstance(crossReason, String.valueOf(managerStaffId), wfId, params);
			return result;
		}
		WorkFlowRule workFlowRule = workFlowRuleDao.findWorkFlowRuleByWFAndNum(workFlow.getId(), workFlowInstance.getPointNum() + 1);
		WorkFlowPointInfo workFlowPointInfo = new WorkFlowPointInfo();
		workFlowPointInfo.setApprovalState(0);
		workFlowPointInfo.setPointName(workFlowInstance.getPointName());
		workFlowPointInfo.setPointNum(workFlowInstance.getPointNum());
		workFlowPointInfo.setReason(crossReason);
		workFlowPointInfo.setApprovalStaffId(workFlowInstance.getManagerStaffId());
		workFlowPointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowPointInfo.setTaskReceiveTime(workFlowInstance.getPointReceiveTime());
		workFlowPointInfo.setWorkFlowInstance(workFlowInstance);
		workFlowPointInfoDao.save(workFlowPointInfo);
		if (workFlowRule == null) {
			workFlowInstance.setIsComplete(WorkFlowInstance.POINTSTATE_COMPLETE);
			workFlowInstance.setManagerStaffId(managerStaffId);
			workFlowInstance.setPointAssignee(null);
			workFlowInstance.setPointName(null);
			workFlowInstance.setPointNum(0);
			workFlowInstance.setPointApproveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
			if (workFlow.getWorkFlowType().getId() == 8) {
				BusinessData businessData = workFlowInstance.getBusinessData();
				String dataInfo = businessData.getDataInfo();
				systemService.addContractWill(dataInfo, 0, workFlowInstance.getInstanceNum(), workFlowInstance.getPointCreateTime());
			}
			if (workFlow.getWorkFlowType().getId() == 15 && workFlowInstance.getReserve8() != null)
				getStaffPostChangeWebService.doPost(workFlowInstance);
			systemService.addNoticeForInstance("流程审批通过通知",
					workFlowInstance.getApplyUser() + "您好：您申请的流程编号为：" + workFlowInstance.getInstanceNum() + "的流程已通过审批，请到历史流程中查看。",
					String.valueOf(workFlowInstance.getStaffId()), null, workFlowInstance);
		} else {
			Staff managerStaff = staffDao.findById(managerStaffId).get();
			workFlowInstance.setPointReason(crossReason);
			workFlowInstance.setManagerStaffId(managerStaffId);
			workFlowInstance.setPointAssignee(managerStaff.getStaffName());
			workFlowInstance.setIfreject(0);
			workFlowInstance.setPointName(workFlowRule.getPointName());
			workFlowInstance.setPointNum(workFlowRule.getOrderNumber());
			workFlowInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
			//			BusinessData businessData = businessDataDao.findBusinessDataByWFIId(workFlowInstance.getId());
			//			businessData.setDataInfo(params);
			//			businessDataDao.save(businessData);
		}
		if (StringHelper.isNotEmpty(fileName) && StringHelper.isNotEmpty(filePath)) {
			if (StringHelper.isEmpty(workFlowInstance.getAttachmentPath()))
				workFlowInstance.setAttachmentName(fileName.substring(2, fileName.length()));
			else {
				workFlowInstance.setAttachmentName(workFlowInstance.getAttachmentName() + fileName);
			}
			workFlowInstance.setAttachmentPath(filePath);
		}
		workFlowInstanceDao.save(workFlowInstance);
		return 1;
	}

	public int crossFileInstance(String crossReason, String managerStaffId, Long instanceId, String deptNames) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(instanceId).get();
		Long managerStaffIdLong = null;
		StringBuilder managerStaffIds = new StringBuilder();
		WorkFlowPointInfo workFlowPointInfo = new WorkFlowPointInfo();
		workFlowPointInfo.setPointName(workFlowInstance.getPointName());
		if (workFlowInstance.getPointName().equals(FILEPOSTNAME1)) {
			if (StringHelper.isNotEmpty(workFlowInstance.getReserve19())) {
				if (Integer.valueOf(workFlowInstance.getReserve19()) == 2) {
					workFlowInstance.setIsComplete(WorkFlowInstance.POINTSTATE_COMPLETE);
					workFlowInstance.setManagerStaffId(null);
					workFlowInstance.setPointAssignee(null);
					workFlowInstance.setPointName(null);
					workFlowInstance.setPointNum(0);
					workFlowInstance.setPointApproveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
					workFlowInstanceDao.save(workFlowInstance);
					return 1;
				} else
					workFlowInstance.setReserve19(String.valueOf(Integer.valueOf(workFlowInstance.getReserve19()) + 1));
			} else
				workFlowInstance.setReserve19("1");
		} else if (workFlowInstance.getPointName().equals(FILEPOSTNAME)) {
			managerStaffIdLong = Long.valueOf(managerStaffId);
			workFlowInstance.setPointName(FILEPOSTNAME1);
		}
		if (!StringHelper.isEmpty(workFlowInstance.getReserve10())) {
			managerStaffIdLong = Long.valueOf(workFlowInstance.getReserve10());
			workFlowInstance.setReserve10(null);
			workFlowInstance.setPointName(FILEPOSTNAME3);
		} else if (!StringHelper.isEmpty(workFlowInstance.getReserve11())) {
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			User user = userDao.findById(shiroUser.getId()).get();
			Staff staff = user.getStaff();
			String reserve11 = workFlowInstance.getReserve11();
			String[] managerStaffIdsArr = reserve11.split(",");
			for (int i = 0; i < managerStaffIdsArr.length; i++) {
				if (Long.valueOf(managerStaffIdsArr[i]).equals(staff.getId())) {
					managerStaffIdsArr = (String[]) ArrayUtils.remove(managerStaffIdsArr, i);
					if (workFlowInstance.getManagerStaffId() == null)
						workFlowInstance.setManagerStaffId(staff.getId());
				}
			}
			if (managerStaffIdsArr.length == 0) {
				workFlowInstance.setReserve11(null);
				if (managerStaffId.contains(",")) {
					String[] idsArr = null;
					idsArr = managerStaffId.split(",");
					Set<Staff> staffs = staffDao.findStaffByPost(OCCNAME);
					for (int i = 0; i < idsArr.length; i++) {
						if (Long.valueOf(idsArr[i]).equals(staffs.iterator().next().getId())) {
							managerStaffIdLong = Long.valueOf(idsArr[i]);
							idsArr = (String[]) ArrayUtils.remove(idsArr, i);
						}
					}
					workFlowInstance.setReserve10(idsArr[0]);
					workFlowInstance.setPointName(FILEPOSTNAME3);
				} else
					workFlowInstance.setPointName(FILEPOSTNAME1);
				managerStaffIdLong = managerStaffIdLong == null ? Long.valueOf(managerStaffId) : managerStaffIdLong;
			} else {
				workFlowInstance.setReserve11(StringHelper.join(managerStaffIdsArr, ",") + ",");
				workFlowInstance.setPointName(FILEPOSTNAME2);
			}
		} else {
			if (StringHelper.isEmpty(managerStaffId)) {
				String[] deptNamesArr = deptNames.split(",");
				for (String dn : deptNamesArr) {
					Staff staff = staffDao.findStaffByDeptAndPost(dn, dn + "经理");
					if (staff == null)
						continue;
					managerStaffIds.append(",").append(staff.getId());
				}
				workFlowInstance.setReserve11(managerStaffIds.substring(1).toString() + ",");
				workFlowInstance.setPointName(FILEPOSTNAME2);
			} else {
				if (managerStaffId.contains(",")) {
					String[] idsArr = null;
					idsArr = managerStaffId.split(",");
					Set<Staff> staffs = staffDao.findStaffByPost(OCCNAME);
					for (int i = 0; i < idsArr.length; i++) {
						if (Long.valueOf(idsArr[i]).equals(staffs.iterator().next().getId())) {
							managerStaffIdLong = Long.valueOf(idsArr[i]);
							idsArr = (String[]) ArrayUtils.remove(idsArr, i);
						}
					}
					workFlowInstance.setReserve10(idsArr[0]);
					workFlowInstance.setPointName(FILEPOSTNAME3);
				} else if (workFlowInstance.getPointName().equals(FILEPOSTNAME1) && StringHelper.isNotEmpty(workFlowInstance.getReserve19())
						&& workFlowInstance.getReserve19().equals("2"))
					workFlowInstance.setPointName(FILEPOSTNAME4);
				else
					workFlowInstance.setPointName(FILEPOSTNAME1);
				managerStaffIdLong = managerStaffIdLong == null ? Long.valueOf(managerStaffId) : managerStaffIdLong;
			}
		}
		workFlowPointInfo.setApprovalState(0);
		workFlowPointInfo.setPointNum(workFlowInstance.getPointNum());
		workFlowPointInfo.setReason(crossReason);
		workFlowPointInfo.setApprovalStaffId(workFlowInstance.getManagerStaffId());
		workFlowPointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowPointInfo.setTaskReceiveTime(workFlowInstance.getPointReceiveTime());
		workFlowPointInfo.setWorkFlowInstance(workFlowInstance);
		workFlowPointInfoDao.save(workFlowPointInfo);
		if (managerStaffIdLong != null) {
			Staff managerStaff = staffDao.findById(managerStaffIdLong).get();
			workFlowInstance.setPointAssignee(managerStaff.getStaffName());
		}
		workFlowInstance.setPointReason(crossReason);
		workFlowInstance.setManagerStaffId(managerStaffIdLong);
		workFlowInstance.setIfreject(0);
		workFlowInstance.setPointNum(workFlowInstance.getPointNum() + 1);
		workFlowInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowInstanceDao.save(workFlowInstance);
		return 1;
	}

	//驳回流程
	public int rejectInstance(String rejectReason, Long wfId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfId).get();
		Long managerStaffId = workFlowInstance.getSubStaffId() == null ? workFlowInstance.getStaffId() : workFlowInstance.getSubStaffId();
		Staff managerStaff = staffDao.findById(managerStaffId).get();
		WorkFlowPointInfo workFlowPointInfo = new WorkFlowPointInfo();
		workFlowPointInfo.setApprovalState(1);
		workFlowPointInfo.setPointName("调整申请");
		workFlowPointInfo.setPointNum(workFlowInstance.getPointNum());
		workFlowPointInfo.setReason(rejectReason);
		if (workFlowInstance.getWorkFlow().getWorkFlowType().getId() == 3 && workFlowInstance.getManagerStaffId() == null) {
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			Long userd = shiroUser.getId();
			User user = userDao.findById(userd).get();
			Staff staff = user.getStaff();
			workFlowPointInfo.setApprovalStaffId(staff.getId());
		} else
			workFlowPointInfo.setApprovalStaffId(workFlowInstance.getManagerStaffId());
		workFlowPointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowPointInfo.setTaskReceiveTime(workFlowInstance.getPointReceiveTime());
		workFlowInstance.setPointReason(rejectReason);
		workFlowInstance.setManagerStaffId(managerStaffId);
		workFlowInstance.setPointAssignee(managerStaff.getStaffName());
		workFlowInstance.setIfreject(1);
		workFlowInstance.setPointName("调整申请");
		workFlowInstance.setPointNum(0);
		workFlowInstance.setReserve10(null);
		workFlowInstance.setReserve11(null);
		workFlowInstance.setReserve19(null);
		workFlowInstanceDao.save(workFlowInstance);
		workFlowPointInfo.setWorkFlowInstance(workFlowInstance);
		workFlowPointInfoDao.save(workFlowPointInfo);
		return 1;
	}

	//审批财务通过
	public int crossFinancialNotesInstance(String crossReason, Long managerStaffId, Long wfId, Long[] nextDeptStaffIds, String fileName, String filePath) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfId).get();
		WorkFlow workFlow = workFlowInstance.getWorkFlow();
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser != null) {
			Long userd = shiroUser.getId();
			Staff staff = accountService.getStaffInfo(userd);
			if (staff != null && !staff.getId().equals(workFlowInstance.getManagerStaffId()))
				return 1;
		}
		if (nextDeptStaffIds != null && nextDeptStaffIds.length > 0) {
			Long first = nextDeptStaffIds[0];
			nextDeptStaffIds = (Long[]) ArrayUtils.remove(nextDeptStaffIds, 0);
			String join = StringHelper.join(nextDeptStaffIds, ",");
			workFlowInstance.setReserve10(join);
			Staff managerStaff = staffDao.findById(first).get();
			workFlowInstance.setPointReason(crossReason);
			workFlowInstance.setManagerStaffId(first);
			workFlowInstance.setPointAssignee(managerStaff.getStaffName());
			workFlowInstance.setIfreject(0);
			workFlowInstance.setPointName("相关部门");
			workFlowInstance.setPointNum(5);
			workFlowInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
			workFlowInstanceDao.save(workFlowInstance);
			WorkFlowPointInfo workFlowPointInfo = new WorkFlowPointInfo();
			workFlowPointInfo.setApprovalState(0);
			workFlowPointInfo.setPointName("相关部门");
			workFlowPointInfo.setPointNum(5);
			workFlowPointInfo.setReason(crossReason);
			workFlowPointInfo.setApprovalStaffId(workFlowInstance.getManagerStaffId());
			workFlowPointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
			workFlowPointInfo.setTaskReceiveTime(workFlowInstance.getPointReceiveTime());
			workFlowPointInfo.setWorkFlowInstance(workFlowInstance);
			workFlowPointInfoDao.save(workFlowPointInfo);
		} else {
			if (StringHelper.isNotEmpty(workFlowInstance.getReserve10())) {
				String reserve10 = workFlowInstance.getReserve10();
				String[] idsArr = reserve10.split(",");
				StringBuilder sb = new StringBuilder();
				Staff managerStaff = null;
				for (int i = 0; i < idsArr.length; i++) {
					managerStaff = staffDao.findById(Long.valueOf(idsArr[i])).get();
					if (i == 0)
						continue;
					sb.append(",").append(idsArr[i]);
				}
				workFlowInstance.setPointReason(crossReason);
				workFlowInstance.setManagerStaffId(managerStaff.getId());
				workFlowInstance.setPointAssignee(managerStaff.getStaffName());
				workFlowInstance.setIfreject(0);
				workFlowInstance.setPointName("相关部门");
				workFlowInstance.setPointNum(5);
				workFlowInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
				workFlowInstance.setReserve10(sb == null ? null : sb.substring(1).toString());
				workFlowInstanceDao.save(workFlowInstance);

				WorkFlowPointInfo workFlowPointInfo = new WorkFlowPointInfo();
				workFlowPointInfo.setApprovalState(0);
				workFlowPointInfo.setPointName("相关部门");
				workFlowPointInfo.setPointNum(5);
				workFlowPointInfo.setReason(crossReason);
				workFlowPointInfo.setApprovalStaffId(workFlowInstance.getManagerStaffId());
				workFlowPointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
				workFlowPointInfo.setTaskReceiveTime(workFlowInstance.getPointReceiveTime());
				workFlowPointInfo.setWorkFlowInstance(workFlowInstance);
				workFlowPointInfoDao.save(workFlowPointInfo);
			} else {
				WorkFlowRule workFlowRule = workFlowRuleDao.findWorkFlowRuleByWFAndNum(workFlow.getId(), workFlowInstance.getPointNum() + 1);
				WorkFlowPointInfo workFlowPointInfo = new WorkFlowPointInfo();
				workFlowPointInfo.setApprovalState(0);
				workFlowPointInfo.setPointName(workFlowInstance.getPointName());
				workFlowPointInfo.setPointNum(workFlowInstance.getPointNum());
				workFlowPointInfo.setReason(crossReason);
				workFlowPointInfo.setApprovalStaffId(workFlowInstance.getManagerStaffId());
				workFlowPointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
				workFlowPointInfo.setTaskReceiveTime(workFlowInstance.getPointReceiveTime());
				workFlowPointInfo.setWorkFlowInstance(workFlowInstance);
				workFlowPointInfoDao.save(workFlowPointInfo);
				if (workFlowRule == null) {
					workFlowInstance.setIsComplete(WorkFlowInstance.POINTSTATE_COMPLETE);
					workFlowInstance.setManagerStaffId(managerStaffId);
					workFlowInstance.setPointAssignee(null);
					workFlowInstance.setPointName(null);
					workFlowInstance.setPointNum(0);
					workFlowInstance.setPointApproveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
					workFlowInstanceDao.save(workFlowInstance);
				} else {
					Staff managerStaff = staffDao.findById(managerStaffId).get();
					workFlowInstance.setPointReason(crossReason);
					workFlowInstance.setManagerStaffId(managerStaffId);
					workFlowInstance.setPointAssignee(managerStaff.getStaffName());
					workFlowInstance.setIfreject(0);
					workFlowInstance.setPointName(workFlowRule.getPointName());
					workFlowInstance.setPointNum(workFlowRule.getOrderNumber());
					workFlowInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
					workFlowInstanceDao.save(workFlowInstance);
					//			BusinessData businessData = businessDataDao.findBusinessDataByWFIId(workFlowInstance.getId());
					//			businessData.setDataInfo(params);
					//			businessDataDao.save(businessData);
				}
			}
		}
		if (StringHelper.isNotEmpty(fileName) && StringHelper.isNotEmpty(filePath)) {
			if (StringHelper.isEmpty(workFlowInstance.getAttachmentPath()))
				workFlowInstance.setAttachmentName(fileName.substring(2, fileName.length()));
			else {
				workFlowInstance.setAttachmentName(workFlowInstance.getAttachmentName() + fileName);
			}
			workFlowInstance.setAttachmentPath(filePath);
		}
		workFlowInstanceDao.save(workFlowInstance);
		return 1;
	}

	//驳回财务流程
	public int rejectFinancialNotesInstance(String rejectReason, Long wfId, Long managerStaffId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfId).get();
		Staff managerStaff = staffDao.findById(managerStaffId).get();
		WorkFlowPointInfo workFlowPointInfo = new WorkFlowPointInfo();
		workFlowPointInfo.setApprovalState(1);
		workFlowPointInfo.setPointName("调整申请");
		workFlowPointInfo.setPointNum(workFlowInstance.getPointNum());
		workFlowPointInfo.setReason(rejectReason);
		workFlowPointInfo.setApprovalStaffId(workFlowInstance.getManagerStaffId());
		workFlowPointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowPointInfo.setTaskReceiveTime(workFlowInstance.getPointReceiveTime());
		workFlowInstance.setPointReason(rejectReason);
		workFlowInstance.setManagerStaffId(managerStaffId);
		workFlowInstance.setPointAssignee(managerStaff.getStaffName());
		workFlowInstance.setIfreject(1);
		workFlowInstance.setPointName("调整申请");
		WorkFlowPointInfo pointInfo = workFlowPointInfoDao.findWorkFlowPointInfoByWfIdAndStaffId(wfId, managerStaffId);
		workFlowInstance.setPointNum(pointInfo.getPointNum());
		workFlowInstanceDao.save(workFlowInstance);
		workFlowPointInfo.setWorkFlowInstance(workFlowInstance);
		workFlowPointInfoDao.save(workFlowPointInfo);
		return 1;
	}

	public List<Map<String, Object>> getAllrejectStaffForFinancialNotes(Long wfId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfId).get();
		List<WorkFlowPointInfo> pointInfo = workFlowPointInfoDao.findAllWorkFlowPointInfo(wfId);
		List<Map<String, Object>> returnList = new ArrayList<>();
		for (WorkFlowPointInfo wp : pointInfo) {
			if (wp.getPointNum() >= workFlowInstance.getPointNum())
				continue;
			Staff s = staffDao.findById(wp.getApprovalStaffId()).get();
			Map<String, Object> maps = new HashMap<>();
			maps.put("value", s.getId());
			maps.put("text", s.getStaffName());
			returnList.add(maps);
		}
		return returnList;
	}

	//自定义保存新流程
	public int saveInstance(String[] params, String wfName, Long workTypeId) {
		WorkFlowType workFlowType = workFlowTypeDao.findById(workTypeId).get();
		WorkFlow oldWorkFlow = workFlowDao.findWorkFlowByType(workTypeId);
		int version = oldWorkFlow == null ? 0 : oldWorkFlow.getVersion();
		WorkFlow workFlow = new WorkFlow();
		String curDateStr = DateUtil.getCurDateStr();
		workFlow.setCreateTime(curDateStr);
		workFlow.setUpdateTime(curDateStr);
		workFlow.setName(wfName);
		workFlow.setPoint(params.length);
		workFlow.setVersion(version + 1);
		workFlow.setWorkFlowType(workFlowType);
		for (String p : params) {
			JSONObject jsonObject = TfOaUtil.fromObject(p);
			String pointName = jsonObject.getString("pointName");
			String pointNum = jsonObject.getString("num");
			String occupationId = jsonObject.getString("occupationId");
			String staffId = jsonObject.getString("staffId");
			String postId = jsonObject.getString("postId");
			String occupationNames = jsonObject.getString("occupationNames");
			String staffNames = jsonObject.getString("staffNames");
			String postNames = jsonObject.getString("postNames");
			WorkFlowRule rule = new WorkFlowRule();
			if (!StringHelper.isEmpty(occupationId)) {
				rule.setOccupationIds(occupationId);
				rule.setOccupationNames(occupationNames);
			}
			if (!StringHelper.isEmpty(staffId)) {
				rule.setStaffIds(staffId);
				rule.setStaffNames(staffNames);
			}
			if (!StringHelper.isEmpty(postId)) {
				rule.setPostIds(postId);
				rule.setPostNames(postNames);
			}
			rule.setPointName(pointName);
			rule.setOrderNumber(Integer.valueOf(pointNum));
			rule.setWorkFlow(workFlow);
			rule.setCreateTime(TfOaUtil.getCurTime());
			workFlowRuleDao.save(rule);
		}
		return 1;
	}

	public int updateInstance(String[] params, String wfName, Long wfId, Long workTypeId) {
		WorkFlow workFlow = workFlowDao.findById(wfId).get();
		WorkFlowType workFlowType = workFlowTypeDao.findById(workTypeId).get();
		List<WorkFlowRule> oldRules = workFlowRuleDao.findWorkFlowRuleByWorkFlowId(wfId);
		for (WorkFlowRule workFlowRule : oldRules) {
			workFlowRuleDao.delete(workFlowRule);
		}
		for (String p : params) {
			JSONObject jsonObject = TfOaUtil.fromObject(p);
			String pointName = jsonObject.getString("pointName");
			String pointNum = jsonObject.getString("num");
			String occupationId = jsonObject.getString("occupationId");
			String staffId = jsonObject.getString("staffId");
			String postId = jsonObject.getString("postId");
			WorkFlowRule rule = new WorkFlowRule();
			String occupationNames = jsonObject.getString("occupationNames");
			String staffNames = jsonObject.getString("staffNames");
			String postNames = jsonObject.getString("postNames");
			if (!StringHelper.isEmpty(occupationId)) {
				rule.setOccupationIds(occupationId);
				rule.setOccupationNames(occupationNames);
			}
			if (!StringHelper.isEmpty(staffId)) {
				rule.setStaffIds(staffId);
				rule.setStaffNames(staffNames);
			}
			if (!StringHelper.isEmpty(postId)) {
				rule.setPostIds(postId);
				rule.setPostNames(postNames);
			}
			rule.setOrderNumber(Integer.valueOf(pointNum));
			rule.setPointName(pointName);
			rule.setWorkFlow(workFlow);
			rule.setCreateTime(TfOaUtil.getCurTime());
			workFlowRuleDao.save(rule);
		}
		workFlow.setPoint(params.length);
		workFlow.setName(wfName);
		workFlow.setWorkFlowType(workFlowType);
		workFlowDao.save(workFlow);
		return 1;
	}

	public int deleteInstance(String wfIds) {
		if (StringHelper.isEmpty(wfIds))
			return -1;
		String[] split = wfIds.split(",");
		int i = 0;
		for (String wfId : split) {
			Long num = workFlowInstanceDao.findInstanceByWfId(Long.valueOf(wfId));
			if (num > 0)
				continue;
			List<WorkFlowRule> findWorkFlowRuleByWorkFlowId = workFlowRuleDao.findWorkFlowRuleByWorkFlowId(Long.valueOf(wfId));
			for (WorkFlowRule workFlowRule : findWorkFlowRuleByWorkFlowId) {
				workFlowRuleDao.delete(workFlowRule);
			}
			workFlowDao.deleteById(Long.valueOf(wfId));
			i++;
		}
		return i;
	}

	//删除驳回流程
	public int deleteRejectInstance(Long wfiId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfiId).get();
		workFlowInstance.setPointApproveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowInstance.setPointName(null);
		workFlowInstance.setPointNum(0);
		workFlowInstance.setManagerStaffId(null);
		workFlowInstance.setIsComplete(WorkFlowInstance.POINTSTATE_DELETE);
		workFlowInstanceDao.save(workFlowInstance);
		return 1;
	}

	public int reloadInstance(String params, Long wfiId, Long subStaffId, String fileName, String filePath, Long managerStaffId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfiId).get();
		BusinessData businessData = workFlowInstance.getBusinessData();
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String reason = "";
		String company = "";
		String deptName = "";
		if (jsonObject.has("reason"))
			reason = jsonObject.getString("reason");
		if (jsonObject.has("company")) {
			String cid = jsonObject.getString("company");
			if (!StringHelper.isEmpty(cid) && isNumeric(cid)) {
				Department department = departMentDao.findById(Long.valueOf(cid)).orElse(null);
				if (department != null)
					company = department.getDepartmentName();
			} else
				company = cid;
		}
		if (jsonObject.has("deptName")) {
			String did = jsonObject.getString("deptName");
			if (!StringHelper.isEmpty(did) && isNumeric(did)) {
				Department department = departMentDao.findById(Long.valueOf(did)).orElse(null);
				if (department != null)
					deptName = department.getDepartmentName();
			} else
				deptName = did;

		}
		businessData.setDataInfo(params);
		if (!StringHelper.isEmpty(filePath) && !StringHelper.isEmpty(fileName)) {
			if (StringHelper.isEmpty(workFlowInstance.getAttachmentPath()))
				workFlowInstance.setAttachmentName(fileName.substring(2, fileName.length()));
			else {
				workFlowInstance.setAttachmentName(workFlowInstance.getAttachmentName() + fileName);
			}
			workFlowInstance.setAttachmentPath(filePath);
		}
		workFlowInstance.setBusinessData(businessData);
		workFlowInstance.setManagerStaffId(managerStaffId);
		int pointNum = workFlowInstance.getPointNum();
		workFlowInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		WorkFlow workFlow = workFlowInstance.getWorkFlow();
		WorkFlowRule workFlowRule = workFlowRuleDao.findfirstRuleByWFId(workFlow.getId());
		workFlowInstance.setPointName(workFlowRule.getPointName());
		workFlowInstance.setPointNum(workFlowRule.getOrderNumber());
		workFlowInstance.setPointReason(reason);
		workFlowInstance.setReserve13(company);
		workFlowInstance.setReserve14(deptName);
		workFlowInstance.setIfreject(0);
		workFlowInstanceDao.save(workFlowInstance);
		WorkFlowRule applyrule = workFlowRuleDao.findApplyRuleByWFId(workFlow.getId());
		WorkFlowPointInfo pointInfo = new WorkFlowPointInfo();
		pointInfo.setPointNum(pointNum);
		pointInfo.setApprovalState(-1);
		pointInfo.setPointName(applyrule.getPointName());
		pointInfo.setReason(reason);
		pointInfo.setApprovalStaffId(workFlowInstance.getStaffId());
		pointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		pointInfo.setTaskReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		pointInfo.setWorkFlowInstance(workFlowInstance);
		workFlowPointInfoDao.save(pointInfo);
		return 1;
	}

	public int uploadFileForContract(Long wfiId, String fileName) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfiId).get();
		String attachmentName = workFlowInstance.getAttachmentName();
		String[] fileNames = attachmentName.split("##");
		for (int i = 0; i < fileNames.length; i++) {
			if (fileNames[i].equals(fileName))
				fileNames = (String[]) ArrayUtils.remove(fileNames, i);
		}
		if (fileNames.length <= 0) {
			workFlowInstance.setAttachmentPath(null);
			workFlowInstance.setAttachmentName(null);
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < fileNames.length; i++)
				sb.append("##").append(fileNames[i]);
			workFlowInstance.setAttachmentName(sb.substring(2).toString());
		}
		workFlowInstanceDao.save(workFlowInstance);
		return 1;
	}

	public int reloadFileInstance(String params, Long wfiId, Long subStaffId, String fileName, String filePath, Long managerStaffId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(wfiId).get();
		BusinessData businessData = workFlowInstance.getBusinessData();
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String reason = "";
		String company = "";
		String deptName = "";
		if (jsonObject.has("reason"))
			reason = jsonObject.getString("reason");
		if (jsonObject.has("company")) {
			String cid = jsonObject.getString("company");
			Department department = departMentDao.findById(Long.valueOf(cid)).orElse(null);
			if (department != null)
				company = department.getDepartmentName();
		}
		if (jsonObject.has("deptName"))
			deptName = jsonObject.getString("deptName");
		businessData.setDataInfo(params);
		if (!StringHelper.isEmpty(filePath) && !StringHelper.isEmpty(fileName)) {
			if (StringHelper.isEmpty(workFlowInstance.getAttachmentPath()))
				workFlowInstance.setAttachmentName(fileName.substring(2, fileName.length()));
			else {
				workFlowInstance.setAttachmentName(workFlowInstance.getAttachmentName() + fileName);
			}
			workFlowInstance.setAttachmentPath(filePath);
		}
		workFlowInstance.setBusinessData(businessData);
		workFlowInstance.setManagerStaffId(managerStaffId);
		int pointNum = workFlowInstance.getPointNum();
		workFlowInstance.setPointReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowInstance.setPointName(FILEPOSTNAME);
		workFlowInstance.setPointNum(1);
		workFlowInstance.setPointReason(reason);
		workFlowInstance.setReserve13(company);
		workFlowInstance.setReserve14(deptName);
		workFlowInstance.setIfreject(0);
		workFlowInstanceDao.save(workFlowInstance);
		WorkFlowPointInfo pointInfo = new WorkFlowPointInfo();
		pointInfo.setPointNum(pointNum);
		pointInfo.setApprovalState(-1);
		pointInfo.setPointName("收发文经办");
		pointInfo.setApprovalStaffId(workFlowInstance.getStaffId());
		pointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		pointInfo.setTaskReceiveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		pointInfo.setWorkFlowInstance(workFlowInstance);
		workFlowPointInfoDao.save(pointInfo);
		return 1;
	}

	//流程编辑申请人
	public int editStartPerson(String wfIds, String staffIds, String staffNames, String postIds, String postNames, String occupationIds,
			String occupationNames) {
		if (StringHelper.isEmpty(wfIds))
			return 0;
		if (StringHelper.isEmpty(staffIds) && StringHelper.isEmpty(postIds) && StringHelper.isEmpty(occupationIds))
			return 0;
		String[] wfIdArr = wfIds.split(",");
		for (String wfId : wfIdArr) {
			WorkFlow workFlow = workFlowDao.findById(Long.valueOf(wfId)).orElse(null);
			if (workFlow == null)
				continue;
			workFlow.setStaffIds(staffIds);
			workFlow.setStaffNames(staffNames);
			workFlow.setOccupationIds(occupationIds);
			workFlow.setOccupationNames(occupationNames);
			workFlow.setPostIds(postIds);
			workFlow.setPostNames(postNames);
			workFlow.getStaffs().clear();
			Set<Staff> allStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, null);
			workFlow.getStaffs().addAll(allStaffs);
			workFlowDao.save(workFlow);
		}
		return 1;
	}

	public long findCountWithParams(String params) {
		return workFlowDao.getCountWithParams(params);
	}

	public List<WorkFlow> findListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		List<WorkFlow> listWithParams = workFlowDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
		for (WorkFlow workFlow : listWithParams) {
			List<WorkFlowRule> findWorkFlowRuleByWorkFlowId = workFlowRuleDao.findWorkFlowRuleByWorkFlowId(workFlow.getId());
			if (CollectionUtils.isNotEmpty(findWorkFlowRuleByWorkFlowId)) {
				List<String> workflowInfosList = new ArrayList<>();
				for (WorkFlowRule workFlowRule : findWorkFlowRuleByWorkFlowId) {
					workflowInfosList.add(workFlowRule.getOrderNumber() + ":" + workFlowRule.getPointName());
				}
				workFlow.setWorkflowInfo(StringHelper.join(workflowInfosList, ","));
			}
		}
		return listWithParams;
	}

	public List<Map<String, Object>> getAllWorkflowType() {
		return workFlowTypeDao.findAllWorkflowTypes();
	}

	public Map<String, Object> firstManagerStaff(String deptName) {
		Staff s = staffDao.findStaffByDeptAndPost(deptName, POSTNAME);
		if (s == null)
			return null;
		Map<String, Object> map = new HashMap<>();
		map.put("value", s.getId());
		map.put("text", s.getStaffName());
		return map;
	}

	public String getWorkFowNameById(Long workflowId) {
		if (workflowId == null || workflowId <= 0)
			return "";
		WorkFlow findOne = workFlowDao.findById(workflowId).orElse(null);
		if (findOne == null)
			return "";
		return findOne.getName();
	}

	public List<Map<String, Object>> getFileManagerStaff(String postName) {
		StringBuilder staffIds = new StringBuilder();
		StringBuilder staffNames = new StringBuilder();
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		Set<Staff> managerStaffs = new HashSet<>();
		if (postName.equals(FILEPOSTNAME3)) {
			String[] managerArr = new String[] { "总经理", "副总经理" };
			for (int i = 0; i < managerArr.length; i++) {
				Set<Staff> findStaffByPost = staffDao.findStaffByPost(managerArr[i]);
				managerStaffs.addAll(findStaffByPost);
			}
		}
		for (Staff s : managerStaffs) {
			staffIds.append(",").append(s.getId());
			staffNames.append(",").append(s.getStaffName());
		}
		map.put("text", staffNames.substring(1).toString());
		map.put("value", staffIds.substring(1).toString());
		resultList.add(map);
		return resultList;
	}

	public List<Map<String, Object>> getFileManagerStaff2(String postName) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		Set<Staff> managerStaffs = staffDao.findStaffByPost(postName);
		if (CollectionUtils.isEmpty(managerStaffs))
			return null;
		for (Staff s : managerStaffs) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("value", s.getId());
			maps.put("text", s.getStaffName());
			resultList.add(maps);
		}
		return resultList;
	}

	public int endInstance(Long instanceId, String crossReason) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(instanceId).get();
		WorkFlowPointInfo workFlowPointInfo = new WorkFlowPointInfo();
		workFlowPointInfo.setApprovalState(0);
		workFlowPointInfo.setPointName(workFlowInstance.getPointName());
		workFlowPointInfo.setPointNum(workFlowInstance.getPointNum());
		workFlowPointInfo.setReason(crossReason);
		workFlowPointInfo.setApprovalStaffId(workFlowInstance.getManagerStaffId());
		workFlowPointInfo.setTaskCrossTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowPointInfo.setTaskReceiveTime(workFlowInstance.getPointReceiveTime());
		workFlowPointInfo.setWorkFlowInstance(workFlowInstance);
		workFlowPointInfoDao.save(workFlowPointInfo);
		workFlowInstance.setIsComplete(WorkFlowInstance.POINTSTATE_COMPLETE);
		workFlowInstance.setManagerStaffId(null);
		workFlowInstance.setPointAssignee(null);
		workFlowInstance.setPointName(null);
		workFlowInstance.setPointNum(0);
		workFlowInstance.setPointApproveTime(DateUtil.formatTimestampToStringByFmt(TfOaUtil.getCurTime(), DateUtil.NORMAL_SQL_DATE_FORMAT));
		workFlowInstanceDao.save(workFlowInstance);
		return 1;
	}

	public List<String> getWorkFloaData(Long workFlowId) {
		//		WorkFlow workFlow = workFlowDao.findOne(workFlowId);
		List<WorkFlowRule> workFlowRules = workFlowRuleDao.findWorkFlowRuleByWorkFlowId(workFlowId);
		if (CollectionUtils.isEmpty(workFlowRules))
			return null;
		List<String> returnValue = new ArrayList<>();
		String firstPoint = "1：申请人";
		returnValue.add(firstPoint);
		for (int i = 0; i < workFlowRules.size(); i++) {
			WorkFlowRule workFlowRule = workFlowRules.get(i);
			StringBuilder sb = new StringBuilder();
			sb.append(i + 2).append("：").append("第" + (i + 1) + "审批人").append("  ").append(workFlowRule.getPointName());
			returnValue.add(sb.toString());
		}
		return returnValue;
	}

	//{"reason":"岗位异动","ApprovalStaff":"731","formId":"TCG-TF-GWYD-1","applyStaff":"葛亚琼","deptName":"技术支持部","applyDate":"2017-06-15","staffNo":"1511755",
	//"nextApproval":"部门经理","postChangeInfo":"{\"changeStaffName\":\"葛亚琼\",\"changeStaffNo\":\"1511755\",\"oldDepartmentName\":\"技术支持部\",\"oldPostNames\":\"职员\",
	//\"newDepartmentName\":\"技术支持部\",\"newPostNames\":\"部门经理\"}"}

	public String doPostChange(String applystaffid, String staffId, String oldpostid, String olddeptid, String newpostid, String newdeptid, String easFormid) {
		String oaformid = returnFormNum(15L);
		Staff applystaff = staffDao.findByOldId(applystaffid);
		Staff changestaff = staffDao.findByOldId(staffId);
		Department oldDepartment = departMentDao.findByOldId(olddeptid);
		Post oldPost = postDao.findByOldId(oldpostid);
		Department newDepartment = departMentDao.findByOldId(newdeptid);
		Post newPost = postDao.findByOldId(newpostid);
		if (oldDepartment == null)
			return "原部门不存在";
		if (oldPost == null)
			return "原岗位不存在";
		if (newDepartment == null)
			return "新部门不存在";
		if (newPost == null)
			return "新岗位不存在";
		String today = DateUtil.formatTimestampToStringByFmt(DateUtil.getNowTimestamp(), DateUtil.NORMAL_DATE_FORMAT);
		WorkFlow workFlow = workFlowDao.findWorkFlowByStaffAndType(applystaff.getId(), 15L);
		if (workFlow == null)
			return "此员工无对应流程";
		WorkFlowRule workFlowRule = workFlowRuleDao.findfirstRuleByWFId(workFlow.getId());
		if (workFlowRule == null)
			return "此员工无对应流程规则";
		String staffIds = workFlowRule.getStaffIds();
		String occupationIds = workFlowRule.getOccupationIds();
		String postIds = workFlowRule.getPostIds();
		List<Long> deptIds = new ArrayList<>();
		Set<Staff> managerStaffs = null;
		if (StringHelper.isEmpty(staffIds)) {
			findSecondDeptChildIds(findSecondDepartMent(oldDepartment, null), deptIds);
			managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, StringHelper.join(deptIds, ","));
		} else
			managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, null);
		Staff managerStaff = managerStaffs.iterator().next();
		if (managerStaff == null)
			return "此员工对应流程无审批人";
		StringBuilder sb = new StringBuilder("{\"reason\":\"shr-岗位异动\",\"ApprovalStaff\":").append("\"" + applystaffid + "\",\"formId\":")
				.append("\"" + oaformid + "\",").append("\"applyStaff\":\"").append("" + applystaff.getStaffName() + "\",").append("\"deptName\":")
				.append("\"" + applystaff.getDepartment().getDepartmentName() + "\",").append("\"applyDate\":\"").append("" + today + "\",")
				.append("\"staffNo\":").append("\"" + applystaff.getStaffNo() + "\",").append("\"nextApproval\":\"")
				.append("" + workFlowRule.getPointName() + "\",").append("\"postChangeInfo\":").append("\"{\\\"changeStaffName\\\":")
				.append("\\\"" + changestaff.getStaffName() + "\\\",").append("\\\"changeStaffNo\\\":").append("\\\"" + changestaff.getStaffNo() + "\\\",")
				.append("\\\"oldDepartmentName\\\":").append("\\\"" + oldDepartment.getDepartmentName() + "\\\",").append("\\\"oldPostNames\\\":")
				.append("\\\"" + oldPost.getPostName() + "\\\",").append("\\\"newDepartmentName\\\":")
				.append("\\\"" + newDepartment.getDepartmentName() + "\\\",").append("\\\"newPostNames\\\":")
				.append("\\\"" + newPost.getPostName() + "\\\"}\"}");
		int startInstance = startInstance(sb.toString(), workFlow.getId(), null, null, null, managerStaff.getId(), easFormid, applystaff);
		if (startInstance > 0)
			return "success";
		return "未知错误";
	}

	public Map<String, Object> endPostChange(WorkFlowInstance flowInstance) {
		String pointReason = flowInstance.getPointReason();
		int isComplete = flowInstance.getIsComplete();
		String reserve8 = flowInstance.getReserve8();
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("BillNo", reserve8);
		returnMap.put("ApprResult", isComplete == 1 ? 2 : 1);
		returnMap.put("ApprView", pointReason);
		return returnMap;
	}

}
