package com.xyj.oa.finance.web;

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

import com.xyj.oa.finance.entity.FinanceOrganization;
import com.xyj.oa.finance.service.FinanceApprovalService;
import com.xyj.oa.finance.service.FinanceService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "budgeting")
public class BudgetingController {

	private static Logger logger = LoggerFactory.getLogger(BudgetingController.class);

	@Autowired
	private FinanceService financeService;

	@Autowired
	private FinanceApprovalService financeApprovalService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "budget/budgeting";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, financeService.findBudgetingCountWithParams(params,0),
					financeService.findBudgetingListWithParams(params, pageNo, pageSize, sidx, sord,0));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/listHistory")
	@ResponseBody
	public Map<String, Object> listHistory(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, financeService.findBudgetingCountWithParams(params,1),
					financeService.findBudgetingListWithParams(params, pageNo, pageSize, sidx, sord,1));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/allSubjectInfo")
	@ResponseBody
	public List<FinanceOrganization> getAllSubjectsInfo(@RequestParam(value = "id") Long appId) {
		List<FinanceOrganization> result = new ArrayList<>();
		try {
			if (appId == null || appId <= 0)
				return result;
			result = financeService.getAllSubjectsInfo(appId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addFinanceSubject(@RequestParam(value = "params") String params, @RequestParam(value = "financeName") String financeName,
			@RequestParam(value = "year") String year, @RequestParam(value = "departmentId") Long departmentId) {
		int result = 0;
		try {
			if (StringHelper.isEmpty(params) || StringHelper.isEmpty(financeName) || StringHelper.isEmpty(year))
				return result;
			String[] split = params.split("&&");
			if (split == null || split.length <= 0)
				return result;
			result = financeService.saveNewOrganization(split, financeName, year, departmentId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteBudgeting(@RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = financeService.deleteFinanceApproval(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/editOneSubject")
	@ResponseBody
	public int editOneSubject(@RequestParam(value = "params") String params, @RequestParam(value = "subjectId") Long subjectId) {
		int result = 0;
		try {
			result = financeService.editOneSubject(params, subjectId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/deleteOneSebject")
	@ResponseBody
	public int deleteOneSebject(@RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = financeService.deleteOneSebject(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/startWorkFlow")
	@ResponseBody
	public int startWorkFlow(@RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = financeApprovalService.saveApprovalFinance(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
