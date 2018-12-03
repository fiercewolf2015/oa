package com.xyj.oa.finance.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.finance.service.FinanceApprovalService;
import com.xyj.oa.finance.service.FinanceService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "budgetApproval")
public class BudgetApprovalController {

	private static Logger logger = LoggerFactory.getLogger(BudgetApprovalController.class);

	@Autowired
	private FinanceService financeService;

	@Autowired
	private FinanceApprovalService financeApprovalService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "budget/budgetApproval";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, financeApprovalService.findBudgetApprovalCountWithParams(params),
					financeApprovalService.findBudgetApprovalListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/listForSimple")
	@ResponseBody
	public Map<String, Object> listForSimple(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return financeApprovalService.getListForSimple(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/allDepartment")
	@ResponseBody
	public Map<String, Object> getAllDepartment(@RequestParam(value = "id") Long parentDepartmentId,@RequestParam(value = "year") String year) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			return financeApprovalService.getAllDepartment(parentDepartmentId,year);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/differentLevel")
	@ResponseBody
	public Map<String, Object> getDifferentLevel(@RequestParam(value = "id") Long id,@RequestParam(value = "year") String year,@RequestParam(value = "level") int level) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			return financeApprovalService.getDifferentLevel(id,year,level);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/reject")
	@ResponseBody
	public int reject(@RequestParam(value = "id") Long id,@RequestParam(value = "reason") String reason) {
		int result = 0;
		try {
			result = financeApprovalService.saveApprovalFinance(id,reason);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
}
