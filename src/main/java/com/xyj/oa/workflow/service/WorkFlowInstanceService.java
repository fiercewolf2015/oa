package com.xyj.oa.workflow.service;

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
import org.springframework.util.Base64Utils;

import com.xyj.oa.api.exception.ApiException;
import com.xyj.oa.api.vo.APIErrorCode;
import com.xyj.oa.api.vo.JsonMapper;
import com.xyj.oa.api.vo.WorkFlowInstanceVo;
import com.xyj.oa.auth.OaShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.staff.repository.StaffDao;
import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.workflow.entity.BusinessData;
import com.xyj.oa.workflow.entity.WorkFlow;
import com.xyj.oa.workflow.entity.WorkFlowInstance;
import com.xyj.oa.workflow.entity.WorkFlowPointInfo;
import com.xyj.oa.workflow.entity.WorkFlowRule;
import com.xyj.oa.workflow.repository.WorkFlowInstanceDao;
import com.xyj.oa.workflow.repository.WorkFlowPointInfoDao;
import com.xyj.oa.workflow.repository.WorkFlowRuleDao;

import net.sf.json.JSONObject;

@Component
@Transactional
public class WorkFlowInstanceService {

	private static final String CONTRACTPOSTNAME = "公司管理员";

	private static final String CONTRACTDEPARTMENTNAME1 = "综合管理部";

	private static final String CONTRACTDEPARTMENTNAME2 = "计划财务部";

	private static final String CONTRACTDEPARTMENTNAME3 = "技术支持部";

	private static final String CONTRACTPOSTNAME1 = "部门经理";

	@Autowired
	private WorkFlowInstanceDao workFlowInstanceDao;

	@Autowired
	private WorkFlowPointInfoDao workFlowPointInfoDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private StaffDao staffDao;

	@Autowired
	WorkFlowRuleDao workFlowRuleDao;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private WorkFlowService workFlowService;

	@Autowired
	private SystemService systemService;

