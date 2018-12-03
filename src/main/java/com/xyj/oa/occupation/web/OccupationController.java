package com.xyj.oa.occupation.web;

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

import com.xyj.oa.occupation.service.OccupationService;
import com.xyj.oa.util.LogUtil;

@Controller
@RequestMapping(value = "occupation")
public class OccupationController {
	private static Logger logger = LoggerFactory.getLogger(OccupationController.class);

	@Autowired
	private OccupationService occupationService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "corp/occupation";
	}

	@RequestMapping(value = "/loadOccupationTree")
	@ResponseBody
	public List<Map<String, Object>> loadOccupationTree() {
		try {
			return occupationService.loadOccupationTree();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return new ArrayList<Map<String, Object>>();
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addOccupation(@RequestParam(value = "params") String params, @RequestParam(value = "pId") Long pId) {
		int result = 0;
		try {
			result = occupationService.addOccupation(params, pId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editOccupation(@RequestParam(value = "params") String params, @RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = occupationService.editOccupation(params, id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteOccupation(@RequestParam(value = "id") Long id) {
		int result = 0;
		try {
			result = occupationService.deleteOccupation(id);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}

}
