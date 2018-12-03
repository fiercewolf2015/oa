package com.xyj.oa.finance.web;

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

import com.xyj.oa.finance.service.BudgetModelService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "budgetModel")
public class BudgetModelController {

	private static Logger logger = LoggerFactory.getLogger(BudgetModelController.class);

	@Autowired
	private BudgetModelService budgetModelService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "budget/budgetModel";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, budgetModelService.findBudgetModelCountWithParams(params),
					budgetModelService.findBudgetModelListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addModel(@RequestParam(value = "name") String name, @RequestParam(value = "remark") String remark,
			@RequestParam(value = "subIds") String subIds, @RequestParam(value = "id") String id) {
		int result = 0;
		try {
			result = budgetModelService.saveBudgetModel(name, remark, subIds,id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteModel(@RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = budgetModelService.deleteModel(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/getAllSubject")
	@ResponseBody
	public List<Map<String, Object>> getAllSubject(@RequestParam(value = "id") Long id) {
		List<Map<String, Object>> result = null;
		try {
			result = budgetModelService.getAllSubject(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
}
