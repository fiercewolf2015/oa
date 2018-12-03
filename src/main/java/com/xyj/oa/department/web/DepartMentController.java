package com.xyj.oa.department.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.department.service.DepartMentService;
import com.xyj.oa.util.LogUtil;

@Controller
@RequestMapping(value = "/department")
public class DepartMentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartMentController.class);

	@Autowired
	private DepartMentService departMentService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "corp/department";
	}

	@RequestMapping(value = "/loadDepartmentTree")
	@ResponseBody
	public List<Map<String, Object>> loadDepartmentTree() {
		try {
			return departMentService.loadDepartmentTree();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return new ArrayList<Map<String, Object>>();
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addDepartment(@RequestParam(value = "params") String params, @RequestParam(value = "pId") Long pId) {
		int result = 0;
		try {
			result = departMentService.addDepartment(params, pId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editDepartment(@RequestParam(value = "params") String params, @RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = departMentService.editDepartment(params, id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public int delDepartment(@RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = departMentService.delDepartment(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/loadSecondDepartmentTree")
	@ResponseBody
	public List<Map<String, Object>> loadSecondDepartmentTree() {
		try {
			return departMentService.loadSecondDepartmentTree();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return new ArrayList<Map<String, Object>>();
		}
	}

	@RequestMapping(value = "/loadThreeDepartmentTree")
	@ResponseBody
	public List<Map<String, Object>> loadThreeDepartmentTree(@RequestParam(value = "pId") Long pId) {
		try {
			return departMentService.loadThreeDepartmentTree(pId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return new ArrayList<Map<String, Object>>();
		}
	}

	@RequestMapping(value = "/getName")
	@ResponseBody
	public String getDepartmentNameById(@RequestParam(value = "dId") Long dId) {
		String result = "";
		try {
			result = departMentService.getDepartmentNameById(dId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
