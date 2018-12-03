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
import com.xyj.oa.util.DateUtil;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "contractWillHistory")
public class ContractWillHistoryController {

	private static Logger logger = LoggerFactory.getLogger(ContractWillHistoryController.class);

	@Autowired
	private SystemService systemService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/contractWillFormForHistory";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findContractWillCountWithParams(params),
					systemService.findContractWillsListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addContractWill(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			result = systemService.addContractWill(params, 1, null, DateUtil.getCurDateStr());
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteContractWill(@RequestParam(value = "contractIds") String contractIds) {
		int result = 0;
		try {
			result = systemService.deleteContractWill(contractIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
