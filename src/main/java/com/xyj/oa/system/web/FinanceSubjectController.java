package com.xyj.oa.system.web;

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

import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.util.LogUtil;

@Controller
@RequestMapping(value = "financeSubject")
public class FinanceSubjectController {

	private static Logger logger = LoggerFactory.getLogger(FinanceSubjectController.class);

	@Autowired
	private SystemService systemService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/financeSubject";
	}

	@RequestMapping(value = "/loadFinanceSubjectTree")
	@ResponseBody
	public List<Map<String, Object>> loadFinanceSubjectTree() {
		try {
			return systemService.loadFinanceSubjectTree();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return new ArrayList<Map<String, Object>>();
		}
	}
	
	@RequestMapping(value = "/loadSecondFinanceSubjectTree")
	@ResponseBody
	public List<Map<String, Object>> loadSecondFinanceSubjectTree() {
		try {
			return systemService.loadSecondFinanceSubjectTree();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return new ArrayList<Map<String, Object>>();
		}
	}
	
	@RequestMapping(value = "/loadThreeFinanceSubjectTree")
	@ResponseBody
	public List<Map<String, Object>> loadThreeFinanceSubjectTree(@RequestParam(value = "pId") Long pId) {
		try {
			return systemService.loadThreeFinanceSubjectTree(pId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return new ArrayList<Map<String, Object>>();
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addFinanceSubject(@RequestParam(value = "params") String params, @RequestParam(value = "pId") Long pId,
			@RequestParam(value = "postsIds") String postsIds) {
		int result = 0;
		try {
			result = systemService.saveFinanceSubject(params, pId,postsIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editFinanceSubject(@RequestParam(value = "params") String params, @RequestParam(value = "id") Long id,
			@RequestParam(value = "postsIds") String postsIds) {
		int result = 0;
		try {
			result = systemService.editFinanceSubject(params, id,postsIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public int delFinanceSubject(@RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = systemService.deleteFinanceSubject(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/balance")
	@ResponseBody
	public int getBalanceById(@RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = systemService.getBalanceById(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
}
