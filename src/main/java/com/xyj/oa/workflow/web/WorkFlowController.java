package com.xyj.oa.workflow.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xyj.oa.log.annotation.ActionLog;
import com.xyj.oa.log.annotation.ConditionColumn;
import com.xyj.oa.log.annotation.WithArgment;
import com.xyj.oa.log.annotation.WithArgments;
import com.xyj.oa.log.annotation.WithColumn;
import com.xyj.oa.log.annotation.WithTable;
import com.xyj.oa.log.constants.LogConstants;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;
import com.xyj.oa.workflow.service.WorkFlowService;

@Controller
@RequestMapping(value = "workflow")
public class WorkFlowController {

	private static Logger logger = LoggerFactory.getLogger(WorkFlowController.class);

	public static final int MAX_ATTACH_SIZE = 31457280;

	@Autowired
	private WorkFlowService workFlowService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "progress/workflow";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, workFlowService.findCountWithParams(params),
					workFlowService.findListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@ActionLog(actionType = "新增/编辑", bizType = "自定义流程", expectedExp = "returnValue == 1")
	@WithArgments(withArgments = { @WithArgment(text = "流程名称", argIndex = 1) })
	@WithTable(name = "WorkFlowType", withColumns = { @WithColumn(text = "流程类型", name = "name") }, conditionColumns = {
			@ConditionColumn(argIndex = 2, name = "id", operator = LogConstants.EQUALS) })
	@RequestMapping(value = "/add")
	@ResponseBody
	public int addWorkflow(@RequestParam(value = "params") String params, @RequestParam(value = "wfName") String wfName,
			@RequestParam(value = "workTypeId") Long workTypeId, @RequestParam(value = "saveOrUpdate") Long wfId) {
		int result = 0;
		if (StringHelper.isEmpty(params))
			return result;
		try {
			String[] split = params.split("#####");
			if (wfId.longValue() == 0)//新增
				result = workFlowService.saveInstance(split, wfName, workTypeId);
			else
				result = workFlowService.updateInstance(split, wfName, wfId, workTypeId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@ActionLog(actionType = "删除流程", bizType = "自定义流程", expectedExp = "returnValue == 1")
	@WithTable(name = "WorkFlow", withColumns = { @WithColumn(text = "流程名称", name = "name") }, conditionColumns = {
			@ConditionColumn(argIndex = 0, name = "id", operator = LogConstants.INLIST) })
	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteWorkflow(@RequestParam(value = "workflowIds") String workflowIds) {
		int result = 0;
		if (StringHelper.isEmpty(workflowIds))
			return result;
		try {
			result = workFlowService.deleteInstance(workflowIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/getAllApprovalStaffs")
	@ResponseBody
	public List<Map<String, Object>> getAllApprovalStaffs(@RequestParam(value = "subStaffId") Long subStaffId,
			@RequestParam(value = "workflowId") Long workflowId, @RequestParam(required = false, value = "dId") Long departmentId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = workFlowService.returnProcessRule(subStaffId, workflowId, departmentId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/getSystemInfo")
	@ResponseBody
	public String[] getSystemInfo(@RequestParam(value = "workflowId") Long workflowId) {
		String[] result = null;
		try {
			result = workFlowService.returnSystemInfo(workflowId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/getAllWorkflowType")
	@ResponseBody
	public List<Map<String, Object>> getAllWorkflowType(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		return workFlowService.getAllWorkflowType();
	}

	@RequestMapping(value = "/getFirstManagerStaff")
	@ResponseBody
	public Map<String, Object> getFirstManagerStaff(@RequestParam(value = "deptName") String deptName, @RequestParam(value = "ifChecked") boolean ifChecked) {
		Map<String, Object> result = null;
		try {
			result = workFlowService.firstManagerStaff(deptName);
			if (result != null)
				result.put("ifChecked", ifChecked);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/getAllrejectStaffForFinancialNotes")
	@ResponseBody
	public List<Map<String, Object>> getAllrejectStaffForFinancialNotes(@RequestParam(value = "wfId") Long wfId) {
		List<Map<String, Object>> result = null;
		try {
			result = workFlowService.getAllrejectStaffForFinancialNotes(wfId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/getWorkflowInfo")
	@ResponseBody
	public String[] getWorkfowInfo(@RequestParam(value = "wfId") Long wfId) {
		String[] result = null;
		try {
			result = workFlowService.returnWorkFlowPointInfo(wfId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/startInstance")
	@SuppressWarnings("deprecation")
	@ResponseBody
	public int addWorkflow(@RequestParam(value = "params") String params, @RequestParam(value = "workflowId") Long workflowId,
			@RequestParam(value = "subStaffId") Long subStaffId, @RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "managerStaffId") Long managerStaffId, HttpServletRequest request) {
		int result = 0;
		try {
			String filePath = "";
			if (StringHelper.isNotEmpty(fileName)) {
				String projectRealPath = request.getRealPath("/");
				filePath = projectRealPath + "static" + File.separator + "file_temp" + File.separator;
			}
			result = workFlowService.startInstance(params, workflowId, null, fileName.trim(), filePath, managerStaffId, null, null);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/startFileInstance")
	@SuppressWarnings("deprecation")
	@ResponseBody
	public int startFileInstance(@RequestParam(value = "params") String params, @RequestParam(value = "workflowId") Long workflowId,
			@RequestParam(value = "subStaffId") Long subStaffId, @RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "managerStaffId") Long managerStaffId, HttpServletRequest request) {
		int result = 0;
		try {
			String filePath = "";
			if (StringHelper.isNotEmpty(fileName)) {
				String projectRealPath = request.getRealPath("/");
				filePath = projectRealPath + "static" + File.separator + "file_temp" + File.separator;
			}
			result = workFlowService.startFileInstance(params, workflowId, null, fileName.trim(), filePath, managerStaffId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/reloadInstance")
	@ResponseBody
	public int reloadInstance(@RequestParam(value = "params") String params, @RequestParam(value = "wfiId") Long wfiId,
			@RequestParam(value = "subStaffId") Long subStaffId, @RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "managerStaffId") Long managerStaffId, HttpServletRequest request) {
		int result = 0;
		try {
			String filePath = "";
			if (StringHelper.isNotEmpty(fileName)) {
				String projectRealPath = request.getRealPath("/");
				filePath = projectRealPath + "static" + File.separator + "file_temp" + File.separator;
			}
			result = workFlowService.reloadInstance(params, wfiId, subStaffId, fileName, filePath, managerStaffId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/reloadFileInstance")
	@ResponseBody
	public int reloadFileInstance(@RequestParam(value = "params") String params, @RequestParam(value = "wfiId") Long wfiId,
			@RequestParam(value = "subStaffId") Long subStaffId, @RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "managerStaffId") Long managerStaffId, HttpServletRequest request) {
		int result = 0;
		try {
			String filePath = "";
			if (StringHelper.isNotEmpty(fileName)) {
				String projectRealPath = request.getRealPath("/");
				filePath = projectRealPath + "static" + File.separator + "file_temp" + File.separator;
			}
			result = workFlowService.reloadFileInstance(params, wfiId, subStaffId, fileName, filePath, managerStaffId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
		return result;
	}

	@RequestMapping(value = "/uploadFile")
	@SuppressWarnings("deprecation")
	@ResponseBody
	public int uploadFile(@RequestParam(value = "file") CommonsMultipartFile files[], @RequestParam(value = "fileName") String fileName,
			HttpServletRequest request) {
		int result = 1;
		FileOutputStream fop = null;
		InputStream is = null;
		try {
			fileName = fileName.substring(2, fileName.length());
			int available = 0;
			for (int i = 0; i < files.length; i++)
				available += files[i].getInputStream().available();
			if (available > 31457280)
				return -99;
			String projectRealPath = request.getRealPath("/");
			String[] fileNameArr = fileName.split("##");
			for (int i = 0; i < files.length; i++) {
				String filePath = projectRealPath + "static" + File.separator + "file_temp" + File.separator + fileNameArr[i].trim();
				File file = new File(filePath);
				fop = new FileOutputStream(file);
				is = files[i].getInputStream();
				byte[] bytes = new byte[is.available()];
				is.read(bytes);
				fop.write(bytes);
				fop.flush();
			}
		} catch (Exception e) {
			result = 0;
			logger.error(LogUtil.stackTraceToString(e));
		} finally {
			try {
				if (fop != null)
					fop.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}
		return result;
	}

	@RequestMapping(value = "/deleteFileForContract")
	@ResponseBody
	public int deleteFileForContract(@RequestParam(value = "wfiId") Long wfiId, @RequestParam(value = "fileName") String fileName) {
		int result = 0;
		if (wfiId == null)
			return result;
		try {
			result = workFlowService.uploadFileForContract(wfiId, fileName);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/editStartPerson")
	@ResponseBody
	public int editStartPerson(@RequestParam(value = "wfIds") String wfIds, @RequestParam(value = "staffIds") String staffIds,
			@RequestParam(value = "staffNames") String staffNames, @RequestParam(value = "postIds") String postIds,
			@RequestParam(value = "postNames") String postNames, @RequestParam(value = "occupationIds") String occupationIds,
			@RequestParam(value = "occupationNames") String occupationNames) {
		int result = 0;
		if (StringHelper.isEmpty(wfIds))
			return result;
		try {
			result = workFlowService.editStartPerson(wfIds, staffIds, staffNames, postIds, postNames, occupationIds, occupationNames);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping("/progress/{workflowId}/{htmlName}/{liId}")
	public String list(Model model, @PathVariable("workflowId") Long workflowId, @PathVariable("htmlName") String htmlName, @PathVariable("liId") String liId) {
		try {
			String workFowNameById = workFlowService.getWorkFowNameById(workflowId);
			model.addAttribute("workflowName", workFowNameById);
			model.addAttribute("liId", liId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return "progress/" + htmlName;
	}

	@RequestMapping(value = "/getWorkFlowData")
	@ResponseBody
	public List<String> getWorkFlowData(@RequestParam(value = "wId") Long wId) {
		List<String> result = null;
		try {
			result = workFlowService.getWorkFloaData(wId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return null;
		}
		return result;
	}

}
