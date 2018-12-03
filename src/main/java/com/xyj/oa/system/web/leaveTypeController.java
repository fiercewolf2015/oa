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
@RequestMapping(value = "leaveType")
public class leaveTypeController {
	
	private static Logger logger = LoggerFactory.getLogger(leaveTypeController.class);

	@Autowired
	private SystemService systemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/leavetype";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findLeaveTypeCountWithParams(params),
					systemService.findLeaveTypeListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public int addLeaveType(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			result = systemService.saveLeaveType(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editLeaveType(@RequestParam(value = "params") String params,@RequestParam(value = "leaveTypeId") Long leaveTypeId) {
		int result = 0;
		try {
			result = systemService.editLeaveType(params,leaveTypeId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteLeaveType(@RequestParam(value = "leaveTypeIds") String leaveTypeIds) {
		int result = 0;
		try {
			result = systemService.deleteLeaveType(leaveTypeIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/getAllLeaveTypes")
	@ResponseBody
	public List<Map<String, Object>> getAllLeaveTypes() {
		return systemService.getAllLeaveTypes();

	}

}
