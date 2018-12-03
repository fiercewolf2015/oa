package com.xyj.oa.system.web;

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
@RequestMapping(value = "financeLine")
public class FinanceLineController {

	private static Logger logger = LoggerFactory.getLogger(FinanceLineController.class);

	@Autowired
	private SystemService systemService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/financeLine";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findFinanceLineCountWithParams(params),
					systemService.findFinanceLinesListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addFinanceLine(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			result = systemService.addFinanceLine(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteAssetType(@RequestParam(value = "financeLineId") Long financeLineId) {
		int result = 0;
		try {
			result = systemService.deleteFinanceLine(financeLineId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/getFinanceLine")
	@ResponseBody
	public Integer[] getFinanceLine() {
		Integer[] result = null;
		try {
			result = systemService.getFinanceLine();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
