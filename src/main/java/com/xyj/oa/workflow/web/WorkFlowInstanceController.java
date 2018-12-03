package com.xyj.oa.workflow.web;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;
import com.xyj.oa.workflow.service.WorkFlowInstanceService;
import com.xyj.oa.workflow.service.WorkFlowService;

@Controller
@RequestMapping(value = "workflowInstance")
public class WorkFlowInstanceController {

	private static Logger logger = LoggerFactory.getLogger(WorkFlowInstanceController.class);

	@Autowired
	private WorkFlowInstanceService workFlowInstanceService;

	@Autowired
	private WorkFlowService workFlowService;

	@RequestMapping(value = "/todolist", method = RequestMethod.GET)
	public String getTodolist() {
		return "progress/todolist";
	}

	@RequestMapping(value = "/donelist", method = RequestMethod.GET)
	public String getDonelist() {
		return "progress/donelist";
	}

	@RequestMapping(value = "/historylist", method = RequestMethod.GET)
	public String gethistorylist() {
		return "progress/historylist";
	}

	@RequestMapping(value = "/allProcess", method = RequestMethod.GET)
	public String getallProcess() {
		return "progress/allProcess";
	}

	@RequestMapping(value = "/todolist")
	@ResponseBody
	public Map<String, Object> todolist(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, workFlowInstanceService.findCountWithParams(params, 1),
					workFlowInstanceService.findListWithParams(params, pageNo, pageSize, sidx, sord, 1));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/donelist")
	@ResponseBody
	public Map<String, Object> donelist(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, workFlowInstanceService.findCountWithParams(params, 2),
					workFlowInstanceService.findListWithParams(params, pageNo, pageSize, sidx, sord, 2));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/historylist")
	@ResponseBody
	public Map<String, Object> historylist(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, workFlowInstanceService.findCountWithParams(params, 3),
					workFlowInstanceService.findListWithParams(params, pageNo, pageSize, sidx, sord, 3));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/allProcess")
	@ResponseBody
	public Map<String, Object> allProcess(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, workFlowInstanceService.findCountWithParams(params, 4),
					workFlowInstanceService.findListWithParams(params, pageNo, pageSize, sidx, sord, 4));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/getWorkflowInstanceInfo")
	@ResponseBody
	public Map<String, Object> getWorkflowInstanceInfo(@RequestParam(value = "instanceId") Long instanceId) {
		Map<String, Object> result = null;
		try {
			result = workFlowInstanceService.getWorkflowInstanceInfo(instanceId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return null;
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/agree")
	@ResponseBody
	public int agreeWorkflowInstanceInfo(@RequestParam(value = "crossReason") String crossReason, @RequestParam(value = "managerStaffId") Long managerStaffId,
			@RequestParam(value = "instanceId") Long instanceId, @RequestParam(value = "nextDeptStaffIds") Long[] nextDeptStaffIds,
			@RequestParam(value = "fileName") String fileName, HttpServletRequest request) {
		int result = 0;
		try {
			String filePath = "";
			if (StringHelper.isNotEmpty(fileName)) {
				String projectRealPath = request.getRealPath("/");
				filePath = projectRealPath + "static" + File.separator + "file_temp" + File.separator;
			}
			if (nextDeptStaffIds == null || nextDeptStaffIds.length <= 0)
				result = workFlowService.crossInstance(crossReason, managerStaffId, instanceId, null, fileName, filePath);
			else
				result = workFlowService.crossFinancialNotesInstance(crossReason, managerStaffId, instanceId, nextDeptStaffIds, fileName, filePath);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
		return result;
	}

//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/process")
//	@ResponseBody
//	public int processNextPoint(@RequestParam(value = "instanceId") Long instanceId, @RequestParam(value = "type") Long typeId, HttpServletRequest request) {
//		int result = 0;
//		try {
//			if (typeId == 3 || typeId == 4)
//				return -99;
//			WorkFlowInstance workFlowInstance = workFlowInstanceService.findWorkFlowInstanceById(instanceId);
//			if (workFlowInstance.getIfreject() == 1)
//				return -100;
//			List<Map<String, Object>> object = null;
//			if (workFlowInstance.getPointNum() == 1)
//				object = workFlowService.returnProcessRule(null, workFlowInstance.getWorkFlow().getId(), null);
//			else {
//				Map<String, Object> workflowInstanceInfo = workFlowInstanceService.getWorkflowInstanceInfo(instanceId);
//				object = (List<Map<String, Object>>) workflowInstanceInfo.get("nextApproval");
//			}
//			Long managerStaffId = null;
//			if (object != null && object.size() > 0) {
//				Map<String, Object> map = object.get(0);
//				Collection<Object> values = map.values();
//				int i = 0;
//				for (Object obj : values) {
//					if (i == values.size() - 1)
//						managerStaffId = Long.valueOf(obj.toString());
//					i++;
//				}
//				result = workFlowService.crossInstance("流程自动流转到下一节点", managerStaffId, instanceId, null, null, null);
//			}
//		} catch (Exception e) {
//			logger.error(LogUtil.stackTraceToString(e));
//			return 0;
//		}
//		return result;
//	}

	@RequestMapping(value = "/agreeFile")
	@ResponseBody
	public int agreeFileWorkflowInstanceInfo(@RequestParam(value = "crossReason") String crossReason,
			@RequestParam(value = "managerStaffId") String managerStaffId, @RequestParam(value = "deptNames") String deptNames,
			@RequestParam(value = "instanceId") Long instanceId, @RequestParam(value = "endInstance") String endInstance) {
		int result = 0;
		try {
			if (StringHelper.isNotEmpty(endInstance) && endInstance.equals(WorkFlowService.ENDINSTANCE)) {
				result = workFlowService.endInstance(instanceId, crossReason);
			} else
				result = workFlowService.crossFileInstance(crossReason, managerStaffId, instanceId, deptNames);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
		return result;
	}

	@RequestMapping(value = "/reject")
	@ResponseBody
	public int rejectWorkflowInstanceInfo(@RequestParam(value = "rejectReason") String rejectReason, @RequestParam(value = "instanceId") Long instanceId) {
		int result = 0;
		try {
			result = workFlowService.rejectInstance(rejectReason, instanceId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
		return result;
	}

	@RequestMapping(value = "/rejectFinancial")
	@ResponseBody
	public int rejectWorkflowInstanceFinancialInfo(@RequestParam(value = "rejectReason") String rejectReason,
			@RequestParam(value = "instanceId") Long instanceId, @RequestParam(value = "managerStaffId") Long managerStaffId) {
		int result = 0;
		try {
			result = workFlowService.rejectFinancialNotesInstance(rejectReason, instanceId, managerStaffId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
		return result;
	}

	@RequestMapping(value = "/revoke")
	@ResponseBody
	public int revokeWorkflowInstanceInfo(@RequestParam(value = "instanceId") Long instanceId) {
		int result = 0;
		try {
			result = workFlowService.deleteRejectInstance(instanceId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
		return result;
	}

	@RequestMapping(value = "/getFileManagerStaff")
	@ResponseBody
	public List<Map<String, Object>> getFileManagerStaff(@RequestParam(value = "postName") String postName) {
		try {
			return workFlowService.getFileManagerStaff(postName);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return null;
		}
	}

	@RequestMapping(value = "/getFileManagerStaff2")
	@ResponseBody
	public List<Map<String, Object>> getFileManagerStaff2(@RequestParam(value = "postName") String postName) {
		try {
			return workFlowService.getFileManagerStaff2(postName);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return null;
		}
	}

	@RequestMapping(value = "/getContractForm")
	@ResponseBody
	public Map<String, Object> getContractForm(@RequestParam(value = "contractNo") String contractNo) {
		try {
			return workFlowInstanceService.getContractForm(contractNo);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return null;
		}
	}

}
