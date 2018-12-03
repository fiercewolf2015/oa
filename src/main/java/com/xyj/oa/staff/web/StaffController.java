package com.xyj.oa.staff.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;

import com.xyj.oa.auth.OaShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.log.annotation.ActionLog;
import com.xyj.oa.log.annotation.ConditionColumn;
import com.xyj.oa.log.annotation.WithArgment;
import com.xyj.oa.log.annotation.WithArgments;
import com.xyj.oa.log.annotation.WithColumn;
import com.xyj.oa.log.annotation.WithTable;
import com.xyj.oa.log.constants.LogConstants;
import com.xyj.oa.occupation.web.OccupationController;
import com.xyj.oa.staff.service.StaffService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;
import com.xyj.oa.webservice.GetStaffAndPostWebService;

@Controller
@RequestMapping(value = "staff")
public class StaffController {

	private static Logger logger = LoggerFactory.getLogger(OccupationController.class);

	@Autowired
	private StaffService staffService;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private GetStaffAndPostWebService getStaffAndPostWebService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "staff/staff";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, staffService.findCountWithParams(params),
					staffService.findListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@ActionLog(actionType = "新增/编辑", bizType = "员工管理", expectedExp = "returnValue == 1")
	@WithArgments(withArgments = { @WithArgment(text = "员工姓名", argExpress = "staffName", argIndex = 0) })
	@RequestMapping(value = "/save")
	@ResponseBody
	public int addStaff(@RequestParam(value = "params") String params, @RequestParam(value = "selPostIds") String selPostIds) {
		int result = 0;
		try {
			result = staffService.saveStaff(params, selPostIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}

	@ActionLog(actionType = "删除员工", bizType = "员工管理", expectedExp = "returnValue == 1")
	@WithTable(name = "Staff", withColumns = { @WithColumn(text = "员工姓名", name = "staffName"), @WithColumn(text = "员工编号", name = "staffNo") }, conditionColumns = { @ConditionColumn(argIndex = 0, name = "id", operator = LogConstants.INLIST) })
	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteStaff(@RequestParam(value = "staffIds") String staffIds) {
		int result = 0;
		try {
			result = staffService.deleteStaffs(staffIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/importStaff")
	@ResponseBody
	public String importStaff(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
		String result = "error";
		InputStream is = null;
		FileOutputStream foutput = null;
		try {
			if (file != null && !file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				is = file.getInputStream();
				result = staffService.importStaff(is, fileName);
				if (StringHelper.isNotEmpty(result))
					throw new Exception(result);
			}
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		} finally {
			try {
				if (is != null)
					is.close();
				if (foutput != null)
					foutput.close();
			} catch (IOException e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}
		return result;
	}

	@RequestMapping(value = "/getAllWorkflow")
	@ResponseBody
	public List<Map<String, Object>> getAllWorkFlowByUser() {
		List<Map<String, Object>> result = null;
		try {
			ShiroUser currentUser = shiroManager.getCurrentUser();
			if (currentUser == null)
				return result;
			Long userId = currentUser.getId();
			if (userId == null || userId <= 0)
				return result;
			result = staffService.findAllWorkflowTypeByUserId(userId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/syncData")
	@ResponseBody
	public int syncData() {
		int result = 0;
		try {
			result = getStaffAndPostWebService.saveOrUpdateDataFromGold();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