	public long findCountWithParams(String params, Integer searchType) {
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return 0;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).get();
		Staff staff = user.getStaff();
		String checkWfiIds = returnCheckWfiIds(staff);
		return workFlowInstanceDao.getCountWithParams(params, staff.getId(), checkWfiIds, searchType);
	}

	public List<WorkFlowInstance> findListWithParams(String params, int pageNo, int pageSize, String sidx, String sord, Integer searchType) {
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return null;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).get();
		Staff staff = user.getStaff();
		String checkWfiIds = returnCheckWfiIds(staff);
		return workFlowInstanceDao.getListWithParams(params, pageNo, pageSize, sidx, sord, staff.getId(), checkWfiIds, searchType);
	}

	public String findListForApi(String params, Integer searchType) throws ApiException {
		if (StringHelper.isEmpty(params))
			throw new ApiException(APIErrorCode.ParamError);
		params = new String(Base64Utils.decodeFromString(params));
		JSONObject object = JSONObject.fromObject(params);
		String userId = object.getString("userId");
		User user = userDao.findById(Long.valueOf(userId)).get();
		int pageNo = object.has("pageNo") ? object.getInt("pageNo") : 1;
		int pageSize = object.has("pageSize") ? object.getInt("pageSize") : 10000;
		if (user == null)
			throw new ApiException(APIErrorCode.UserNoExist);
		Staff staff = user.getStaff();
		String checkWfiIds = returnCheckWfiIds(staff);
		List<WorkFlowInstance> listWithParams = workFlowInstanceDao.getListWithParams(params, pageNo, pageSize, "", "", staff.getId(), checkWfiIds, searchType);
		Long count = workFlowInstanceDao.getCountWithParams(params, staff.getId(), checkWfiIds, searchType);
		WorkFlowInstanceVo vo = new WorkFlowInstanceVo(listWithParams, count.intValue());
		String json = JsonMapper.nonNullMapper().toJson(vo);
		return json;
	}

	private String returnCheckWfiIds(Staff staff) {
		List<Long> list = workFlowPointInfoDao.findCheckIdsByStaffId(staff.getId());
		return StringHelper.join(list, ",");
	}

	public WorkFlowInstance findWorkFlowInstanceById(Long instanceId) {
		return workFlowInstanceDao.findById(instanceId).orElse(null);
	}

	public Map<String, Object> getWorkflowInstanceInfo(Long instanceId) {
		Map<String, Object> result = new HashMap<>();
		if (instanceId == null || instanceId < 0)
			return null;
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(instanceId).orElse(null);
		if (workFlowInstance == null)
			return null;
		BusinessData businessData = workFlowInstance.getBusinessData();
		if (businessData == null)
			return null;
		if (workFlowInstance.getWorkFlow().getWorkFlowType().getId() == 3) {
			result = getWorkflowFileInstanceInfo(workFlowInstance);
			return result;
		}
		String dataInfo = businessData.getDataInfo();
		Long workflowTypeId = businessData.getWorkFlowType().getId();
		String name = "";
		JSONObject jsonObject = JSONObject.fromObject(dataInfo);
		if (workflowTypeId == 1)//加班类型
			name = systemService.getOverTimeTypeNameById(jsonObject.getLong("overtimeType"));
		else if (workflowTypeId == 4)//预算
			name = systemService.getCommissionerNameById(jsonObject.getLong("budgetPerson"));
		//		else if (workflowTypeId == 6)//用印
		//			name = systemService.getSealTypeNameById(jsonObject.getLong("useType"));
		else if (workflowTypeId == 7)//资产业务
			name = systemService.getAssetTypeNameById(jsonObject.getLong("bussinessType"));
		result.put("typeName", name);
		String attachmentPath = workFlowInstance.getAttachmentPath();//附件路径
		String attachmentName = workFlowInstance.getAttachmentName();
		if (workflowTypeId.equals(16L))
			result.put("pointNum", workFlowInstance.getPointNum());
		result.put("dataInfo", dataInfo);
		result.put("formId", workFlowInstance.getInstanceNum());
		result.put("wfId", workFlowInstance.getWorkFlow().getId());
		result.put("attachmentPath", attachmentPath);
		result.put("attachmentName", attachmentName);
		result.put("workflowTypeId", workflowTypeId);
		List<WorkFlowPointInfo> findAllWorkFlowPointInfo = workFlowPointInfoDao.findAllWorkFlowPointInfo(instanceId);
		List<Map<String, String>> pointInfoList = new ArrayList<>();
		Map<String, String> pointMap = null;
		if (CollectionUtils.isNotEmpty(findAllWorkFlowPointInfo)) {
			for (WorkFlowPointInfo workFlowPointInfo : findAllWorkFlowPointInfo) {
				pointMap = new HashMap<String, String>();
				pointMap.put("pointName", workFlowPointInfo.getPointName());//节点名称
				pointMap.put("staffName", //经办人
						!staffDao.findById(workFlowPointInfo.getApprovalStaffId()).isPresent() ? ""
								: staffDao.findById(workFlowPointInfo.getApprovalStaffId()).get().getStaffName());
				pointMap.put("taskReceiveTime", workFlowPointInfo.getTaskReceiveTime());//接收时间
				pointMap.put("taskCrossTime", workFlowPointInfo.getTaskCrossTime());//完成时间
				pointMap.put("approvalState", workFlowPointInfo.getApprovalState().toString());
				pointMap.put("reason", workFlowPointInfo.getReason() == null ? "" : workFlowPointInfo.getReason());//原因（意见）
				pointInfoList.add(pointMap);
			}
		}
		result.put("allPointInfo", pointInfoList);
		List<Map<String, Object>> returnCrossProcessRule = null;
		if (workflowTypeId == 16L)
			returnCrossProcessRule = returnFinancialNotesProcessRule(instanceId);
		else if (workflowTypeId == 8) {
			Staff staff = staffDao.findById(workFlowInstance.getStaffId()).get();
			Set<Post> posts = staff.getPosts();
			for (Post post : posts) {
				if (post.getPostName().equals(CONTRACTPOSTNAME) || !StringHelper.isEmpty(workFlowInstance.getReserve10()))
					returnCrossProcessRule = returnContractProcessRule(instanceId);
			}
			if (returnCrossProcessRule == null)
				returnCrossProcessRule = returnCrossProcessRule(instanceId);
		} else
			returnCrossProcessRule = returnCrossProcessRule(instanceId);
		if (CollectionUtils.isEmpty(returnCrossProcessRule)) {
			result.put("pointName", "您已经是最后的审批人员");
			result.put("nextApproval", null);
		} else {
			result.put("pointName", returnCrossProcessRule.get(0).get("pointName"));
			returnCrossProcessRule.remove(0);
			result.put("nextApproval", returnCrossProcessRule);
		}
		return result;
	}

	private Map<String, Object> getWorkflowFileInstanceInfo(WorkFlowInstance workFlowInstance) {
		Map<String, Object> result = new HashMap<>();
		Long instanceId = workFlowInstance.getId();
		BusinessData businessData = workFlowInstance.getBusinessData();
		String dataInfo = businessData.getDataInfo();
		Long workflowTypeId = businessData.getWorkFlowType().getId();
		String attachmentPath = workFlowInstance.getAttachmentPath();//附件路径
		String attachmentName = workFlowInstance.getAttachmentName();
		result.put("dataInfo", dataInfo);
		result.put("wfId", workFlowInstance.getWorkFlow().getId());
		result.put("attachmentPath", attachmentPath);
		result.put("attachmentName", attachmentName);
		result.put("workflowTypeId", workflowTypeId);
		result.put("formId", workFlowInstance.getInstanceNum());
		List<WorkFlowPointInfo> findAllWorkFlowPointInfo = workFlowPointInfoDao.findAllWorkFlowPointInfo(instanceId);
		List<Map<String, String>> pointInfoList = new ArrayList<>();
		Map<String, String> pointMap = null;
		if (CollectionUtils.isNotEmpty(findAllWorkFlowPointInfo)) {
			for (WorkFlowPointInfo workFlowPointInfo : findAllWorkFlowPointInfo) {
				pointMap = new HashMap<String, String>();
				pointMap.put("pointName", workFlowPointInfo.getPointName());//节点名称
				pointMap.put("staffName", //经办人
						!staffDao.findById(workFlowPointInfo.getApprovalStaffId()).isPresent() ? ""
								: staffDao.findById(workFlowPointInfo.getApprovalStaffId()).get().getStaffName());
				pointMap.put("taskReceiveTime", workFlowPointInfo.getTaskReceiveTime());//接收时间
				pointMap.put("taskCrossTime", workFlowPointInfo.getTaskCrossTime());//完成时间
				pointMap.put("approvalState", workFlowPointInfo.getApprovalState().toString());
				pointMap.put("reason", workFlowPointInfo.getReason() == null ? "" : workFlowPointInfo.getReason());//原因（意见）
				pointInfoList.add(pointMap);
			}
		}
		result.put("allPointInfo", pointInfoList);
		List<Map<String, Object>> returnCrossProcessRule = returnCrossFileProcessRule(workFlowInstance);
		if (CollectionUtils.isEmpty(returnCrossProcessRule)) {
			result.put("pointName", "您已经是最后的审批人员");
			result.put("nextApproval", null);
		} else {
			result.put("pointName", returnCrossProcessRule.get(0).get("pointName"));
			returnCrossProcessRule.remove(0);
			result.put("nextApproval", returnCrossProcessRule);
		}
		return result;
	}

	private List<Map<String, Object>> returnCrossFileProcessRule(WorkFlowInstance workFlowInstance) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		int pointNum = workFlowInstance.getPointNum();
		String pointName = workFlowInstance.getPointName();
		Map<String, Object> firstMap = new HashMap<>();
		if (pointNum == 1) {
			firstMap.put("pointName", WorkFlowService.FILEPOSTNAME1);
			returnList.add(firstMap);
			//			Set<Staff> managerStaffs = staffDao.findStaffByPost(WorkFlowService.FILEPOSTNAME1);
			//			for (Staff s : managerStaffs) {
			//				Map<String, Object> maps = new HashMap<>();
			//				maps.put("value", s.getId());
			//				maps.put("text", s.getStaffName());
			//				returnList.add(maps);
			//			}
		} else if (pointNum == 2) {
			firstMap.put("pointName", WorkFlowService.FILEPOSTNAME2 + "#" + WorkFlowService.FILEPOSTNAME3);
			returnList.add(firstMap);
		} else if ((pointNum == 3 || pointNum == 4 || pointNum == 5 || pointNum == 6 || pointNum == 7 || pointNum == 8 || pointNum == 9)
				&& pointName.equals(WorkFlowService.FILEPOSTNAME2)) {
			firstMap.put("pointName", WorkFlowService.FILEPOSTNAME3);
			returnList.add(firstMap);
			Map<String, Object> map = new HashMap<>();
			Set<Staff> managerStaffs = new HashSet<>();
			String[] managerArr = new String[] { "总经理", "副总经理" };
			for (int i = 0; i < managerArr.length; i++) {
				Set<Staff> findStaffByPost = staffDao.findStaffByPost(managerArr[i]);
				managerStaffs.addAll(findStaffByPost);
			}
			StringBuilder staffIds = new StringBuilder();
			StringBuilder staffNames = new StringBuilder();
			for (Staff s : managerStaffs) {
				staffIds.append(",").append(s.getId());
				staffNames.append(",").append(s.getStaffName());
			}
			map.put("value", staffIds.substring(1).toString());
			map.put("text", staffNames.substring(1).toString());
			returnList.add(map);
		} else if ((pointNum == 3 || pointNum == 4 || pointNum == 5 || pointNum == 6 || pointNum == 7 || pointNum == 8 || pointNum == 9)
				&& pointName.equals(WorkFlowService.FILEPOSTNAME3)) {
			firstMap.put("pointName", WorkFlowService.FILEPOSTNAME1);
			returnList.add(firstMap);
			Set<Staff> managerStaffs = staffDao.findStaffByPost(WorkFlowService.FILEPOSTNAME1);
			for (Staff s : managerStaffs) {
				Map<String, Object> maps = new HashMap<>();
				maps.put("value", s.getId());
				maps.put("text", s.getStaffName());
				returnList.add(maps);
			}
		} else if ((pointNum == 6 || pointNum == 10 || pointNum == 7 || pointNum == 9 || pointNum == 5 || pointNum == 8)
				&& pointName.equals(WorkFlowService.FILEPOSTNAME1) && StringHelper.isNotEmpty(workFlowInstance.getReserve19())
				&& Integer.valueOf(workFlowInstance.getReserve19()) == 1) {
			firstMap.put("pointName", WorkFlowService.FILEPOSTNAME2 + "#" + WorkFlowService.FILEPOSTNAME4 + "#" + WorkFlowService.ENDINSTANCE);
			returnList.add(firstMap);
		} else if (!StringHelper.isEmpty(pointName) && (pointName.equals(WorkFlowService.FILEPOSTNAME4) || pointName.equals(WorkFlowService.FILEPOSTNAME2))) {
			firstMap.put("pointName", WorkFlowService.FILEPOSTNAME1);
			returnList.add(firstMap);
			Set<Staff> managerStaffs = staffDao.findStaffByPost(WorkFlowService.FILEPOSTNAME1);
			for (Staff s : managerStaffs) {
				Map<String, Object> maps = new HashMap<>();
				maps.put("value", s.getId());
				maps.put("text", s.getStaffName());
				returnList.add(maps);
			}
		} else if (pointNum == 0) {
			firstMap.put("pointName", WorkFlowService.FILEPOSTNAME);
			returnList.add(firstMap);
			Set<Staff> managerStaffs = staffDao.findStaffByPost(WorkFlowService.FILEPOSTNAME);
			for (Staff s : managerStaffs) {
				Map<String, Object> maps = new HashMap<>();
				maps.put("value", s.getId());
				maps.put("text", s.getStaffName());
				returnList.add(maps);
			}
		}
		return returnList;
	}

	private List<Map<String, Object>> returnContractProcessRule(Long instanceId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(instanceId).get();
		StringBuilder sb = new StringBuilder();
		Set<Staff> managerStaffs = new HashSet<>();
		Staff staff = null;
		if (StringHelper.isEmpty(workFlowInstance.getReserve10())) {
			staff = staffDao.findStaffByDeptAndPost(CONTRACTDEPARTMENTNAME1, CONTRACTPOSTNAME1);
			Staff staff2 = staffDao.findStaffByDeptAndPost(CONTRACTDEPARTMENTNAME2, CONTRACTPOSTNAME1);
			Staff staff3 = staffDao.findStaffByDeptAndPost(CONTRACTDEPARTMENTNAME3, CONTRACTPOSTNAME1);
			sb.append(staff2.getId()).append(",").append(staff3.getId());
			workFlowInstance.setReserve10(sb.toString());
		} else {
			String reserve10 = workFlowInstance.getReserve10();
			String[] staffIdsArr = reserve10.split(",");
			staff = staffDao.findById(Long.valueOf(staffIdsArr[0])).get();
			workFlowInstance.setReserve10(staffIdsArr.length >= 2 ? staffIdsArr[1] : null);
		}
		workFlowInstanceDao.save(workFlowInstance);
		managerStaffs.add(staff);
		List<Map<String, Object>> returnList = new ArrayList<>();
		Map<String, Object> firstMap = new HashMap<>();
		firstMap.put("pointName", "相关部门");
		returnList.add(firstMap);
		for (Staff s : managerStaffs) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("value", s.getId());
			maps.put("text", s.getStaffName());
			returnList.add(maps);
		}
		return returnList;
	}

	private List<Map<String, Object>> returnCrossProcessRule(Long workFlowInstanceId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(workFlowInstanceId).get();
		WorkFlow workFlow = workFlowInstance.getWorkFlow();
		WorkFlowRule workFlowRule = workFlowRuleDao.findWorkFlowRuleByWFAndNum(workFlow.getId(), workFlowInstance.getPointNum() + 1);
		if (workFlowRule == null)
			return null;
		String staffIds = workFlowRule.getStaffIds();
		String occupationIds = workFlowRule.getOccupationIds();
		String postIds = workFlowRule.getPostIds();
		List<Long> deptIds = new ArrayList<>();
		Long staffId = workFlowInstance.getStaffId();
		Staff staff = staffDao.findById(staffId).get();
		Set<Staff> managerStaffs = null;
		if (StringHelper.isEmpty(staffIds)) {
			workFlowService.findSecondDeptChildIds(workFlowService.findSecondDepartMent(staff.getDepartment(), null), deptIds);
			managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, StringHelper.join(deptIds, ","));
		} else
			managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, null);
		List<Map<String, Object>> returnList = new ArrayList<>();
		Map<String, Object> firstMap = new HashMap<>();
		firstMap.put("pointName", workFlowRule.getPointName());
		returnList.add(firstMap);
		for (Staff s : managerStaffs) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("value", s.getId());
			maps.put("text", s.getStaffName());
			returnList.add(maps);
		}
		return returnList;
	}

	public List<Map<String, Object>> returnFinancialNotesProcessRule(Long workFlowInstanceId) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findById(workFlowInstanceId).get();
		WorkFlow workFlow = workFlowInstance.getWorkFlow();
		WorkFlowRule workFlowRule = workFlowRuleDao.findWorkFlowRuleByWFAndNum(workFlow.getId(), workFlowInstance.getPointNum() + 1);
		if (workFlowRule == null)
			return null;
		String staffIds = workFlowRule.getStaffIds();
		String occupationIds = workFlowRule.getOccupationIds();
		String postIds = workFlowRule.getPostIds();
		List<Long> deptIds = new ArrayList<>();
		Long staffId = workFlowInstance.getStaffId();
		Staff staff = staffDao.findById(staffId).get();
		Set<Staff> managerStaffs = null;
		if (StringHelper.isNotEmpty(workFlowInstance.getReserve10())) {
			String reserve10 = workFlowInstance.getReserve10();
			String[] idsArr = reserve10.split(",");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < idsArr.length; i++) {
				Staff managerStaff = staffDao.findById(Long.valueOf(idsArr[i])).get();
				managerStaffs = new HashSet<>();
				managerStaffs.add(managerStaff);
				if (i == 0)
					continue;
				sb.append(",").append(idsArr[i]);
			}
			workFlowInstance.setReserve10(sb == null ? null : sb.substring(1).toString());
			workFlowInstanceDao.save(workFlowInstance);
		} else {
			if (StringHelper.isEmpty(staffIds)) {
				workFlowService.findSecondDeptChildIds(workFlowService.findSecondDepartMent(staff.getDepartment(), null), deptIds);
				managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, StringHelper.join(deptIds, ","));
			} else
				managerStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, postIds, occupationIds, null);
		}
		List<Map<String, Object>> returnList = new ArrayList<>();
		Map<String, Object> firstMap = new HashMap<>();
		firstMap.put("pointName", workFlowRule.getPointName());
		returnList.add(firstMap);
		for (Staff s : managerStaffs) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("value", s.getId());
			maps.put("text", s.getStaffName());
			returnList.add(maps);
		}
		return returnList;
	}

	public Map<String, Object> getContractForm(String contractNo) {
		WorkFlowInstance workFlowInstance = workFlowInstanceDao.findByReserve2(contractNo);
		if (workFlowInstance != null) {
			BusinessData businessData = workFlowInstance.getBusinessData();
			JSONObject fromObject = JSONObject.fromObject(businessData.getDataInfo());
			Map<String, Object> result = new HashMap<>();
			String contractBeginDate = fromObject.getString("contractBeginDate");
			String contractEndDate = fromObject.getString("contractEndDate");
			String contractName = fromObject.getString("contractName");
			String contractPriceTotal = fromObject.getString("contractPriceTotal");
			String taxRate = fromObject.getString("taxRate");
			String price = fromObject.getString("price");
			String tax = fromObject.getString("tax");
			result.put("beginDate", contractBeginDate);
			result.put("endDate", contractEndDate);
			result.put("contractContent", contractName);
			result.put("contractPrice", contractPriceTotal);
			result.put("contractRate", taxRate.substring(0, taxRate.length() - 1));
			result.put("priceRate", price);
			result.put("contractMoney", tax);
			return result;
		}
		return null;
	}

}
