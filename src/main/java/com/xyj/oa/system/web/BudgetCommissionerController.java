package com.xyj.oa.system.web;

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
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "budgetCommissioner")
public class BudgetCommissionerController {
	
	private static Logger logger = LoggerFactory.getLogger(BudgetCommissionerController.class);

	@Autowired
	private SystemService systemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/budgetcommissioner";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findBudgetCommissionerCountWithParams(params),
					systemService.findBudgetCommissionerListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public int addBudgetCommissioner(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			result = systemService.saveBudgetCommissioner(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editBudgetCommissioner(@RequestParam(value = "params") String params,@RequestParam(value = "budgetCommissionerId") Long budgetCommissionerId) {
		int result = 0;
		try {
			result = systemService.editBudgetCommissioner(params, budgetCommissionerId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteBudgetCommissioner(@RequestParam(value = "budgetCommissionerIds") String budgetCommissionerIds) {
		int result = 0;
		try {
			result = systemService.deleteBudgetCommissioner(budgetCommissionerIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/getAllBudgetCommissioners")
	@ResponseBody
	public List<Map<String, Object>> getAllBudgetCommissioners() {
		return systemService.getAllBudgetCommissioners();
	}
	
	@RequestMapping(value="/getName")
	@ResponseBody
	public String getCommissionerNameById(@RequestParam(value="id") Long id){
		String result = "";
		try {
			result = systemService.getCommissionerNameById(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
