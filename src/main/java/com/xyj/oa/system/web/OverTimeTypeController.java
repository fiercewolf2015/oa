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
@RequestMapping(value = "overtimeType")
public class OverTimeTypeController {
	
	private static Logger logger = LoggerFactory.getLogger(OverTimeTypeController.class);

	@Autowired
	private SystemService systemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/overtime";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findOvertimeCountWithParams(params),
					systemService.findOvertimeListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public int addOvertimeType(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			result = systemService.saveOvertimeType(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editOvertimeType(@RequestParam(value = "params") String params,@RequestParam(value = "overtimeTypeId") Long overtimeTypeId) {
		int result = 0;
		try {
			result = systemService.editOvertimeType(params,overtimeTypeId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteOvertimeType(@RequestParam(value = "overtimeTypeIds") String overtimeTypeIds) {
		int result = 0;
		try {
			result = systemService.deleteOverTimeType(overtimeTypeIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/getAllOvertimeTypes")
	@ResponseBody
	public List<Map<String, Object>> getAllOvertimeTypes() {
		return systemService.getAllOvertimeTypes();

	}
	
	@RequestMapping(value="/getName")
	@ResponseBody
	public String getOvertimeTypeNameById(@RequestParam(value="typeId") Long typeId){
		String result = "";
		try {
			result = systemService.getOverTimeTypeNameById(typeId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
